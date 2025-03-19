package de.tomate65.survivalmod.recipes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UniversalRecipeLoader {

    /*private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_DIR = new File("config/survival");
    private static final File RECIPES_FILE = new File(CONFIG_DIR, "recipes.json");

    public static void initialize() {
        // Erstelle das Verzeichnis, falls es nicht existiert
        if (!CONFIG_DIR.exists()) {
            CONFIG_DIR.mkdirs();
        }

        // Überprüfe, ob die Rezeptdatei existiert
        if (!RECIPES_FILE.exists()) {
            // Erstelle eine leere Rezeptdatei, falls sie nicht existiert
            createEmptyRecipesFile();
        }

        // Registriere den Server-Lifecycle-Event, um die Rezepte zu laden
        ServerLifecycleEvents.SERVER_STARTED.register(UniversalRecipeLoader::onServerStarted);
    }

    private static void createEmptyRecipesFile() {
        try (FileWriter writer = new FileWriter(RECIPES_FILE)) {
            // Erstelle ein leeres JSON-Array für die Rezepte
            JsonArray emptyRecipesArray = new JsonArray();
            GSON.toJson(emptyRecipesArray, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void onServerStarted(MinecraftServer server) {
        // Lade die Rezepte, wenn der Server gestartet wird
        loadRecipes(server.getRecipeManager());
    }

    private static void loadRecipes(RecipeManager recipeManager) {
        if (!RECIPES_FILE.exists()) {
            System.err.println("Recipes file not found: " + RECIPES_FILE.getAbsolutePath());
            return;
        }

        try (FileReader reader = new FileReader(RECIPES_FILE)) {
            // Lade die Rezepte aus der Datei
            JsonArray recipesArray = GSON.fromJson(reader, JsonArray.class);

            // Verarbeite jedes Rezept
            for (int i = 0; i < recipesArray.size(); i++) {
                JsonObject recipeJson = recipesArray.get(i).getAsJsonObject();
                // Lade das Rezept in das Minecraft-Rezeptsystem
                loadRecipeIntoGame(recipeManager, recipeJson);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadRecipeIntoGame(RecipeManager recipeManager, JsonObject recipeJson) {
        // Extrahiere den Rezepttyp
        String type = recipeJson.get("type").getAsString();

        // Finde den entsprechenden Rezept-Serializer
        RecipeSerializer<?> serializer = Registries.RECIPE_SERIALIZER.get(Identifier.of(type));
        if (serializer == null) {
            System.err.println("Unknown recipe type: " + type);
            return;
        }

        // Deserialisiere das Rezept
        Recipe<?> recipe = serializer.read(Identifier.of("survivalmod", "custom_recipe_" + recipeJson.hashCode()), recipeJson);
        if (recipe == null) {
            System.err.println("Failed to deserialize recipe: " + recipeJson);
            return;
        }

        // Füge das Rezept dem Rezept-Manager hinzu
        recipeManager.getStonecutterRecipes();

        System.out.println("Successfully loaded recipe: " + recipe.getType().toString());
    }*/ //Viel spaß später noch
}