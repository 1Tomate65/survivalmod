package de.tomate65.survivalmod.config.datapack.recipes;

import de.tomate65.survivalmod.manager.RecipeGeneratorHelper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CryingObsidianRecipe implements RecipeGeneratorHelper {
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
    "O": [
      "minecraft:obsidian"
    ],
    "D": [
      "minecraft:dragon_breath"
    ]
  },
  "pattern": [
    "OOO",
    "ODO",
    "OOO"
  ],
  "result": {
    "item": "minecraft:crying_obsidian",
    "id": "minecraft:crying_obsidian",
    "count": 4
  }
}
""";

        File outputFile = new File(directory, "crying_obsidian.json");
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(recipeJson);
        } catch (IOException e) {
            System.err.println("Failed to write crying obsidian recipe file: " + e.getMessage());
        }
    }
}