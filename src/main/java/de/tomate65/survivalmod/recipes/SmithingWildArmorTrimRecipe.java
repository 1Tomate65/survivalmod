package de.tomate65.survivalmod.recipes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SmithingWildArmorTrimRecipe implements RecipeGeneratorHelper {
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
    "minecraft:wild_armor_trim_smithing_template"
  ],
  "addition": [
    "minecraft:netherite_ingot"
  ],
  "template": [
    "minecraft:netherite_upgrade_smithing_template"
  ],
  "result": {
    "id": "minecraft:wild_armor_trim_smithing_template",
    "count": 2
  }
}
""";

        File outputFile = new File(directory, "smithing_wild_armortrim.json");
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(recipeJson);
        } catch (IOException e) {
            System.err.println("Failed to write wild armor trim recipe file: " + e.getMessage());
        }
    }
}