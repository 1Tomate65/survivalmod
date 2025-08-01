package de.tomate65.survivalmod.recipes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TallGrassRecipe implements RecipeGeneratorHelper {
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
    ]
  },
  "pattern": [
    "S",
    "S"
  ],
  "result": {
    "id": "minecraft:tall_grass"
  },
  "count": 1
}
""";

        File outputFile = new File(directory, "tall_grass.json");
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(recipeJson);
        } catch (IOException e) {
            System.err.println("Failed to write tall grass recipe file: " + e.getMessage());
        }
    }
}