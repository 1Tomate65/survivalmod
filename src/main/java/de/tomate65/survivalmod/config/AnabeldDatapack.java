//package de.tomate65.survivalmod.config;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import de.tomate65.survivalmod.manager.RecipeManager;
//import net.fabricmc.loader.api.FabricLoader;
//import net.minecraft.server.MinecraftServer;
//import net.minecraft.util.Identifier;
//
//import java.io.File;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Set;
//
//public class AnabeldDatapack {
//    private static final File RECIPES_CONFIG_DIR = new File(FabricLoader.getInstance().getConfigDir().toFile(), "survival/recipe");
//    private static final File RECIPES_CONFIG = new File(RECIPES_CONFIG_DIR, "recipes.json");
//    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
//    private static Map<String, Boolean> recipeStates = new HashMap<>();
//
//    public static void initialize(MinecraftServer server) {
//        if (!RECIPES_CONFIG_DIR.exists()) {
//            RECIPES_CONFIG_DIR.mkdirs();
//        }
//
//        if (RECIPES_CONFIG.exists()) {
//            loadConfig();
//        } else {
//            generateDefaultConfig(server);
//        }
//    }
//
//    private static void generateDefaultConfig(MinecraftServer server) {
//        JsonObject defaultConfig = new JsonObject();
//        Set<Identifier> modRecipes = RecipeManager.getModRecipes(server);
//
//        for (Identifier recipeId : modRecipes) {
//            defaultConfig.addProperty(recipeId.toString(), true);
//        }
//
//        try (FileWriter writer = new FileWriter(RECIPES_CONFIG)) {
//            GSON.toJson(defaultConfig, writer);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        loadConfig();
//    }
//
//    private static void loadConfig() {
//        try (FileReader reader = new FileReader(RECIPES_CONFIG)) {
//            JsonObject config = JsonParser.parseReader(reader).getAsJsonObject();
//            recipeStates.clear();
//
//            for (String key : config.keySet()) {
//                recipeStates.put(key, config.get(key).getAsBoolean());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void saveConfig() {
//        JsonObject config = new JsonObject();
//        recipeStates.forEach(config::addProperty);
//
//        try (FileWriter writer = new FileWriter(RECIPES_CONFIG)) {
//            GSON.toJson(config, writer);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static boolean isRecipeEnabled(String recipeId) {
//        return recipeStates.getOrDefault(recipeId, true);
//    }
//
//    public static void setRecipeState(String recipeId, boolean enabled) {
//        recipeStates.put(recipeId, enabled);
//        saveConfig();
//    }
//
//    public static Map<String, Boolean> getAllRecipes() {
//        return new HashMap<>(recipeStates);
//    }
//}