package de.tomate65.survivalmod.recipes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SmithingGoldHorseArmorRecipe implements RecipeGeneratorHelper {
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
    "minecraft:iron_horse_armor"
  ],
  "addition": [
    "minecraft:gold_block"
  ],
  "template": [
    "minecraft:netherite_upgrade_smithing_template"
  ],
  "result": {
    "id": "minecraft:golden_horse_armor",
    "count": 1
  }
}
""";

        File outputFile = new File(directory, "smithing_gold_horsearmor.json");
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(recipeJson);
        } catch (IOException e) {
            System.err.println("Failed to write gold horse armor recipe: " + e.getMessage());
        }
    }
}