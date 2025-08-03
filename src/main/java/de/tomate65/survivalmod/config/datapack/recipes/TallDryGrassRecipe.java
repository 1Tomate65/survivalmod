package de.tomate65.survivalmod.config.datapack.recipes;

import de.tomate65.survivalmod.manager.RecipeGeneratorHelper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TallDryGrassRecipe implements RecipeGeneratorHelper {
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
      "minecraft:short_dry_grass"
    ]
  },
  "pattern": [
    "S",
    "S"
  ],
  "result": {
    "id": "minecraft:tall_dry_grass"
  },
  "count": 1
}
""";

        File outputFile = new File(directory, "tall_dry_grass.json");
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(recipeJson);
        } catch (IOException e) {
            System.err.println("Failed to write tall grass recipe file: " + e.getMessage());
        }
    }
}