package de.tomate65.survivalmod.recipes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LightBlockRecipe implements RecipeGeneratorHelper {
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
    "G": [
      "minecraft:glass_pane"
    ],
    "T": [
      "minecraft:torch"
    ]
  },
  "pattern": [
    "GGG",
    "GTG",
    "GGG"
  ],
  "result": {
    "id": "minecraft:light",
    "count": 1
  }
}
        """;

        File outputFile = new File(directory, "light_block.json");
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(recipeJson);
        } catch (IOException e) {
            System.err.println("Failed to write light block recipe file: " + e.getMessage());
        }
    }
}