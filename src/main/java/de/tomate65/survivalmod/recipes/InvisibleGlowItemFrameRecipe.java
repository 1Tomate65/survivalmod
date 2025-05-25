package de.tomate65.survivalmod.recipes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class InvisibleGlowItemFrameRecipe implements RecipeGeneratorHelper {
    @Override
    public void generateRecipeFile(File directory) {
        if (!directory.exists() && !directory.mkdirs()) {
            System.err.println("Failed to create recipe directory");
            return;
        }

        String recipeJson = """
{
  "type": "minecraft:crafting_shaped",
  "category": "building",
  "key": {
    "G": [
      "minecraft:glass_pane"
    ],
    "I": [
      "minecraft:glow_item_frame"
    ]
  },
  "pattern": [
    "GGG",
    "GIG",
    "GGG"
  ],
  "result": {
    "id": "minecraft:glow_item_frame",
    "components": {
      "minecraft:custom_name": "\\"Invisible Glow Item Frame\\"",
      "minecraft:item_name": "\\"Invisible Glow Item Frame\\"",
      "minecraft:rarity": "rare",
      "minecraft:entity_data": "{id:\\"minecraft:glow_item_frame\\",Invisible:1b}"
    },
    "count": 1
  },
  "show_notification": false
}
        """;

        File outputFile = new File(directory, "invisible_glow_item_frame.json");
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(recipeJson);
        } catch (IOException e) {
            System.err.println("Failed to write invisible glow item frame recipe file: " + e.getMessage());
        }
    }
}