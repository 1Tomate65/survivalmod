package de.tomate65.survivalmod.recipes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BetterLoadStoneRecipe implements RecipeGeneratorHelper {
    @Override
    public void generateRecipeFile(File directory) {
        if (!directory.exists() && !directory.mkdirs()) {
            System.err.println("Failed to create recipe directory");
            return;
        }
        // Create the JSON content as a formatted string
        String recipeJson = """
{
  "type": "minecraft:crafting_shaped",
  "category": "misc",
  "key": {
    "C": [
      "minecraft:chiseled_stone_bricks"
    ],
    "I": [
      "netherite_ingot"
    ]
  },
  "pattern": [
    "CCC",
    "CIC",
    "CCC"
  ],
  "result": {
    "id": "minecraft:lodestone",
    "count": 4
  }
}
    """;

        // Write to file
        File outputFile = new File(directory, "better_lodestone.json");
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(recipeJson);
        } catch (IOException e) {
            System.err.println("Failed to write better lodestone recipe file: " + e.getMessage());
        }
    }
}