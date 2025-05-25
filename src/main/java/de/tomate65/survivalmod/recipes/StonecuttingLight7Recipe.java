package de.tomate65.survivalmod.recipes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class StonecuttingLight7Recipe implements RecipeGeneratorHelper {
    @Override
    public void generateRecipeFile(File directory) {
        if (!directory.exists() && !directory.mkdirs()) {
            System.err.println("Failed to create recipe directory");
            return;
        }

        String recipeJson = """
{
  "type": "minecraft:stonecutting",
  "ingredient": "minecraft:light",
  "result": {
    "id": "minecraft:light",
    "components": {
      "minecraft:block_state": {
        "level": "7"
      }
    },
    "count": 1
  }
}
        """;

        File outputFile = new File(directory, "stonecutting_light_7.json");
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(recipeJson);
        } catch (IOException e) {
            System.err.println("Failed to write stonecutting light level 0 recipe file: " + e.getMessage());
        }
    }
}