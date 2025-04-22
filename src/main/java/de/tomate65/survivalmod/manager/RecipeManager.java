//package de.tomate65.survivalmod.manager;
//
//import net.minecraft.recipe.Recipe;
//import net.minecraft.recipe.RecipeEntry;
//import net.minecraft.recipe.ServerRecipeManager;
//import net.minecraft.registry.RegistryKey;
//import net.minecraft.server.MinecraftServer;
//import net.minecraft.util.Identifier;
//
//import java.util.HashSet;
//import java.util.Set;
//
//public class ModRecipeManager {
//    public static Set<String> getModRecipes(MinecraftServer server) {
//        Set<String> modRecipes = new HashSet<>();
//        ServerRecipeManager recipeManager = (ServerRecipeManager) server.getRecipeManager();
//
//        // Get all recipe entries
//        for (RecipeEntry<?> entry : recipeManager.values()) {
//            RegistryKey<Recipe<?>> recipeKey = entry.getKey().orElse(null);
//            if (recipeKey != null) {
//                Identifier recipeId = recipeKey.getValue();
//                if (recipeId.getNamespace().equals("survivalmod")) {
//                    modRecipes.add(recipeId.toString());
//                }
//            }
//        }
//
//        return modRecipes;
//    }
//}