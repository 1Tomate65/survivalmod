package de.tomate65.survivalmod.recipes;

import java.util.Map;

import static de.tomate65.survivalmod.recipes.RecipeGenerator.generateBlastingRecipe;

public class RecipeBuilder {
    // public static void main(String arg[]){Recipe();}
    public static void Recipe()
        {
        String[][] pattern1 = { {"A", "A", "A"}, {"A", "B", "A"}, {"A", "A", "A"} };
        String[][] pattern2 = { {"A"}, {"B"} };
        String[][] pattern3 = { {"A"}, {"A"} };
        Map<Character, String> key1 = Map.of(
                'A', "minecraft:glass_pane",
                'B', "minecraft:torch"
        );
        Map<Character, String> key2 = Map.of(
                'A', "minecraft:chiseled_stone_bricks",
                'B', "minecraft:netherite_ingot"
        );
        Map<Character, String> key3 = Map.of(
                'A', "minecraft:chiseled_stone_bricks",
                'B', "minecraft:iron_ingot"
        );
        Map<Character, String> key4 = Map.of(
                'A', "minecraft:obsidian",
                'B', "minecraft:dragon_breath"
        );
        Map<Character, String> key5 = Map.of(
                'A', "minecraft:short_grass",
                'B', "minecraft:dirt"
        );
        Map<Character, String> key6 = Map.of(
                'A', "minecraft:fern"
        );
        Map<Character, String> key7 = Map.of(
                'A', "minecraft:short_grass"
        );


        RecipeGenerator.generateShapedRecipe(
                "minecraft:light",
                1,
                pattern1,
                key1,
                "./shaped_recipe.json"
        );
        RecipeGenerator.generateShapedRecipe(
                "minecraft:lodestone",
                4,
                pattern1,
                key2,
                "./shaped_recipe.json"
        );        RecipeGenerator.generateShapedRecipe(
                "minecraft:lodestone",
                1,
                pattern1,
                key3,
                "./shaped_recipe.json"
        );
        RecipeGenerator.generateShapedRecipe(
                "minecraft:crying_obsidian",
                1,
                pattern1,
                key4,
                "./shaped_recipe.json"
        );
        RecipeGenerator.generateShapedRecipe(
                "minecraft:grass_block",
                1,
                pattern2,
                key5,
                "./shaped_recipe.json"
        );
        RecipeGenerator.generateShapedRecipe(
                "minecraft:large_fern",
                1,
                pattern3,
                key6,
                "./shaped_recipe.json"
        );
        RecipeGenerator.generateShapedRecipe(
                "minecraft:large_fern",
                1,
                pattern3,
                key7,
                "./shaped_recipe.json"
        );


        RecipeGenerator.generateBlastingRecipe("minecraft:raw_copper_block", "minecraft:copper_block", 2.25f, 250, "./blasting_recipe.json");
        RecipeGenerator.generateBlastingRecipe("minecraft:raw_iron_block", "minecraft:iron_block", 2.25f, 250, "./blasting_recipe.json");
        RecipeGenerator.generateBlastingRecipe("minecraft:raw_gold_block", "minecraft:gold_block", 2.25f, 250, "./blasting_recipe.json");


        RecipeGenerator.generateSmithingRecipe("minecraft:netherite_upgrade_smithing_template", "minecraft:bolt_armor_trim_smithing_template",       "minecraft:netherite_ingot", "minecraft:bolt_armor_trim_smithing_template",  2, "./smithing_recipe.json");
        RecipeGenerator.generateSmithingRecipe("minecraft:netherite_upgrade_smithing_template", "minecraft:coast_armor_trim_smithing_template",      "minecraft:netherite_ingot", "minecraft:coast_armor_trim_smithing_template", 2, "./smithing_recipe.json");
        RecipeGenerator.generateSmithingRecipe("minecraft:netherite_upgrade_smithing_template", "minecraft:golden_horse_armor",                      "minecraft:diamond_block",   "minecraft:diamond_horse_armor",                1, "./smithing_recipe.json");
        RecipeGenerator.generateSmithingRecipe("minecraft:netherite_upgrade_smithing_template", "minecraft:dune_armor_trim_smithing_template",       "minecraft:netherite_ingot", "minecraft:dune_armor_trim_smithing_template",  2, "./smithing_recipe.json");
        RecipeGenerator.generateSmithingRecipe("minecraft:netherite_upgrade_smithing_template", "minecraft:eye_armor_trim_smithing_template",        "minecraft:netherite_ingot", "minecraft:eye_armor_trim_smithing_template",   2, "./smithing_recipe.json");
        RecipeGenerator.generateSmithingRecipe("minecraft:netherite_upgrade_smithing_template", "minecraft:flow_armor_trim_smithing_template",       "minecraft:netherite_ingot", "minecraft:flow_armor_trim_smithing_template",  2, "./smithing_recipe.json");
        RecipeGenerator.generateSmithingRecipe("minecraft:netherite_upgrade_smithing_template", "minecraft:iron_horse_armor",                        "minecraft:gold_block",      "minecraft:golden_horse_armor",                 1, "./smithing_recipe.json");
        RecipeGenerator.generateSmithingRecipe("minecraft:netherite_upgrade_smithing_template", "minecraft:host_armor_trim_smithing_template",       "minecraft:netherite_ingot", "minecraft:host_armor_trim_smithing_template",  2, "./smithing_recipe.json");
        RecipeGenerator.generateSmithingRecipe("minecraft:netherite_upgrade_smithing_template", "minecraft:leather_horse_armor",                     "minecraft:iron_block",      "minecraft:iron_horse_armor",                   1, "./smithing_recipe.json");
        RecipeGenerator.generateSmithingRecipe("minecraft:netherite_upgrade_smithing_template", "minecraft:raiser_armor_trim_smithing_template",     "minecraft:netherite_ingot", "minecraft:raiser_armor_trim_smithing_template", 2, "./smithing_recipe.json");
        RecipeGenerator.generateSmithingRecipe("minecraft:netherite_upgrade_smithing_template", "minecraft:rib_armor_trim_smithing_template",        "minecraft:netherite_ingot", "minecraft:rib_armor_trim_smithing_template", 2, "./smithing_recipe.json");
        RecipeGenerator.generateSmithingRecipe("minecraft:netherite_upgrade_smithing_template", "minecraft:sentry_armor_trim_smithing_template",     "minecraft:netherite_ingot", "minecraft:sentry_armor_trim_smithing_template", 2, "./smithing_recipe.json");
        RecipeGenerator.generateSmithingRecipe("minecraft:netherite_upgrade_smithing_template", "minecraft:shaper_armor_trim_smithing_template",     "minecraft:netherite_ingot", "minecraft:shaper_armor_trim_smithing_template", 2, "./smithing_recipe.json");
        RecipeGenerator.generateSmithingRecipe("minecraft:netherite_upgrade_smithing_template", "minecraft:silence_armor_trim_smithing_template",    "minecraft:netherite_ingot", "minecraft:silence_armor_trim_smithing_template", 2, "./smithing_recipe.json");
        RecipeGenerator.generateSmithingRecipe("minecraft:netherite_upgrade_smithing_template", "minecraft:snout_armor_trim_smithing_template",      "minecraft:netherite_ingot", "minecraft:snout_armor_trim_smithing_template", 2, "./smithing_recipe.json");
        RecipeGenerator.generateSmithingRecipe("minecraft:netherite_upgrade_smithing_template", "minecraft:spire_armor_trim_smithing_template",      "minecraft:netherite_ingot", "minecraft:spire_armor_trim_smithing_template", 2, "./smithing_recipe.json");
        RecipeGenerator.generateSmithingRecipe("minecraft:netherite_upgrade_smithing_template", "minecraft:tide_armor_trim_smithing_template",       "minecraft:netherite_ingot", "minecraft:tide_armor_trim_smithing_template", 2, "./smithing_recipe.json");
        RecipeGenerator.generateSmithingRecipe("minecraft:netherite_upgrade_smithing_template", "minecraft:netherite_upgrade_smithing_template",     "minecraft:netherite_ingot", "minecraft:netherite_upgrade_smithing_template", 2, "./smithing_recipe.json");
        RecipeGenerator.generateSmithingRecipe("minecraft:netherite_upgrade_smithing_template", "minecraft:vex_armor_trim_smithing_template",        "minecraft:netherite_ingot", "minecraft:vex_armor_trim_smithing_template", 2, "./smithing_recipe.json");
        RecipeGenerator.generateSmithingRecipe("minecraft:netherite_upgrade_smithing_template", "minecraft:ward_armor_trim_smithing_template",       "minecraft:netherite_ingot", "minecraft:ward_armor_trim_smithing_template", 2, "./smithing_recipe.json");
        RecipeGenerator.generateSmithingRecipe("minecraft:netherite_upgrade_smithing_template", "minecraft:wayfinder_armor_trim_smithing_template",  "minecraft:netherite_ingot", "minecraft:wayfinder_armor_trim_smithing_template", 2, "./smithing_recipe.json");
        RecipeGenerator.generateSmithingRecipe("minecraft:netherite_upgrade_smithing_template", "minecraft:wild_armor_trim_smithing_template",       "minecraft:netherite_ingot", "minecraft:wild_armor_trim_smithing_template", 2, "./smithing_recipe.json");
    }
}