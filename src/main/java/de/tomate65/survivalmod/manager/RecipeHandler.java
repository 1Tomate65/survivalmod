package de.tomate65.survivalmod.manager;

import com.google.gson.*;
import de.tomate65.survivalmod.config.datapack.datapackgen.DatapackGen;

import java.io.*;
import java.util.*;

import static de.tomate65.survivalmod.Survivalmod.ModVersion;

public class RecipeHandler {
    public static final File RECIPE_DIR = new File("config/survival/" + ModVersion + "/recipe");
    private static final File TOGGLE_FILE = new File(RECIPE_DIR.getParentFile(), "recipe_toggles.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static final Map<String, Boolean> recipeStates = new HashMap<>();

    public static void initialize() {
        createDirectories();
        loadToggles();
    }

    private static void createDirectories() {
        if (!RECIPE_DIR.exists()) {
            RECIPE_DIR.mkdirs();
        }
    }

    private static void loadToggles() {
        recipeStates.clear();
        File[] availableRecipeFiles = RECIPE_DIR.listFiles((dir, name) -> name.endsWith(".json"));
        if (availableRecipeFiles == null) {
            availableRecipeFiles = new File[0];
        }

        if (!TOGGLE_FILE.exists()) {
            for (File recipeFile : availableRecipeFiles) {
                recipeStates.put(recipeFile.getName().replace(".json", ""), false);
            }
            saveToggles();
            return;
        }
        try {
            boolean isOldFormat;
            try (FileReader reader = new FileReader(TOGGLE_FILE)) {
                isOldFormat = reader.read() != '{';
            }

            if (isOldFormat) {
                System.out.println("[SurvivalMod] Old plain text recipe_toggles.json format detected. Migrating...");
                Set<String> enabledInOldFormat = new HashSet<>();
                try (BufferedReader reader = new BufferedReader(new FileReader(TOGGLE_FILE))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (!line.isBlank()) {
                            enabledInOldFormat.add(line.trim().replace(".json", ""));
                        }
                    }
                }

                // Setze alle Rezepte auf 'true', um neue und alte zu aktivieren.
                for (File recipeFile : availableRecipeFiles) {
                    recipeStates.put(recipeFile.getName().replace(".json", ""), true);
                }

                TOGGLE_FILE.delete();
                saveToggles();
                System.out.println("[SurvivalMod] Migration to new recipe_toggles.json format complete.");

            } else {
                try (FileReader reader = new FileReader(TOGGLE_FILE)) {
                    JsonObject recipeObject = JsonParser.parseReader(reader).getAsJsonObject();
                    for (Map.Entry<String, JsonElement> entry : recipeObject.entrySet()) {
                        if (entry.getValue().isJsonPrimitive() && entry.getValue().getAsJsonPrimitive().isBoolean()) {
                            recipeStates.put(entry.getKey(), entry.getValue().getAsBoolean());
                        }
                    }
                }
            }

            boolean needsSave = false;
            for (File recipeFile : availableRecipeFiles) {
                String recipeName = recipeFile.getName().replace(".json", "");
                if (!recipeStates.containsKey(recipeName)) {
                    recipeStates.put(recipeName, false);
                    needsSave = true;
                }
            }
            if (needsSave) {
                saveToggles();
            }

        } catch (IOException e) {
            System.err.println("Failed to read or migrate recipe toggle file: " + e.getMessage());
        }
    }

    private static void saveToggles() {
        try (FileWriter writer = new FileWriter(TOGGLE_FILE)) {
            JsonObject recipeObject = new JsonObject();
            List<String> sortedKeys = new ArrayList<>(recipeStates.keySet());
            Collections.sort(sortedKeys);

            for (String key : sortedKeys) {
                recipeObject.addProperty(key, recipeStates.get(key));
            }
            GSON.toJson(recipeObject, writer);
        } catch (IOException e) {
            System.err.println("Failed to save recipe toggles: " + e.getMessage());
        }
    }

    public static boolean toggleRecipe(String recipeName, boolean enable) {
        if (recipeName.endsWith(".json")) {
            recipeName = recipeName.replace(".json", "");
        }
        if (!recipeStates.containsKey(recipeName)) {
            return false;
        }
        recipeStates.put(recipeName, enable);
        saveToggles();

        // Aktualisiere den Datapack
        File datapackFolder = new File("world/datapacks/survivalmod_datapack");
        Set<String> enabledRecipes = new HashSet<>();
        recipeStates.forEach((recipe, enabled) -> {
            if (enabled) {
                enabledRecipes.add(recipe + ".json");
            }
        });
        DatapackGen.registerRecipesToDatapack(datapackFolder, enabledRecipes, RECIPE_DIR);

        return true;
    }

    public static boolean isRecipeEnabled(String recipeName) {
        if (recipeName.endsWith(".json")) {
            recipeName = recipeName.replace(".json", "");
        }
        return recipeStates.getOrDefault(recipeName, false);
    }

    public static int toggleAllRecipes(boolean enable) {
        for (String key : recipeStates.keySet()) {
            recipeStates.put(key, enable);
        }
        saveToggles();
        return recipeStates.size();
    }

    public static Map<String, Boolean> getRecipeStates() {
        return Collections.unmodifiableMap(recipeStates);
    }

    public static List<String> getAllRecipeFiles() {
        return new ArrayList<>(recipeStates.keySet());
    }
}