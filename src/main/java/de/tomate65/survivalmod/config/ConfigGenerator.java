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
    private static final File SURVIVAL_CONFIG = new File(CONFIG_DIR, "conf.json");
    private static final File TOGGLE_CONFIG = new File(CONFIG_DIR, "toggle.json");
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
        //generateToggleConfig();
    }

    private static void generateSurvivalConfig() {
        if (!SURVIVAL_CONFIG.exists()) {
            try (FileWriter writer = new FileWriter(SURVIVAL_CONFIG)) {
                JsonObject survivalJson = new JsonObject();
                JsonObject commands = new JsonObject();
                JsonObject survival = new JsonObject();
                JsonArray rules = new JsonArray();
                JsonArray info = new JsonArray();

                // Rules as a JSON-Array adden
                rules.add("No griefing");
                rules.add("Be respectful");
                rules.add("Do not cheat");

                // Info as a JSON-Array adden
                info.add("This mod adds 23 new structures");
                info.add("This mod was originally created for a private Minecraft server");
                info.add("I hope it is easy to understand and configure this config file");
                info.add("");
                info.add("Feel free to make suggestions or complain about bugs,");
                info.add("But updates may take a while");

                // join "rules" and "info" as JSON-Array together
                survival.add("info", info);
                survival.add("rules", rules);

                // Structure build
                commands.add("survival", survival);
                survivalJson.add("commands", commands);

                // Debug: Output JSON Structure
                String jsonString = GSON.toJson(survivalJson);

                // Write JSON to the file
                writer.write(jsonString);
                System.out.println("Successfully wrote to " + SURVIVAL_CONFIG.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Error writing to " + SURVIVAL_CONFIG.getAbsolutePath());
                e.printStackTrace();
            }
        }
    }

    private static void generateToggleConfig() {
        if (!TOGGLE_CONFIG.exists()) {
            try (FileWriter writer = new FileWriter(TOGGLE_CONFIG)) {
                JsonObject toggleJson = new JsonObject();
                JsonArray defaultToggles = new JsonArray();

                // Default values for the toggle system as an array
                defaultToggles.add("stone");

                toggleJson.add("toggles", defaultToggles); // Inserting an array into the configuration
                // Debug: JSON-Struktur Output JSON Structure
                String jsonString = GSON.toJson(toggleJson);

                // Write JSON to the file
                writer.write(jsonString);
                System.out.println("Successfully wrote to " + TOGGLE_CONFIG.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Error writing to " + TOGGLE_CONFIG.getAbsolutePath());
                e.printStackTrace();
            }
        }
    }
}