package de.tomate65.survivalmod.config.datapack.datapackgen;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.Set;


public class DatapackGen {
    private static final String[] ICON_FILES = {"icon.png", "icon_1.png"};

    /**
     * @param datapackFolder
     * @param enabledData
     * @param dataSourceDir
     * @param dataType
     */
    public static void registerDataToDatapack(File datapackFolder, Set<String> enabledData, File dataSourceDir, String dataType) {
        if (!dataSourceDir.exists()) {
            System.err.println("[SurvivalMod] " + dataType + " source directory does not exist: " + dataSourceDir.getAbsolutePath());
            return;
        }

        File dataTargetDir = new File(datapackFolder, "data/survival/" + dataType);
        File metaFile = new File(datapackFolder, "pack.mcmeta");

        try {
            System.out.println("[SurvivalMod] Generating datapack with " + enabledData.size() + " enabled " + dataType + "...");

            if (dataTargetDir.exists()) {
                cleanOldDataFiles(dataTargetDir);
            }

            if (!dataTargetDir.exists() && !dataTargetDir.mkdirs()) {
                throw new IOException("Failed to create " + dataType + " directory: " + dataTargetDir.getAbsolutePath());
            }

            if (!datapackFolder.exists()) {
                datapackFolder.mkdirs();
            }

            if (!metaFile.exists()) {
                writePackMeta(metaFile);
                copyModIcon(datapackFolder);
            }


            int copiedCount = copyEnabledData(enabledData, dataSourceDir, dataTargetDir, dataType);

            System.out.println("[SurvivalMod] Successfully loaded " + copiedCount + " " + dataType + " into datapack at: " + datapackFolder.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("[SurvivalMod] Failed to register " + dataType + " to datapack: " + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * @param dataDir
     * @throws IOException
     */
    private static void cleanOldDataFiles(File dataDir) throws IOException {
        System.out.println("[SurvivalMod] Cleaning old data from: " + dataDir.getPath());
        try (var stream = Files.walk(dataDir.toPath())) {
            stream.sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }


    private static void writePackMeta(File metaFile) throws IOException {
        System.out.println("[SurvivalMod] Creating pack.mcmeta file...");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(metaFile))) {
            writer.write("{\"pack\":{\"pack_format\":26,\"description\":\"SurvivalMod Runtime Data\"}}");
        }
    }

    private static void copyModIcon(File datapackFolder) throws IOException {
        try {
            String iconPath = (Math.random() < 0.001) ?
                    "/assets/survivalmod/icon_1.png" :
                    "/assets/survivalmod/icon.png";

            String iconName = iconPath.contains("icon_1.png") ? "icon_1.png" : "icon.png";
            File iconFile = new File(datapackFolder, iconName);

            try (InputStream iconStream = DatapackGen.class.getResourceAsStream(iconPath)) {
                if (iconStream != null) {
                    System.out.println("[SurvivalMod] Copying mod icon: " + iconName);
                    Files.copy(iconStream, iconFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } else {
                    System.out.println("[SurvivalMod] Could not find mod icon at: " + iconPath);
                }
            }
        } catch (IOException e) {
            System.err.println("[SurvivalMod] Failed to copy mod icon: " + e.getMessage());
            throw e;
        }
    }

    private static int copyEnabledData(Set<String> enabledData, File dataSourceDir, File dataTargetDir, String dataType) throws IOException {
        int copiedCount = 0;
        for (String dataId : enabledData) {
            File source = new File(dataSourceDir, dataId);
            if (!source.exists()) {
                System.err.println("[SurvivalMod] " + dataType + " file not found: " + source.getAbsolutePath());
                continue;
            }

            File target = new File(dataTargetDir, dataId);
            if (!target.getParentFile().exists() && !target.getParentFile().mkdirs()) {
                throw new IOException("Failed to create directory: " + target.getParentFile().getAbsolutePath());
            }

            System.out.println("[SurvivalMod] Copying " + dataType + ": " + dataId);
            Files.copy(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
            copiedCount++;
        }
        return copiedCount;
    }
    public static void registerRecipesToDatapack(File datapackFolder, Set<String> enabledRecipes, File recipeSourceDir) {
        if (!recipeSourceDir.exists()) {
            System.err.println("[SurvivalMod] Recipe source directory does not exist: " + recipeSourceDir.getAbsolutePath());
            return;
        }

        File recipesDir = new File(datapackFolder, "data/survival/recipe");
        File metaFile = new File(datapackFolder, "pack.mcmeta");

        try {
            System.out.println("[SurvivalMod] Generating datapack with " + enabledRecipes.size() + " enabled recipes...");

            cleanOldFiles(datapackFolder, recipesDir);

            if (!recipesDir.exists() && !recipesDir.mkdirs()) {
                throw new IOException("Failed to create recipe directory: " + recipesDir.getAbsolutePath());
            }

            writePackMeta(metaFile);

            copyModIcon(datapackFolder);

            int copiedCount = copyEnabledRecipes(enabledRecipes, recipeSourceDir, recipesDir);

            System.out.println("[SurvivalMod] Successfully loaded " + copiedCount + " recipes into datapack at: " + datapackFolder.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("[SurvivalMod] Failed to register recipes to datapack: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void cleanOldFiles(File datapackFolder, File recipesDir) throws IOException {
        // Clean old recipes
        if (recipesDir.exists()) {
            System.out.println("[SurvivalMod] Cleaning old recipes from datapack...");
            if (datapackFolder.exists()) {
                Files.walk(datapackFolder.toPath())
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            }
        }
        for (String icon : ICON_FILES) {
            File iconFile = new File(datapackFolder, icon);
            if (iconFile.exists()) {
                Files.delete(iconFile.toPath());
            }
        }
    }

    private static int copyEnabledRecipes(Set<String> enabledRecipes, File recipeSourceDir, File recipesTargetDir) throws IOException {
        int copiedCount = 0;
        for (String recipeId : enabledRecipes) {
            File source = new File(recipeSourceDir, recipeId);
            if (!source.exists()) {
                System.err.println("[SurvivalMod] Recipe file not found: " + source.getAbsolutePath());
                continue;
            }

            File target = new File(recipesTargetDir, recipeId);
            if (!target.getParentFile().exists() && !target.getParentFile().mkdirs()) {
                throw new IOException("Failed to create directory: " + target.getParentFile().getAbsolutePath());
            }

            System.out.println("[SurvivalMod] Copying recipe: " + recipeId);
            Files.copy(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
            copiedCount++;
        }
        return copiedCount;
    }
}