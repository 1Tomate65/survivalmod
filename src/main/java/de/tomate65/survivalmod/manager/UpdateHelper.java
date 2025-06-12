package de.tomate65.survivalmod.manager;

import de.tomate65.survivalmod.config.ConfigGenerator;
import de.tomate65.survivalmod.config.ConfigReader;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import static de.tomate65.survivalmod.Survivalmod.ModVersion;

public class UpdateHelper {
    private static final File CONFIG_ROOT = new File(FabricLoader.getInstance().getConfigDir().toFile(), "survival");
    private static final String CURRENT_VERSION = ModVersion;

    public static void checkAndUpdateConfigs() {
        String configVersion = ConfigReader.getModVersion();

        if (!configVersion.equals(CURRENT_VERSION)) {
            System.out.println("[SurvivalMod] Config version (" + configVersion + ") doesn't match mod version (" + CURRENT_VERSION + "). Migrating configs...");

            File oldVersionFolder = new File(CONFIG_ROOT, configVersion);
            if (!oldVersionFolder.exists()) {
                oldVersionFolder.mkdirs();
            }

            File newVersionFolder = new File(CONFIG_ROOT, CURRENT_VERSION);
            if (!newVersionFolder.exists()) {
                newVersionFolder.mkdirs();
            }

            moveConfigsToVersionedFolder(oldVersionFolder);

            ConfigGenerator.generateConfigs();
            ConfigReader.loadConfig();
        }
    }

    private static void moveConfigsToVersionedFolder(File versionFolder) {
        try {
            try (Stream<Path> paths = Files.list(CONFIG_ROOT.toPath())) {
                paths.filter(path -> {
                    String fileName = path.getFileName().toString();
                    return !fileName.matches("\\d+\\.\\d+\\.\\d+.*") &&
                            !fileName.equals("survival") &&
                            !path.toFile().isDirectory();
                }).forEach(path -> {
                    try {
                        Files.move(path, versionFolder.toPath().resolve(path.getFileName()),
                                StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        System.err.println("[SurvivalMod] Failed to move config file: " + path);
                        e.printStackTrace();
                    }
                });
            }

            File playerDataFolder = new File(CONFIG_ROOT, "Playerdata");
            if (playerDataFolder.exists()) {
                Files.move(playerDataFolder.toPath(), versionFolder.toPath().resolve("Playerdata"),
                        StandardCopyOption.REPLACE_EXISTING);
            }

            File recipeFolder = new File(CONFIG_ROOT, "recipe");
            if (recipeFolder.exists()) {
                Files.move(recipeFolder.toPath(), versionFolder.toPath().resolve("recipe"),
                        StandardCopyOption.REPLACE_EXISTING);
            }

            File langFolder = new File(CONFIG_ROOT, "lang");
            if (langFolder.exists()) {
                Files.move(langFolder.toPath(), versionFolder.toPath().resolve("lang"),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            System.err.println("[SurvivalMod] Error during config migration:");
            e.printStackTrace();
        }
    }

    public static String getCurrentVersion() {
        return CURRENT_VERSION;
    }
}