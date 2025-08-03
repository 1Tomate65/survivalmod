package de.tomate65.survivalmod.config.datapack.recipes;

import de.tomate65.survivalmod.manager.RecipeGeneratorHelper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GoldBlockBlastingRecipe implements RecipeGeneratorHelper {
    @Override
    public void generateRecipeFile(File directory) {
        if (!directory.exists() && !directory.mkdirs()) {
            System.err.println("Failed to create recipe directory");
            return;
        }

        String recipeJson = """
{
  "type": "minecraft:blasting",
  "ingredient": [
    "minecraft:raw_gold_block"
  ],
  "result": {
    "id": "minecraft:gold_block"
  },
  "experience": 2.25,
  "cookingtime": 250
}
        """;

        File outputFile = new File(directory, "gold_block.json");
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(recipeJson);
        } catch (IOException e) {
            System.err.println("Failed to write gold block blasting recipe file: " + e.getMessage());
        }
    }
}