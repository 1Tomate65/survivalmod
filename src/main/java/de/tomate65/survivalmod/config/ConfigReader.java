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

    private static boolean generateRecipes;
    private static boolean generateStructures;
    //private static int numberOfCraftsRemain;

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

            generateRecipes = config.get("Generate Recipes").getAsBoolean();
            generateStructures = config.get("Generate Structures").getAsBoolean();
            //numberOfCraftsRemain = config.get("Number of crafts remain").getAsInt();

            System.out.println("Generate Recipes: " + generateRecipes);
            System.out.println("Generate Structures: " + generateStructures);
            //System.out.println("Number of crafts remain: " + numberOfCraftsRemain);

        } catch (IOException e) {
            System.err.println("Error reading conf config file: " + e.getMessage());
        }
    }
    public static boolean isGenerateRecipes() {
        return generateRecipes;
    }
    public static boolean isGenerateStructures() {
        return generateStructures;
    }
    /*public static int getNumberOfCraftsRemain() {
        return numberOfCraftsRemain;
    }*/
}