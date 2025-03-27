package de.tomate65.survivalmod.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ConfigReader {
    private static final File SURVIVAL_CONFIG_FILE = new File("config/survival/survival.json");
    private static final File CONF_CONFIG_FILE = new File("config/survival/conf.json");

    // Config fields with default values
    private static boolean generateRecipes = false;
    private static boolean generateStructures = false;
    private static boolean toggleCommandEnabled = false;
    private static String defaultStatCategory = "mined"; // Fallback if not in config

    public static void loadConfig() {
        if (!SURVIVAL_CONFIG_FILE.exists()) {
            System.err.println("Config file does not exist: " + SURVIVAL_CONFIG_FILE.getAbsolutePath());
            return;
        }

        try (FileReader reader = new FileReader(SURVIVAL_CONFIG_FILE)) {
            JsonObject config = JsonParser.parseReader(reader).getAsJsonObject();
            JsonObject commands = config.getAsJsonObject("commands");
            JsonObject survival = commands.getAsJsonObject("survival");

            JsonArray info = survival.getAsJsonArray("info");
            for (int i = 0; i < info.size(); i++) {
                System.out.println("Info " + (i + 1) + ": " + info.get(i).getAsString());
            }

            JsonArray rules = survival.getAsJsonArray("rules");
            for (int i = 0; i < rules.size(); i++) {
                System.out.println("Rule " + (i + 1) + ": " + rules.get(i).getAsString());
            }
        } catch (IOException e) {
            System.err.println("Error reading config file: " + e.getMessage());
        }

        loadConfConfig();
    }

    private static void loadConfConfig() {
        if (!CONF_CONFIG_FILE.exists()) {
            System.err.println("Config file does not exist: " + CONF_CONFIG_FILE.getAbsolutePath());
            return;
        }

        try (FileReader reader = new FileReader(CONF_CONFIG_FILE)) {
            JsonObject config = JsonParser.parseReader(reader).getAsJsonObject();

            // Safely read values with fallbacks
            generateRecipes = config.has("Generate Recipes")
                    ? config.get("Generate Recipes").getAsBoolean()
                    : false;

            generateStructures = config.has("Generate Structures")
                    ? config.get("Generate Structures").getAsBoolean()
                    : false;

            toggleCommandEnabled = config.has("Toggle Command")
                    ? config.get("Toggle Command").getAsBoolean()
                    : false;

            defaultStatCategory = config.has("Default Statistik Category")
                    ? config.get("Default Statistik Category").getAsString()
                    : "mined";

            System.out.println("Generate Recipes: " + generateRecipes);
            System.out.println("Generate Structures: " + generateStructures);
            System.out.println("Toggle Command Enabled: " + toggleCommandEnabled);
            System.out.println("Default Stat Category: " + defaultStatCategory);

        } catch (IOException e) {
            System.err.println("Error reading conf config file: " + e.getMessage());
        }
    }

    // Getters
    public static boolean isGenerateRecipes() {
        return generateRecipes;
    }

    public static boolean isGenerateStructures() {
        return generateStructures;
    }

    public static boolean isToggleCommandEnabled() {
        return toggleCommandEnabled;
    }

    public static String getDefaultStatCategory() {
        return defaultStatCategory;
    }
}