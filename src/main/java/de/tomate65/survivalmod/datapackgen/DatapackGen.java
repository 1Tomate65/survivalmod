package de.tomate65.survivalmod.datapackgen;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Set;

public class DatapackGen {
    private static final String[] ICON_FILES = {"icon.png", "icon_1.png"};

    public static void registerRecipesToDatapack(File datapackFolder, Set<String> enabledRecipes, File recipeSourceDir) {
        // Validate recipe source directory exists
        if (!recipeSourceDir.exists()) {
            System.err.println("[SurvivalMod] Recipe source directory does not exist: " + recipeSourceDir.getAbsolutePath());
            return;
        }

        File recipesDir = new File(datapackFolder, "data/survival/recipe");
        File metaFile = new File(datapackFolder, "pack.mcmeta");

        try {
            System.out.println("[SurvivalMod] Generating datapack with " + enabledRecipes.size() + " enabled recipes...");

            // Clean old recipes and icon files
            cleanOldFiles(datapackFolder, recipesDir);

            // Create necessary directories
            if (!recipesDir.exists() && !recipesDir.mkdirs()) {
                throw new IOException("Failed to create recipe directory: " + recipesDir.getAbsolutePath());
            }

            // Write pack.mcmeta
            writePackMeta(metaFile);

            // Copy mod icon with rare chance for alternate icon
            copyModIcon(datapackFolder);

            // Copy enabled recipes
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
            Files.walk(recipesDir.toPath())
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }

        // Clean old icon files
        for (String icon : ICON_FILES) {
            File iconFile = new File(datapackFolder, icon);
            if (iconFile.exists()) {
                Files.delete(iconFile.toPath());
            }
        }
    }

    private static void writePackMeta(File metaFile) throws IOException {
        if (!metaFile.exists()) {
            System.out.println("[SurvivalMod] Creating pack.mcmeta file...");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(metaFile))) {
                writer.write("{\"pack\":{\"pack_format\":26,\"description\":\"SurvivalMod Runtime Recipes\"}}");
            }
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
                    Files.copy(iconStream, iconFile.toPath());
                } else {
                    System.out.println("[SurvivalMod] Could not find mod icon at: " + iconPath);
                }
            }
        } catch (IOException e) {
            System.err.println("[SurvivalMod] Failed to copy mod icon: " + e.getMessage());
            throw e;
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
            Files.copy(source.toPath(), target.toPath());
            copiedCount++;
        }
        return copiedCount;
    }
}