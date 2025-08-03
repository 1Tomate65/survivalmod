package de.tomate65.survivalmod.config.datapack.recipes;

import de.tomate65.survivalmod.manager.RecipeGeneratorHelper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SmithingIronHorseArmorRecipe implements RecipeGeneratorHelper {
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
    "minecraft:leather_horse_armor"
  ],
  "addition": [
    "minecraft:iron_block"
  ],
  "template": [
    "minecraft:netherite_upgrade_smithing_template"
  ],
  "result": {
    "id": "minecraft:iron_horse_armor",
    "count": 1
  }
}
""";

        File outputFile = new File(directory, "smithing_iron_horsearmor.json");
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(recipeJson);
        } catch (IOException e) {
            System.err.println("Failed to write iron horse armor recipe: " + e.getMessage());
        }
    }
}