package de.tomate65.survivalmod.config.datapack.loottables;

import de.tomate65.survivalmod.manager.LootTableGeneratorHelper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DessertLootTable implements LootTableGeneratorHelper {

    @Override
    public void generateLootTableFile(File directory) throws IOException {
        if (!directory.exists() && !directory.mkdirs()) {
            System.err.println("Failed to create loot table directory: " + directory.getAbsolutePath());
            return;
        }

        String lootTableJson = """
{
  "type": "minecraft:chest",
  "pools": [
    {
      "rolls": 5,
      "bonus_rolls": {
        "type": "minecraft:uniform",
        "min": 0,
        "max": 2
      },
      "entries": [
        {
          "type": "minecraft:group",
          "children": [
            {
              "type": "minecraft:item",
              "name": "minecraft:gunpowder",
              "weight": 2,
              "quality": 12,
              "functions": [
                {
                  "function": "minecraft:limit_count",
                  "limit": 4
                }
              ]
            },
            {
              "type": "minecraft:item",
              "name": "bone",
              "weight": 3,
              "quality": 2,
              "functions": [
                {
                  "function": "minecraft:limit_count",
                  "limit": 3
                }
              ]
            },
            {
              "type": "minecraft:item",
              "name": "minecraft:rotten_flesh",
              "weight": 1,
              "quality": 2,
              "functions": [
                {
                  "function": "minecraft:limit_count",
                  "limit": 5
                }
              ]
            }
          ]
        },
        {
          "type": "minecraft:group",
          "children": [
            {
              "type": "minecraft:item",
              "name": "minecraft:dune_armor_trim_smithing_template",
              "weight": 1,
              "quality": 1,
              "functions": [
                {
                  "function": "minecraft:limit_count",
                  "limit": 2
                }
              ]
            },
            {
              "type": "minecraft:item",
              "name": "minecraft:enchanted_book",
              "weight": 1,
              "functions": [
                {
                  "function": "minecraft:enchant_randomly",
                  "only_compatible": false
                }
              ],
              "conditions": []
            },
            {
              "type": "minecraft:item",
              "name": "minecraft:yellow_banner",
              "weight": 1,
              "quality": 1,
              "functions": [
                {
                  "function": "minecraft:limit_count",
                  "limit": 3
                },
                {
                  "function": "minecraft:set_banner_pattern",
                  "patterns": [
                    { "pattern": "minecraft:rhombus", "color": "yellow" },
                    { "pattern": "minecraft:curly_border", "color": "black" },
                    { "pattern": "minecraft:cross", "color": "black" },
                    { "pattern": "minecraft:circle", "color": "black" },
                    { "pattern": "minecraft:flower", "color": "yellow" },
                    { "pattern": "minecraft:triangles_top", "color": "black" },
                    { "pattern": "minecraft:triangles_top", "color": "black" }
                  ],
                  "append": false,
                  "conditions": []
                }
              ]
            }
          ]
        },
        {
          "type": "minecraft:item",
          "name": "sand",
          "weight": 3,
          "functions": [
            {
              "function": "minecraft:limit_count",
              "limit": 6
            }
          ]
        }
      ]
    }
  ]
}
""";

        File outputFile = new File(directory, "dessert.json");
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(lootTableJson);
        } catch (IOException e) {
            System.err.println("Failed to write dessert loot table file: " + e.getMessage());
        }
    }
}