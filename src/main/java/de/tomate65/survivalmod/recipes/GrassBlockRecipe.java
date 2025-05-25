package de.tomate65.survivalmod.recipes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GrassBlockRecipe implements RecipeGeneratorHelper {
    @Override
    public void generateRecipeFile(File directory) {
        if (!directory.exists() && !directory.mkdirs()) {
            System.err.println("Failed to create recipe directory");
            return;
        }

        String recipeJson = """
{
  "type": "minecraft:crafting_shaped",
  "category": "misc",
  "key": {
    "S": [
      "minecraft:short_grass"
    ],
    "D": [
      "minecraft:dirt"
    ]
  },
  "pattern": [
    "S",
    "D"
  ],
  "result": {
    "item": "minecraft:grass_block",
    "id": "minecraft:grass_block",
    "count": 1
  }
}
""";

        File outputFile = new File(directory, "grass_block.json");
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(recipeJson);
        } catch (IOException e) {
            System.err.println("Failed to write grass block recipe file: " + e.getMessage());
        }
    }
}