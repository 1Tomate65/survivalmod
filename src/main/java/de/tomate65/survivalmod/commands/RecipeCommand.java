package de.tomate65.survivalmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import de.tomate65.survivalmod.manager.RecipeHandler;
import de.tomate65.survivalmod.recipes.RecipeGenerator;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RecipeCommand {
    private static final SuggestionProvider<ServerCommandSource> RECIPE_SUGGESTIONS =
            (context, builder) -> {
                // Add wildcard option first
                builder.suggest("*");

                // Add all recipe files (without .json)
                RecipeHandler.getAllRecipeFiles().stream()
                        .map(recipe -> recipe.replace(".json", ""))
                        .forEach(builder::suggest);

                return CompletableFuture.completedFuture(builder.build());
            };

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("recipetoggle")
                .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.literal("enable")
                        .then(CommandManager.argument("recipe", StringArgumentType.string())
                                .suggests(RECIPE_SUGGESTIONS)
                                .executes(context -> {
                                    String recipe = StringArgumentType.getString(context, "recipe");
                                    if (recipe.equals("*")) {
                                        return toggleAllRecipes(context, true);
                                    }
                                    return toggleRecipe(context, true);
                                })))
                .then(CommandManager.literal("disable")
                        .then(CommandManager.argument("recipe", StringArgumentType.string())
                                .suggests(RECIPE_SUGGESTIONS)
                                .executes(context -> {
                                    String recipe = StringArgumentType.getString(context, "recipe");
                                    if (recipe.equals("*")) {
                                        return toggleAllRecipes(context, false);
                                    }
                                    return toggleRecipe(context, false);
                                })))
                .then(CommandManager.literal("list")
                        .executes(RecipeCommand::listRecipes)));
    }

    private static int toggleAllRecipes(CommandContext<ServerCommandSource> context, boolean enable) {
        int count = RecipeHandler.toggleAllRecipes(enable);
        context.getSource().sendFeedback(() -> Text.literal(
                String.format("%s all %d recipes. Run /reload to apply changes.",
                        enable ? "Enabled" : "Disabled",
                        count)
        ), true);
        return count;
    }

    private static int toggleRecipe(CommandContext<ServerCommandSource> context, boolean enable) {
        String recipe = StringArgumentType.getString(context, "recipe");
        boolean success = RecipeHandler.toggleRecipe(recipe, enable);

        if (success) {
            context.getSource().sendFeedback(() -> Text.literal(
                    String.format("Recipe %s %s. Run /reload to apply changes.",
                            recipe,
                            enable ? "enabled" : "disabled")
            ), true);
            return 1;
        } else {
            context.getSource().sendError(Text.literal("Recipe file not found: " + recipe));
            return 0;
        }
    }

    private static int listRecipes(CommandContext<ServerCommandSource> context) {
        List<String> recipes = RecipeHandler.getAllRecipeFiles();
        if (recipes.isEmpty()) {
            context.getSource().sendFeedback(() -> Text.literal("No custom recipes found."), false);
        } else {
            context.getSource().sendFeedback(() -> Text.literal("Available recipes:"), false);
            for (String recipe : recipes) {
                boolean enabled = RecipeHandler.isRecipeEnabled(recipe);
                context.getSource().sendFeedback(() -> Text.literal(
                        String.format("- %s (%s)", recipe, enabled ? "enabled" : "disabled")
                ), false);
            }
        }
        return ((List<?>) recipes).size();
    }
}