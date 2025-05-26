package de.tomate65.survivalmod.datapackgen;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Set;

public class DatapackGen {
    private static final String[] ICON_FILES = {"icon.png", "icon_1.png"};

    public static void registerRecipesToDatapack(File datapackFolder, Set<String> enabledRecipes, File recipeSourceDir) {
        File recipesDir = new File(datapackFolder, "data/survival/recipe");
        File metaFile = new File(datapackFolder, "pack.mcmeta");

        try {
            // Clean old recipes and icon files
            cleanOldFiles(datapackFolder, recipesDir);

            // Create necessary directories
            recipesDir.mkdirs();

            // Write pack.mcmeta
            writePackMeta(metaFile);

            // Copy mod icon with rare chance for alternate icon
            copyModIcon(datapackFolder);

            // Copy enabled recipes
            copyEnabledRecipes(enabledRecipes, recipeSourceDir, recipesDir);

            System.out.println("[SurvivalMod] Dynamic recipes loaded into datapack.");
        } catch (IOException e) {
            System.err.println("Failed to register recipes to datapack: " + e.getMessage());
        }
    }

    private static void cleanOldFiles(File datapackFolder, File recipesDir) throws IOException {
        // Clean old recipes
        if (recipesDir.exists()) {
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
                    Files.copy(iconStream, iconFile.toPath());
                } else {
                    System.out.println("[SurvivalMod] Could not find mod icon at: " + iconPath);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to copy mod icon: " + e.getMessage());
            throw e;
        }
    }

    private static void copyEnabledRecipes(Set<String> enabledRecipes, File recipeSourceDir, File recipesTargetDir) throws IOException {
        for (String recipeId : enabledRecipes) {
            File source = new File(recipeSourceDir, recipeId);
            File target = new File(recipesTargetDir, recipeId);
            target.getParentFile().mkdirs();
            Files.copy(source.toPath(), target.toPath());
        }
    }
}