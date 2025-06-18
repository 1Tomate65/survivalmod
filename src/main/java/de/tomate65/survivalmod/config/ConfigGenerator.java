package de.tomate65.survivalmod.config;

import com.google.gson.*;
import de.tomate65.survivalmod.manager.TranslationManager;
import de.tomate65.survivalmod.manager.UpdateHelper;
import de.tomate65.survivalmod.recipes.RecipeGenerator;

import java.io.*;
import java.util.List;

import static de.tomate65.survivalmod.Survivalmod.ModVersion; // <-- this is the current Version, 0.3.0

public class ConfigGenerator {
    private static final File CONFIG_DIR = new File("config/survival/" + ModVersion);
    private static final File LANG_DIR = new File("config/survival/" + ModVersion + "/lang");
    private static final File SURVIVAL_CONFIG = new File(CONFIG_DIR, "survival.json");
    private static final File TOGGLE_CONFIG = new File(CONFIG_DIR, "toggle.json");
    private static final File CONF_CONFIG = new File(CONFIG_DIR, "conf.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static final String[] VERSIONS = {
            ModVersion
    };

    public static void generateConfigs() {
        createDirectory(CONFIG_DIR);
        createDirectory(LANG_DIR);

        // Generate language toggle config first
        generateLanguageToggleConfig();

        generateSurvivalConfig();
        generateToggleConfig();
        generateConfConfig();
        RecipeGenerator.generateAllRecipes(new File("config/survival/" + ModVersion + "/recipe"));
        TranslationManager.generateAllTranslations(LANG_DIR);

        ConfigReader.loadConfig();
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
                        "The Mod is translated into 13 languages",
                        "Toggle them with /toggle language language id",
                        "§cPlease Report any translation error on the Issue page on github",
                        " ",
                        "Feel free to suggest improvements"));
                survival.add("changelog", createStringArray(
                        "§7§l§nChangelog §8- §60.3.0 The Structure and balancing Update",
                        "",
                        "§l§6§nAdditions & Removals",
                        "§7- §2Added Swiss German as a Translation",
                        "§7- §2Reworked Config System",
                        "§7- §2Added Loottables - a complete list on Modrinth",
                        "§7- §2Redesigned most Structures",
                        "§7- §2Removed redundant recipes",
                        "§7- §2Added Tall Dry Grass Recipe",
                        "§7- §2Balanced nether_shelter_1 to no longer spawn a ``Netherite Axt`` containing Looting 3 as an Enchantment",
                        "§7- §2Balanced Loottable for most Structures",
                        "",
                        "§l§6§nBug Fixes",
                        "§7- §2Fixed a bug, there in certain languages some translations completely went missing",
                        "§7- §2Fixed a bug, there the nether shelters spawned on the nether roof",
                        "§7- §2Fixed a bug, there identical structures spawned right next to each other",
                        "§7- §2Fixed a bugs, As they came and couldn't be noticed",
                        "",
                        "§l§6§nThanks",
                        "§9A thank you, to Desonetos for translating the language",
                        "§9A thank you, to This_Pluto for Hearing me out and suggesting and testing unofficial versions",
                        "§9A thank you, to TheCityCrafter, EinFynn, wchtig_, Fortex and Connplay for reading the Modrinth Description and giveing me feedback",
                        "even thou I ask you nicely"

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

                config.addProperty("ModVersion", ModVersion);

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

    private static void generateLanguageToggleConfig() {
        File languageToggleFile = new File(LANG_DIR, "language_files_toggle.json");
        if (!languageToggleFile.exists()) {
            try (FileWriter writer = new FileWriter(languageToggleFile)) {
                JsonObject config = new JsonObject();
                List<String> languageCodes = TranslationManager.getAvailableLanguageCodes();

                for (String langCode : languageCodes) {
                    // Use the proper underscored codes from TranslationManager
                    boolean isDefault = langCode.equals(ConfigReader.getDefaultLanguage());
                    config.addProperty(langCode, isDefault);
                }

                writer.write(GSON.toJson(config));
            } catch (IOException e) {
                System.err.println("Error creating language toggle config: " + e.getMessage());
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