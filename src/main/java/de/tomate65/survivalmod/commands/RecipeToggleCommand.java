//package de.tomate65.survivalmod.commands;
//
//import com.mojang.brigadier.CommandDispatcher;
//import com.mojang.brigadier.context.CommandContext;
//import de.tomate65.survivalmod.config.AnabeldDatapack;
//import net.minecraft.server.command.CommandManager;
//import net.minecraft.server.command.ServerCommandSource;
//import net.minecraft.text.Text;
//
//import java.util.Map;
//import com.mojang.brigadier.arguments.StringArgumentType;
//
//public class RecipeToggleCommand {
//    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
//        dispatcher.register(
//                CommandManager.literal("recipestoggle")
//                        .requires(source -> source.hasPermissionLevel(2)) // OP level 2 required
//                        .then(CommandManager.literal("list")
//                                .executes(RecipeToggleCommand::listRecipes))
//                        .then(CommandManager.literal("toggle")
//                                .then(CommandManager.argument("recipe", StringArgumentType.string())
//                                        .suggests((context, builder) -> {
//                                            AnabeldDatapack.getAllRecipes().keySet().forEach(builder::suggest);
//                                            return builder.buildFuture();
//                                        })
//                                        .executes(context -> {
//                                            String recipeId = StringArgumentType.getString(context, "recipe");
//                                            if (!AnabeldDatapack.getAllRecipes().containsKey(recipeId)) {
//                                                context.getSource().sendError(Text.literal("Unknown recipe: " + recipeId));
//                                                return 0;
//                                            }
//                                            return toggleRecipe(context, recipeId);
//                                        })))
//        );
//    }
//
//    private static int listRecipes(CommandContext<ServerCommandSource> context) {
//        ServerCommandSource source = context.getSource();
//        Map<String, Boolean> recipes = AnabeldDatapack.getAllRecipes();
//
//        if (recipes.isEmpty()) {
//            source.sendFeedback(() -> Text.literal("No recipes found in config."), false);
//            return 0;
//        }
//
//        source.sendFeedback(() -> Text.literal("Available recipes:"), false);
//        recipes.forEach((id, enabled) -> {
//            String status = enabled ? "§aENABLED" : "§cDISABLED";
//            source.sendFeedback(() -> Text.literal(String.format("- %s: %s", id, status)), false);
//        });
//
//        return 1;
//    }
//
//    private static int toggleRecipe(CommandContext<ServerCommandSource> context, String recipeId) {
//        ServerCommandSource source = context.getSource();
//        boolean currentState = AnabeldDatapack.isRecipeEnabled(recipeId);
//        boolean newState = !currentState;
//
//        AnabeldDatapack.setRecipeState(recipeId, newState);
//
//        String status = newState ? "§aenabled" : "§cdisabled";
//        source.sendFeedback(() -> Text.literal(String.format("Recipe %s is now %s", recipeId, status)), true);
//
//        // Reload recipes for all players
//        source.getServer().getPlayerManager().getPlayerList().forEach(player -> {
//            player.unlockRecipes(source.getServer().getRecipeManager().values());
//            //player.resetRecipes(source.getServer().getRecipeManager().values());
//        });
//
//        return 1;
//    }
//}