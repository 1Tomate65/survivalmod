package de.tomate65.survivalmod.config;

import com.google.gson.*;
import de.tomate65.survivalmod.translation.TranslationManager;

import java.io.*;

public class ConfigGenerator {
    private static final File CONFIG_DIR = new File("config/survival");
    private static final File LANG_DIR = new File("config/survival/lang");
    private static final File SURVIVAL_CONFIG = new File(CONFIG_DIR, "survival.json");
    private static final File TOGGLE_CONFIG = new File(CONFIG_DIR, "toggle.json");
    private static final File CONF_CONFIG = new File(CONFIG_DIR, "conf.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void generateConfigs() {
        createDirectory(CONFIG_DIR);
        createDirectory(LANG_DIR);

        generateSurvivalConfig();
        generateToggleConfig();
        generateConfConfig();
        TranslationManager.generateAllTranslations(LANG_DIR);
    }


    private static void createDirectory(File dir) {
        if (!dir.exists() && !dir.mkdirs()) {
            System.err.println("Failed to create directory: " + dir.getAbsolutePath());
        }
    }

    private static void generateSurvivalConfig() {
        if (!SURVIVAL_CONFIG.exists()) {
            try (FileWriter writer = new FileWriter(SURVIVAL_CONFIG)) {
                JsonObject config = new JsonObject();
                JsonObject commands = new JsonObject();
                JsonObject survival = new JsonObject();

                survival.add("rules", createStringArray("No griefing", "Be respectful", "Do not cheat"));
                survival.add("info", createStringArray("",
                        "This mod adds: structures, recipes and two commands",
                        " ",
                        "Originally created for a private server",
                        " ",
                        "The Mod is translated into 10 languages",
                        "Toggle them with /toggle language language id",
                        "§cPlease Report any translation error on the Issue page on github",
                        " ",
                        "Feel free to suggest improvements"));
                survival.add("changelog", createStringArray(
                        "§7§l§nChangelog",
                        "",
                        "§7-§r §aFix Color Command again",
                        "§7-§r §aChanged command feedback to be translatable through the toggle command",
                        "§7-§r §aAdded Chines, Hindu, Pirat Speak and Russian as a translation"
                ));

                commands.add("survival", survival);
                config.add("commands", commands);

                writer.write(GSON.toJson(config));
            } catch (IOException e) {
                System.err.println("Error creating survival config: " + e.getMessage());
            }
        }
    }

    public static void generateToggleConfig() {
        if (!TOGGLE_CONFIG.exists()) {
            try (FileWriter writer = new FileWriter(TOGGLE_CONFIG)) {
                JsonObject config = new JsonObject();
                JsonArray toggles = new JsonArray();

                toggles.add("stone");
                toggles.add("dirt");
                toggles.add("oak_log");
                toggles.add("timeplayed");

                config.add("toggles", toggles);
                writer.write(GSON.toJson(config));
            } catch (IOException e) {
                System.err.println("Error creating toggle config: " + e.getMessage());
            }
        }
    }

    private static void generateConfConfig() {
        if (!CONF_CONFIG.exists()) {
            try (FileWriter writer = new FileWriter(CONF_CONFIG)) {
                JsonObject config = new JsonObject();

                // Toggle Settings
                config.addProperty("ChatMsgFrequency", 10);
                config.addProperty("Toggle Command", true);
                config.addProperty("Default Statistik Category", "mined");
                config.addProperty("InvertToggleFile", false);

                // Magnet settings
                JsonObject magnetSettings = new JsonObject();
                magnetSettings.addProperty("enabled", false);
                magnetSettings.addProperty("hungerStrength", 5);
                magnetSettings.addProperty("radius", 5);
                config.add("magnet", magnetSettings);

                // Color settings
                config.addProperty("default_text_color", "GRAY");
                config.addProperty("default_category_color", "GRAY");
                config.addProperty("default_material_color", "GRAY");
                config.addProperty("default_number_color", "GOLD");
                config.addProperty("default_time_color", "AQUA");

                // Comments
                config.addProperty("#1", "Valid stats: mined, crafted, used, broken, picked_up, dropped");
                config.addProperty("#2", "InvertToggleFile: true makes toggle.json a blocklist instead of allowlist");

                writer.write(GSON.toJson(config));
            } catch (IOException e) {
                System.err.println("Error creating conf config: " + e.getMessage());
            }
        }
    }

    private static JsonArray createStringArray(String... elements) {
        JsonArray array = new JsonArray();
        for (String element : elements) {
            array.add(element);
        }
        return array;
    }
}