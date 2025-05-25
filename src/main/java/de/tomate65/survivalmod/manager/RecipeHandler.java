package de.tomate65.survivalmod.manager;

import de.tomate65.survivalmod.recipes.RecipeGenerator;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.util.WorldSavePath;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class RecipeHandler {
    private static final File RECIPE_DIR = new File("config/survival/recipe");
    private static final File TOGGLE_FILE = new File("config/survival/recipe_toggles.json");

    private static Set<String> enabledRecipes = new HashSet<>();


    public static void initialize() {
        createDirectories();
        RecipeGenerator.generateAllRecipes(RECIPE_DIR);
        loadToggles();

        // Register datapack logic once the server is fully started
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            Path worldPath = server.getSavePath(WorldSavePath.ROOT);
            File datapackFolder = worldPath.resolve("datapacks/runtime_recipes").toFile();
            registerRecipesToDatapack(datapackFolder);
        });
    }

    private static void createDirectories() {
        if (!RECIPE_DIR.exists()) RECIPE_DIR.mkdirs();
    }

    private static void loadToggles() {
        enabledRecipes.clear();
        if (!TOGGLE_FILE.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(TOGGLE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                enabledRecipes.add(line.trim());
            }
        } catch (IOException e) {
            System.err.println("Failed to read recipe toggle file: " + e.getMessage());
        }
    }

    private static void saveToggles() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TOGGLE_FILE))) {
            for (String recipe : enabledRecipes) {
                writer.write(recipe);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Failed to save recipe toggles: " + e.getMessage());
        }
    }

    public static boolean toggleRecipe(String recipeId, boolean enable) {
        if (!recipeId.endsWith(".json")) {
            recipeId += ".json";
        }

        File file = new File(RECIPE_DIR, recipeId);
        if (!file.exists()) return false;

        if (enable) enabledRecipes.add(recipeId);
        else enabledRecipes.remove(recipeId);

        saveToggles();
        return true;
    }

    public static boolean isRecipeEnabled(String recipe) {
        return enabledRecipes.contains(recipe);
    }

    public static void registerRecipesToDatapack(File datapackFolder) {
        File recipesDir = new File(datapackFolder, "data/survival/recipe");
        File iconFile = new File(datapackFolder, "icon.png");
        File metaFile = new File(datapackFolder, "pack.mcmeta");

        try {
            // Clean old recipes
            if (recipesDir.exists()) {
                Files.walk(recipesDir.toPath())
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            }
            recipesDir.mkdirs();

            // Write pack.mcmeta
            if (!metaFile.exists()) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(metaFile))) {
                    writer.write("{\"pack\":{\"pack_format\":26,\"description\":\"SurvivalMod Runtime Recipes\"}}");
                }
            }

            // Copy mod icon with rare chance for alternate icon
            try {
                String iconPath = (Math.random() < 0.001) ?
                        "/assets/survivalmod/icon_1.png" :
                        "/assets/survivalmod/icon.png";

                try (InputStream iconStream = RecipeHandler.class.getResourceAsStream(iconPath)) {
                    if (iconStream != null) {
                        Files.copy(iconStream, iconFile.toPath());
                    } else {
                        System.out.println("[SurvivalMod] Could not find mod icon at: " + iconPath);
                    }
                }
            } catch (IOException e) {
                System.err.println("Failed to copy mod icon: " + e.getMessage());
            }

            // Copy enabled recipes
            for (String recipeId : enabledRecipes) {
                File source = new File(RECIPE_DIR, recipeId);
                File target = new File(recipesDir, recipeId);
                target.getParentFile().mkdirs();
                Files.copy(source.toPath(), target.toPath());
            }

            System.out.println("[SurvivalMod] Dynamic recipes loaded into datapack.");
        } catch (IOException e) {
            System.err.println("Failed to register recipes to datapack: " + e.getMessage());
        }
    }

    public static int toggleAllRecipes(boolean enable) {
        File[] files = RECIPE_DIR.listFiles((dir, name) -> name.endsWith(".json"));
        if (files == null) return 0;

        int previousCount = enabledRecipes.size();

        if (enable) {
            // Add all recipes
            for (File file : files) {
                enabledRecipes.add(file.getName());
            }
        } else {
            // Remove all recipes
            enabledRecipes.clear();
        }

        // Only save if something changed
        if (enable ? (enabledRecipes.size() != previousCount) : (previousCount > 0)) {
            saveToggles();
        }

        return enable ? files.length : previousCount;
    }

    public static List<String> getAllRecipeFiles() {
        File[] files = RECIPE_DIR.listFiles((dir, name) -> name.endsWith(".json"));
        if (files == null) return Collections.emptyList();

        return Arrays.stream(files)
                .map(File::getName)
                .collect(Collectors.toList());
    }
}
