package de.tomate65.survivalmod.config;

import com.google.gson.*;
import de.tomate65.survivalmod.manager.MagnetManager;

import java.io.*;
import java.util.*;

public class ConfigReader {
    private static final File CONF_CONFIG = new File("config/survival/conf.json");
    private static final File LANG_DIR = new File("config/survival/lang");

    // Configuration values with defaults
    private static boolean toggleCommandEnabled = true;
    private static boolean invertToggleFile = false;
    private static int chatMsgFrequency = 10;
    private static String modVersion = "0.3.0";
    private static final Map<String, Boolean> languageToggles = new HashMap<>();
    private static String defaultStatCategory = "mined";
    private static String defaultLanguage = "en_us";
    private static String defaultTextColor = "GRAY";
    private static String defaultCategoryColor = "GRAY";
    private static String defaultMaterialColor = "GRAY";
    private static String defaultNumberColor = "GOLD";
    private static String defaultTimeColor = "AQUA";

    private static final Map<String, Map<String, String>> translations = new HashMap<>();

    public static void loadConfig() {
        loadConfConfig();
        loadLanguageToggleConfig();  // Add this line
        loadTranslations();
    }

    private static void loadConfConfig() {
        if (!CONF_CONFIG.exists()) return;

        try (FileReader reader = new FileReader(CONF_CONFIG)) {
            JsonObject config = JsonParser.parseReader(reader).getAsJsonObject();

            modVersion = config.get("ModVersion").getAsString();

            // Load basic settings
            chatMsgFrequency = config.get("ChatMsgFrequency").getAsInt();
            toggleCommandEnabled = config.get("Toggle Command").getAsBoolean();
            defaultStatCategory = config.get("Default Statistik Category").getAsString();


            // Load toggle mode
            if (config.has("InvertToggleFile")) {
                invertToggleFile = config.get("InvertToggleFile").getAsBoolean();
            }

            // Load colors
            defaultTextColor = config.get("default_text_color").getAsString();
            defaultCategoryColor = config.get("default_category_color").getAsString();
            defaultMaterialColor = config.get("default_material_color").getAsString();
            defaultNumberColor = config.get("default_number_color").getAsString();
            defaultTimeColor = config.get("default_time_color").getAsString();



            // Load magnet settings
            JsonObject magnetSettings = config.has("magnet") ? config.getAsJsonObject("magnet") : new JsonObject();
            boolean allowMagnet = magnetSettings.has("enabled") && magnetSettings.get("enabled").getAsBoolean();
            int hungerStrength = magnetSettings.has("hungerStrength") ? magnetSettings.get("hungerStrength").getAsInt() : 5;
            int radius = magnetSettings.has("radius") ? magnetSettings.get("radius").getAsInt() : 5;
            MagnetManager.setConfigValues(allowMagnet, hungerStrength, radius);

        } catch (Exception e) {
            System.err.println("Error reading conf config: " + e.getMessage());
        }
    }

    private static void loadTranslations() {
        translations.clear();
        File[] langFiles = LANG_DIR.listFiles((dir, name) -> name.endsWith(".json"));
        if (langFiles == null) return;

        for (File langFile : langFiles) {
            String langCode = langFile.getName().replace(".json", "");
            try (FileReader reader = new FileReader(langFile)) {
                JsonObject langData = JsonParser.parseReader(reader).getAsJsonObject();
                Map<String, String> langMap = new HashMap<>();

                for (Map.Entry<String, JsonElement> entry : langData.entrySet()) {
                    langMap.put(entry.getKey(), entry.getValue().getAsString());
                }

                translations.put(langCode, langMap);
            } catch (Exception e) {
                System.err.println("Error loading language file: " + langFile.getName());
            }
        }
    }

    public static Set<String> getAvailableLanguages() {
        return translations.keySet();
    }

    public static String translate(String key, String langCode) {
        Map<String, String> lang = translations.getOrDefault(langCode, translations.get(defaultLanguage));
        return lang != null ? lang.getOrDefault(key, key) : key;
    }

    // Getters
    public static boolean isToggleCommandEnabled() { return toggleCommandEnabled; }
    public static int getChatMsgFrequency() { return chatMsgFrequency; }
    public static String getModVersion() {return modVersion; }
    public static String getDefaultStatCategory() { return defaultStatCategory; }
    public static String getDefaultLanguage() { return defaultLanguage; }
    public static String getDefaultTextColor() { return defaultTextColor; }
    public static String getDefaultCategoryColor() { return defaultCategoryColor; }
    public static String getDefaultMaterialColor() { return defaultMaterialColor; }
    public static String getDefaultNumberColor() { return defaultNumberColor; }
    public static boolean isInvertedToggleMode() { return invertToggleFile; }
    public static String getDefaultTimeColor() { return defaultTimeColor; }
    public static boolean isLanguageEnabled(String langCode) {
        return languageToggles.getOrDefault(langCode, langCode.equals("en_us"));
    }

    private static void loadLanguageToggleConfig() {
        File languageToggleFile = new File("config/survival/lang/language_files_toggle.json");
        if (!languageToggleFile.exists()) return;

        try (FileReader reader = new FileReader(languageToggleFile)) {
            JsonObject config = JsonParser.parseReader(reader).getAsJsonObject();
            languageToggles.clear();

            for (Map.Entry<String, JsonElement> entry : config.entrySet()) {
                languageToggles.put(entry.getKey(), entry.getValue().getAsBoolean());
            }
        } catch (Exception e) {
            System.err.println("Error reading language toggle config: " + e.getMessage());
        }
    }

    public static boolean isRecipeEnabled(String recipeId) {
        // Check if the recipe config file exists
        File recipeConfigFile = new File("config/survival/recipes.json");
        if (!recipeConfigFile.exists()) {
            // If config file doesn't exist, enable all recipes by default
            return true;
        }

        try (FileReader reader = new FileReader(recipeConfigFile)) {
            JsonObject config = JsonParser.parseReader(reader).getAsJsonObject();

            // Check if there's a specific setting for this recipe
            if (config.has(recipeId)) {
                return config.get(recipeId).getAsBoolean();
            }

            // Check for a global "all_recipes" setting
            if (config.has("all_recipes")) {
                return config.get("all_recipes").getAsBoolean();
            }

            // Default to enabled if no specific setting exists
            return true;
        } catch (Exception e) {
            System.err.println("Error reading recipe config: " + e.getMessage());
            return true; // Default to enabled if there's an error
        }
    }
}