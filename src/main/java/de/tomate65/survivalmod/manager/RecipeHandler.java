package de.tomate65.survivalmod.manager;

import de.tomate65.survivalmod.datapackgen.DatapackGen;
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

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            Path worldPath = server.getSavePath(WorldSavePath.ROOT);
            File datapackFolder = worldPath.resolve("datapacks/runtime_recipes").toFile();
            DatapackGen.registerRecipesToDatapack(datapackFolder, enabledRecipes, RECIPE_DIR);
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
