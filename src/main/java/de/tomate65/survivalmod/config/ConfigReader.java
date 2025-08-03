package de.tomate65.survivalmod.config;

import com.google.gson.*;
import de.tomate65.survivalmod.manager.MagnetManager;
import java.io.*;
import java.util.*;

import static de.tomate65.survivalmod.Survivalmod.ModVersion;

public class ConfigReader {
    private static final File CONF_CONFIG = new File("config/survival/" + ModVersion + "/conf.json");
    private static final File LANG_DIR = new File("config/survival/" + ModVersion + "/lang");

    // Config values with defaults
    private static boolean toggleCommandEnabled = true;
    private static boolean invertToggleFile = false;
    private static int chatMsgFrequency = 10;
    private static String modVersion = ModVersion;
    private static final Map<String, Boolean> languageToggles = new HashMap<>();
    private static String defaultStatCategory = "mined";
    private static String defaultLanguage = "en_us";
    private static String defaultTextColor = "GRAY";
    private static String defaultCategoryColor = "GRAY";
    private static String defaultMaterialColor = "GRAY";
    private static String defaultNumberColor = "GOLD";
    private static String defaultTimeColor = "AQUA";
    public static final Map<String, Map<String, String>> translations = new HashMap<>();

    public static void loadConfig() {
        JsonObject config = loadConfConfig();
        loadLanguageToggleConfig();
        loadTranslations();
    }

    private static JsonObject loadConfConfig() {
        if (!CONF_CONFIG.exists()) return null;

        try (FileReader reader = new FileReader(CONF_CONFIG)) {
            JsonObject config = JsonParser.parseReader(reader).getAsJsonObject();
            modVersion = config.get("ModVersion").getAsString();

            // Load basic settings
            chatMsgFrequency = config.get("ChatMsgFrequency").getAsInt();
            toggleCommandEnabled = config.get("Toggle Command").getAsBoolean();
            defaultStatCategory = config.get("Default Statistik Category").getAsString();

            // Optional settings
            if (config.has("InvertToggleFile")) {
                invertToggleFile = config.get("InvertToggleFile").getAsBoolean();
            }

            // Color settings
            defaultTextColor = config.get("default_text_color").getAsString();
            defaultCategoryColor = config.get("default_category_color").getAsString();
            defaultMaterialColor = config.get("default_material_color").getAsString();
            defaultNumberColor = config.get("default_number_color").getAsString();
            defaultTimeColor = config.get("default_time_color").getAsString();

            // Magnet settings
            JsonObject magnetSettings = config.has("magnet")
                    ? config.getAsJsonObject("magnet")
                    : new JsonObject();
            MagnetManager.setConfigValues(
                    magnetSettings.has("enabled") && magnetSettings.get("enabled").getAsBoolean(),
                    magnetSettings.has("hungerStrength") ? magnetSettings.get("hungerStrength").getAsInt() : 5,
                    magnetSettings.has("radius") ? magnetSettings.get("radius").getAsInt() : 5
            );

            return config;
        } catch (Exception e) {
            System.err.println("Error reading conf config: " + e.getMessage());
            return null;
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
                langData.entrySet().forEach(entry ->
                        langMap.put(entry.getKey(), entry.getValue().getAsString())
                );
                translations.put(langCode, langMap);
            } catch (Exception e) {
                System.err.println("Error loading language file: " + langFile.getName());
                e.printStackTrace();
            }
        }
    }

    private static void loadLanguageToggleConfig() {
        File toggleFile = new File(LANG_DIR, "language_files_toggle.json");
        if (!toggleFile.exists()) return;

        try (FileReader reader = new FileReader(toggleFile)) {
            JsonObject config = JsonParser.parseReader(reader).getAsJsonObject();
            languageToggles.clear();
            config.entrySet().forEach(entry -> {
                String code = entry.getKey().contains("_")
                        ? entry.getKey()
                        : entry.getKey().replaceAll("([a-z]{2})([a-z]{2})", "$1_$2");
                languageToggles.put(code.toLowerCase(), entry.getValue().getAsBoolean());
            });
        } catch (Exception e) {
            System.err.println("Error reading language toggle config: " + e.getMessage());
        }
    }

    // Getters
    public static boolean isToggleCommandEnabled() { return toggleCommandEnabled; }
    public static int getChatMsgFrequency() { return chatMsgFrequency; }
    public static String getModVersion() { return modVersion; }
    public static String getDefaultStatCategory() { return defaultStatCategory; }
    public static String getDefaultLanguage() { return defaultLanguage; }
    public static String getDefaultTextColor() { return defaultTextColor; }
    public static String getDefaultCategoryColor() { return defaultCategoryColor; }
    public static String getDefaultMaterialColor() { return defaultMaterialColor; }
    public static String getDefaultNumberColor() { return defaultNumberColor; }
    public static boolean isInvertedToggleMode() { return invertToggleFile; }
    public static String getDefaultTimeColor() { return defaultTimeColor; }
    public static Set<String> getAvailableLanguages() { return translations.keySet(); }

    public static boolean isLanguageEnabled(String langCode) {
        return languageToggles.getOrDefault(
                langCode.toLowerCase().replace(" ", "_"),
                langCode.equalsIgnoreCase("en_us")
        );
    }

    public static String translate(String key, String langCode) {
        Map<String, String> lang = translations.getOrDefault(
                langCode,
                translations.get(defaultLanguage)
        );
        return lang != null ? lang.getOrDefault(key, key) : key;
    }

    public static boolean isRecipeEnabled(String recipeId) {
        File recipeFile = new File("config/survival/" + ModVersion + "/recipes.json");
        if (!recipeFile.exists()) return true;

        try (FileReader reader = new FileReader(recipeFile)) {
            JsonObject config = JsonParser.parseReader(reader).getAsJsonObject();
            if (config.has(recipeId)) return config.get(recipeId).getAsBoolean();
            if (config.has("all_recipes")) return config.get("all_recipes").getAsBoolean();
            return true;
        } catch (Exception e) {
            System.err.println("Error reading recipe config: " + e.getMessage());
            return true;
        }
    }

    public static boolean isLootTableEnabled(String lootTableId) {
        File loottableFile = new File("config/survival/" + ModVersion + "/loottable_toggles.json");
        if (!loottableFile.exists()) return true;

        try (FileReader reader = new FileReader(loottableFile)) {
            JsonObject config = JsonParser.parseReader(reader).getAsJsonObject();
            if (config.has(lootTableId)) return config.get(lootTableId).getAsBoolean();
            if (config.has("all_loottables")) return config.get("all_loottables").getAsBoolean();
            return true;
        } catch (Exception e) {
            System.err.println("Error reading loottable config: " + e.getMessage());
            return true;
        }
    }
}