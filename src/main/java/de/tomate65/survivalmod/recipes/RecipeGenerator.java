package de.tomate65.survivalmod.recipes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeGenerator {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void generateShapedRecipe(String output, int count, String[][] pattern, Map<Character, String> key, String savePath) {
        Map<String, Object> json = new HashMap<>();
        json.put("type", "minecraft:crafting_shaped");
        json.put("pattern", pattern);

        Map<String, Object> keyMap = new HashMap<>();
        for (Map.Entry<Character, String> entry : key.entrySet()) {
            Map<String, String> itemMap = new HashMap<>();
            itemMap.put("item", entry.getValue());
            keyMap.put(entry.getKey().toString(), itemMap);
        }
        json.put("key", keyMap);

        Map<String, Object> result = new HashMap<>();
        result.put("item", output);
        if (count > 1) {
            result.put("count", count);
        }
        json.put("result", result);

        saveJson(json, savePath);
    }

    public static void generateShapelessRecipe(String output, int count, List<String> ingredients, String savePath) {
        Map<String, Object> json = new HashMap<>();
        json.put("type", "minecraft:crafting_shapeless");

        List<Map<String, String>> ingredientList = ingredients.stream()
                .map(item -> Map.of("item", item))
                .toList();
        json.put("ingredients", ingredientList);

        Map<String, Object> result = new HashMap<>();
        result.put("item", output);
        if (count > 1) {
            result.put("count", count);
        }
        json.put("result", result);

        saveJson(json, savePath);
    }

    public static void generateBlastingRecipe(String input, String output, float experience, int cookingTime, String savePath) {
        Map<String, Object> json = new HashMap<>();
        json.put("type", "minecraft:blasting");

        Map<String, String> ingredient = new HashMap<>();
        ingredient.put("item", input);
        json.put("ingredient", ingredient);

        json.put("result", output);
        json.put("experience", experience);
        json.put("cookingtime", cookingTime);

        saveJson(json, savePath);
    }

    public static void generateSmithingRecipe(String template, String base, String addition, String result, int count, String savePath) {
        Map<String, Object> json = new HashMap<>();
        json.put("type", "minecraft:smithing_transform");

        Map<String, String> templateItem = new HashMap<>();
        templateItem.put("item", template);
        json.put("template", templateItem);

        Map<String, String> baseItem = new HashMap<>();
        baseItem.put("item", base);
        json.put("base", baseItem);

        Map<String, String> additionItem = new HashMap<>();
        additionItem.put("item", addition);
        json.put("addition", additionItem);

        Map<String, Object> resultItem = new HashMap<>();
        resultItem.put("item", result);
        if (count > 1) {
            resultItem.put("count", count); // Fügt die Anzahl hinzu, falls sie größer als 1 ist
        }
        json.put("result", resultItem);

        saveJson(json, savePath);
    }

    private static void saveJson(Map<String, Object> json, String path) {
        try (FileWriter writer = new FileWriter(path)) {
            GSON.toJson(json, writer);
            System.out.println("Rezept gespeichert: " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
