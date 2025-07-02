package de.tomate65.survivalmod.recipes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import de.tomate65.survivalmod.config.ConfigReader;
import de.tomate65.survivalmod.manager.RecipeHandler;
import net.minecraft.server.command.ServerCommandSource;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RecipeGenerator {
    private static final List<RecipeGeneratorHelper> helpers = new ArrayList<>();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    static {
        // Crafting recipes
        helpers.add(new BetterLoadStoneRecipe());
        helpers.add(new InvisibleItemFrameRecipe());
        helpers.add(new InvisibleGlowItemFrameRecipe());
        helpers.add(new LightBlockRecipe());
        helpers.add(new GrassBlockRecipe());
        helpers.add(new CryingObsidianRecipe());
        helpers.add(new LargeFernRecipe());
        helpers.add(new TallGrassRecipe());
        helpers.add(new TallDryGrassRecipe());

        // Blasting recipes
        helpers.add(new CopperBlockBlastingRecipe());
        helpers.add(new GoldBlockBlastingRecipe());
        helpers.add(new IronBlockBlastingRecipe());

        // Stonecutting recipes
        helpers.add(new StonecuttingLight0Recipe());
        helpers.add(new StonecuttingLight1Recipe());
        helpers.add(new StonecuttingLight2Recipe());
        helpers.add(new StonecuttingLight3Recipe());
        helpers.add(new StonecuttingLight4Recipe());
        helpers.add(new StonecuttingLight5Recipe());
        helpers.add(new StonecuttingLight6Recipe());
        helpers.add(new StonecuttingLight7Recipe());
        helpers.add(new StonecuttingLight8Recipe());
        helpers.add(new StonecuttingLight9Recipe());
        helpers.add(new StonecuttingLight10Recipe());
        helpers.add(new StonecuttingLight11Recipe());
        helpers.add(new StonecuttingLight12Recipe());
        helpers.add(new StonecuttingLight13Recipe());
        helpers.add(new StonecuttingLight14Recipe());
        helpers.add(new StonecuttingLight15Recipe());

        helpers.add(new SmithingBoltArmorTrimRecipe());
        helpers.add(new SmithingUpgradeTemplateRecipe());
        helpers.add(new SmithingIronHorseArmorRecipe());
        helpers.add(new SmithingGoldHorseArmorRecipe());
        helpers.add(new SmithingDiamondHorseArmorRecipe());
        helpers.add(new SmithingCoastArmorTrimRecipe());
        helpers.add(new SmithingFlowArmorTrimRecipe());
        helpers.add(new SmithingHostArmorTrimRecipe());
        helpers.add(new SmithingDuneArmorTrimRecipe());
        helpers.add(new SmithingEyeArmorTrimRecipe());
        helpers.add(new SmithingRaiserArmorTrimRecipe());
        helpers.add(new SmithingRibArmorTrimRecipe());
        helpers.add(new SmithingSentryArmorTrimRecipe());
        helpers.add(new SmithingShaperArmorTrimRecipe());
        helpers.add(new SmithingSilenceArmorTrimRecipe());
        helpers.add(new SmithingSnoutArmorTrimRecipe());
        helpers.add(new SmithingSpireArmorTrimRecipe());
        helpers.add(new SmithingTideArmorTrimRecipe());
        helpers.add(new SmithingVexArmorTrimRecipe());
        helpers.add(new SmithingWardArmorTrimRecipe());
        helpers.add(new SmithingWayfinderArmorTrimRecipe());
        helpers.add(new SmithingWildArmorTrimRecipe());
    }

    public static void generateAllRecipes(File recipeDir) {
        if (!recipeDir.exists() && !recipeDir.mkdirs()) {
            System.err.println("[SurvivalMod] Failed to create recipe directory at: " + recipeDir.getAbsolutePath());
            return;
        }

        int generatedCount = 0;
        for (RecipeGeneratorHelper helper : helpers) {
            String recipeId = getRecipeIdFromHelper(helper);
            if (ConfigReader.isRecipeEnabled(recipeId)) {
                try {
                    helper.generateRecipeFile(recipeDir);
                    generatedCount++;
                } catch (Exception e) {
                    System.err.println("[SurvivalMod] Failed to generate recipe " + recipeId + ": " + e.getMessage());
                }
            }
        }

        System.out.println("[SurvivalMod] Successfully generated " + generatedCount + " recipes");
    }

    private static String getRecipeIdFromHelper(RecipeGeneratorHelper helper) {
        // Example: BetterLoadStoneRecipe -> better_load_stone
        String className = helper.getClass().getSimpleName();
        return className.replace("Recipe", "")
                .replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }

    public static List<String> getAvailableRecipeIds() {
        List<String> ids = new ArrayList<>();
        for (RecipeGeneratorHelper helper : helpers) {
            ids.add(getRecipeIdFromHelper(helper));
        }
        return ids;
    }

    public static void writeRecipeFile(File outputFile, Object recipeData) throws IOException {
        outputFile.getParentFile().mkdirs();
        try (FileWriter writer = new FileWriter(outputFile)) {
            GSON.toJson(recipeData, writer);
        }
    }

    private static final SuggestionProvider<ServerCommandSource> RECIPE_SUGGESTIONS =
            (context, builder) -> {
                // Add wildcard option first
                builder.suggest("*");

                // Then add all recipes
                List<String> recipes = RecipeHandler.getAllRecipeFiles();
                for (String recipe : recipes) {
                    String recipeId = recipe.replace(".json", "");
                    builder.suggest(recipeId);
                }
                return CompletableFuture.completedFuture(builder.build());
            };
}
