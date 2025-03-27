package de.tomate65.survivalmod.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigGenerator {
    private static final File CONFIG_DIR = new File("config/survival");
    private static final File CONFIG_DIR_recipe = new File("config/survival/recipe");
    private static final File SURVIVAL_CONFIG = new File(CONFIG_DIR, "survival.json");
    private static final File TOGGLE_CONFIG = new File(CONFIG_DIR, "toggle.json");
    private static final File CONF_CONFIG = new File(CONFIG_DIR, "conf.json"); // Neue Konfigurationsdatei
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void generateConfigs() {
        if (!CONFIG_DIR.exists()) {
            boolean dirsCreated = CONFIG_DIR.mkdirs();
            if (!dirsCreated) {
                System.err.println("Failed to create config directory: " + CONFIG_DIR.getAbsolutePath());
                return;
            }
        }

        generateSurvivalConfig();
        generateToggleConfig();
        generateConfConfig(); // Generiere die neue Konfigurationsdatei

        if (!CONFIG_DIR_recipe.exists()) {
            boolean dirsCreated = CONFIG_DIR_recipe.mkdirs();
            if (!dirsCreated) {
                System.err.println("Failed to create config directory: " + CONFIG_DIR_recipe.getAbsolutePath());
                return;
            }
        }
        // Here the recipe register
    }

    private static void generateSurvivalConfig() {
        if (!SURVIVAL_CONFIG.exists()) {
            try (FileWriter writer = new FileWriter(SURVIVAL_CONFIG)) {
                JsonObject survivalJson = new JsonObject();
                JsonObject commands = new JsonObject();
                JsonObject survival = new JsonObject();
                JsonArray rules = new JsonArray();
                JsonArray info = new JsonArray();
                JsonArray changelog = new JsonArray();

                rules.add("No griefing");
                rules.add("Be respectful");
                rules.add("Do not cheat");

                info.add("This mod has a default of 24 new structures");
                info.add("This mod has a default of 50 new recipes");
                info.add("This mod was originally created for a private Minecraft server");
                info.add("I hope it is easy to understand and configure this config file");
                info.add("");
                info.add("Feel free to make suggestions or complain about bugs,");
                info.add("");
                info.add("This is the mod Version a");
                info.add("");
                info.add("§6§nFeatures in Progress");
                info.add("§8- §r/toggle");
                info.add("§7 - §rits unusable for an unoped player");
                info.add("§8- §rRecipes per config file");
                info.add("§7 - §rDoesn't work currently");

                changelog.add("§7§l§nChangelog");
                changelog.add("");
                changelog.add("Nothings changed it seems");
                changelog.add("O' I know,");
                changelog.add("They added the changelog in this update");

                survival.add("rules", rules);
                survival.add("info", info);
                survival.add("changelog", changelog);

                commands.add("survival", survival);
                survivalJson.add("commands", commands);

                String jsonString = GSON.toJson(survivalJson);

                writer.write(jsonString);
                System.out.println("Successfully wrote to " + SURVIVAL_CONFIG.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Error writing to " + SURVIVAL_CONFIG.getAbsolutePath());
                e.printStackTrace();
            }
        }
    }

    public static void generateToggleConfig() {
        if (!TOGGLE_CONFIG.exists()) {
            try (FileWriter writer = new FileWriter(TOGGLE_CONFIG)) {
                JsonObject toggleJson = new JsonObject();
                JsonArray defaultToggles = new JsonArray();

                defaultToggles.add("stone");

                toggleJson.add("toggles", defaultToggles);
                String jsonString = GSON.toJson(toggleJson);

                writer.write(jsonString);
                System.out.println("Successfully wrote to " + TOGGLE_CONFIG.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Error writing to " + TOGGLE_CONFIG.getAbsolutePath());
                e.printStackTrace();
            }
        }
    }

    private static void generateConfConfig() {
        if (!CONF_CONFIG.exists()) {
            try (FileWriter writer = new FileWriter(CONF_CONFIG)) {
                JsonObject confJson = new JsonObject();

                // Example configuration values
                confJson.addProperty("Generate Recipes", false);
                confJson.addProperty("Generate Structures", false);
                confJson.addProperty("Toggle Command", false);
                confJson.addProperty("Number of crafts remain", -1);

                // Add the commented commands as actual properties with comments as values
                confJson.addProperty("#1", "Valid Default Statistics are:");
                confJson.addProperty("#2", "mined, crafted, used, broken, picked_up, dropped, killed, killed_by, custom");
                confJson.addProperty("Default Statistik Category", "mined");

                String jsonString = GSON.toJson(confJson);

                writer.write(jsonString);
                System.out.println("Successfully wrote to " + CONF_CONFIG.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Error writing to " + CONF_CONFIG.getAbsolutePath());
                e.printStackTrace();
            }
        }
    }
}