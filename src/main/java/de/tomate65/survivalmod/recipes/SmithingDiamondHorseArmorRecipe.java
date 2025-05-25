package de.tomate65.survivalmod.recipes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SmithingDiamondHorseArmorRecipe implements RecipeGeneratorHelper {
    @Override
    public void generateRecipeFile(File directory) {
        if (!directory.exists() && !directory.mkdirs()) {
            System.err.println("Failed to create recipe directory");
            return;
        }

        String recipeJson = """
{
  "type": "minecraft:smithing_transform",
  "base": [
    "minecraft:golden_horse_armor"
  ],
  "addition": [
    "minecraft:diamond_block"
  ],
  "template": [
    "minecraft:netherite_upgrade_smithing_template"
  ],
  "result": {
    "id": "minecraft:diamond_horse_armor",
    "count": 1
  }
}
""";

        File outputFile = new File(directory, "smithing_diamond_horsearmor.json");
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(recipeJson);
        } catch (IOException e) {
            System.err.println("Failed to write diamond horse armor recipe: " + e.getMessage());
        }
    }
}