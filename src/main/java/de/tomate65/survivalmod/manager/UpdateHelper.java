package de.tomate65.survivalmod.manager;

import com.google.gson.*;
import de.tomate65.survivalmod.config.ConfigGenerator;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.tomate65.survivalmod.Survivalmod.ModVersion;

public class UpdateHelper {
    // Shared constants
    static final File CONFIG_ROOT = new File(FabricLoader.getInstance().getConfigDir().toFile(), "survival");
    private static final String CURRENT_VERSION = ModVersion;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final List<String> MODIFIABLE_CONFIGS = Arrays.asList("toggle.json", "survival.json", "conf.json");

    // Version migration core methods
    static void migrateConfigs(String fromVersion, String toVersion) throws IOException {
        File fromDir = new File(CONFIG_ROOT, fromVersion);
        File toDir = new File(CONFIG_ROOT, toVersion);

        if (!fromDir.exists()) {
            throw new IOException("Source version directory doesn't exist: " + fromDir);
        }

        if (!toDir.exists()) {
            toDir.mkdirs();
        }

        for (String configName : MODIFIABLE_CONFIGS) {
            File oldConfig = new File(fromDir, configName);
            File newConfig = new File(toDir, configName);
            if (oldConfig.exists()) {
                if (newConfig.exists()) {
                    mergeConfigs(oldConfig, newConfig);
                } else {
                    Files.copy(oldConfig.toPath(), newConfig.toPath());
                }
            }
        }

        File[] filesToCopy = fromDir.listFiles();
        if (filesToCopy != null) {
            for (File sourceFile : filesToCopy) {
                String fileName = sourceFile.getName();
                if (!MODIFIABLE_CONFIGS.contains(fileName)) {
                    Files.copy(
                            sourceFile.toPath(),
                            new File(toDir, fileName).toPath(),
                            StandardCopyOption.REPLACE_EXISTING
                    );
                }
            }
        }
    }

    private static void mergeConfigs(File oldConfig, File newConfig) throws IOException {
        JsonObject oldJson = JsonParser.parseReader(new FileReader(oldConfig)).getAsJsonObject();
        JsonObject newJson = JsonParser.parseReader(new FileReader(newConfig)).getAsJsonObject();

        for (String key : oldJson.keySet()) {
            if (!newJson.has(key)) {
                newJson.add(key, oldJson.get(key));
            } else if (oldJson.get(key).isJsonObject() && newJson.get(key).isJsonObject()) {
                mergeJsonObjects(oldJson.getAsJsonObject(key), newJson.getAsJsonObject(key));
            }
        }

        try (FileWriter writer = new FileWriter(newConfig)) {
            GSON.toJson(newJson, writer);
        }
    }

    private static void mergeJsonObjects(JsonObject source, JsonObject target) {
        for (String key : source.keySet()) {
            if (!target.has(key)) {
                target.add(key, source.get(key));
            } else if (source.get(key).isJsonObject() && target.get(key).isJsonObject()) {
                mergeJsonObjects(source.getAsJsonObject(key), target.getAsJsonObject(key));
            }
        }
    }

    public static String findHighestExistingVersion(List<String> versions) {
        File configRoot = new File(FabricLoader.getInstance().getConfigDir().toFile(), "survival");
        String highestVersion = null;

        for (String version : versions) {
            File versionDir = new File(configRoot, version);
            if (versionDir.exists() && versionDir.isDirectory()) {
                highestVersion = version;
            }
        }
        return highestVersion;
    }

    public static void moveConfigsToVersionedFolder(File versionFolder) throws IOException {
        System.out.println("[SurvivalMod] Moving unversioned configs to version: " + versionFolder.getName());

        // 1. Spezielle Ordner (Inhalt verschieben)
        // Korrigierte Kleinschreibung für "playerdata"
        moveSpecialFolderContents(versionFolder, "playerdata");
        moveSpecialFolderContents(versionFolder, "recipe");
        moveSpecialFolderContents(versionFolder, "lang");

        // 2. Alle restlichen losen Dateien verschieben
        try (Stream<Path> paths = Files.list(CONFIG_ROOT.toPath())) {
            paths.filter(path -> {
                String fileName = path.getFileName().toString();
                // Filter, um bereits versionierte Ordner und die speziellen Ordner auszuschließen
                return !fileName.matches("\\d+\\.\\d+\\.\\d+.*") &&
                        !fileName.equals("playerdata") &&
                        !fileName.equals("recipe") &&
                        !fileName.equals("lang") &&
                        !Files.isDirectory(path);
            }).forEach(path -> {
                try {
                    Files.move(path, versionFolder.toPath().resolve(path.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("[SurvivalMod] Moved file: " + path.getFileName());
                } catch (IOException e) {
                    System.err.println("[SurvivalMod] Failed to move config file: " + path.getFileName() + " - " + e.getMessage());
                }
            });
        }
    }

    private static void moveSpecialFolderContents(File versionFolder, String folderName) throws IOException {
        File sourceFolder = new File(CONFIG_ROOT, folderName);
        File targetFolder = new File(versionFolder, folderName);

        if (!sourceFolder.exists() || !sourceFolder.isDirectory()) {
            return; // Nichts zu tun, wenn der Quellordner nicht existiert
        }

        if (!targetFolder.exists()) {
            targetFolder.mkdirs(); // Zielordner erstellen, falls nicht vorhanden
        }

        try (Stream<Path> stream = Files.list(sourceFolder.toPath())) {
            for (Path sourcePath : stream.collect(Collectors.toList())) {
                Path targetPath = targetFolder.toPath().resolve(sourcePath.getFileName());
                Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            }
        }

        // Alten, jetzt leeren Ordner löschen
        Files.delete(sourceFolder.toPath());
        System.out.println("[SurvivalMod] Successfully moved content of folder: " + folderName);
    }

    public static String getCurrentVersion() {
        return CURRENT_VERSION;
    }

    public static void migrateThroughVersions(String fromVersion) throws IOException {
        List<String> versions = Arrays.asList(ConfigGenerator.VERSIONS);
        String currentVersion = getCurrentVersion();

        int currentIndex = versions.indexOf(currentVersion);
        int existingIndex = versions.indexOf(fromVersion);

        if (existingIndex < 0 || currentIndex < 0) {
            throw new IOException("Version not found in migration path");
        }


        // Create target version directory first
        File targetVersionFolder = new File(CONFIG_ROOT, currentVersion);
        if (!targetVersionFolder.exists()) {
            targetVersionFolder.mkdirs();

            // Only move old configs if they exist in source version folder
            File sourceVersionFolder = new File(CONFIG_ROOT, fromVersion);
            if (sourceVersionFolder.exists()) {
                migrateConfigs(fromVersion, currentVersion);
                moveConfigsToVersionedFolder(targetVersionFolder);
            }

            // Generate new configs after migration
            ConfigGenerator.generateConfigs();
        }
    }
}