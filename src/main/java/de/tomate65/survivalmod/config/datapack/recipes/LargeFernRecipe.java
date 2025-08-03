package de.tomate65.survivalmod.config.datapack.recipes;

import de.tomate65.survivalmod.manager.RecipeGeneratorHelper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LargeFernRecipe implements RecipeGeneratorHelper {
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
    "F": [
      "minecraft:fern"
    ]
  },
  "pattern": [
    "F",
    "F"
  ],
  "result": {
    "id": "minecraft:large_fern"
  },
  "count": 1
}
""";

        File outputFile = new File(directory, "large_fern.json");
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(recipeJson);
        } catch (IOException e) {
            System.err.println("Failed to write large fern recipe file: " + e.getMessage());
        }
    }
}