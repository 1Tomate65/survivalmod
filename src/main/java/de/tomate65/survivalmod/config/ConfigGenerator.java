package de.tomate65.survivalmod.config;

import com.google.gson.*;
import de.tomate65.survivalmod.manager.TranslationManager;
import de.tomate65.survivalmod.manager.UpdateHelper;
import de.tomate65.survivalmod.recipes.RecipeGenerator;

import java.io.*;
import java.util.List;

import static de.tomate65.survivalmod.Survivalmod.ModVersion;

public class ConfigGenerator {
    private static final File CONFIG_DIR = new File("config/survival/" + ModVersion);
    private static final File LANG_DIR = new File("config/survival/" + ModVersion + "/lang");
    private static final File SURVIVAL_CONFIG = new File(CONFIG_DIR, "survival.json");
    private static final File TOGGLE_CONFIG = new File(CONFIG_DIR, "toggle.json");
    private static final File CONF_CONFIG = new File(CONFIG_DIR, "conf.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static final String[] VERSIONS = {
            "0.3.0"
    };

    public static void generateConfigs() {
        File versionFolder = new File(CONFIG_DIR.getParentFile(), ModVersion);
        File unversionedConf = new File(CONFIG_DIR.getParentFile(), "conf.json");

       if (!versionFolder.exists() && unversionedConf.exists()) {
            try {
                versionFolder.mkdirs();
                UpdateHelper.moveConfigsToVersionedFolder(versionFolder);
            } catch (IOException e) {
                System.err.println("Failed to move configs to versioned folder: " + e.getMessage());
                e.printStackTrace();
            }
        } else if (!versionFolder.exists()) {
            versionFolder.mkdirs();
        }

        generateConfConfig();
        generateSurvivalConfig();
        generateToggleConfig();

        File recipeDir = new File("config/survival/" + ModVersion + "/recipe");
        if (!recipeDir.exists()) recipeDir.mkdirs();

        if (!LANG_DIR.exists()) LANG_DIR.mkdirs();

        generateLanguageToggleConfig();

        RecipeGenerator.generateAllRecipes(recipeDir);
        TranslationManager.generateAllTranslations(LANG_DIR);

        updateConfigVersion();

        System.out.println("Configs Generated/Updated");
    }

    private static void updateConfigVersion() {
        try {
            if (CONF_CONFIG.exists()) {
                JsonObject config = JsonParser.parseReader(new FileReader(CONF_CONFIG)).getAsJsonObject();
                config.addProperty("ModVersion", ModVersion);
                try (FileWriter writer = new FileWriter(CONF_CONFIG)) {
                    GSON.toJson(config, writer);
                }
            }
        } catch (Exception e) {
            System.err.println("Error updating config version: " + e.getMessage());
        }
    }

    private static void generateSurvivalConfig() {
        try {
            JsonObject defaultConfig = new JsonObject();
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
                    "§7§l§nChangelog §8- §60.3.0 The Structure and Balancing Update",
                    "",
                    // --- Additions & Changes ---
                    "§l§6§nAdditions & Changes",

                    "§7- §2Reworked the config system.",
                    "§7- §2Added loot tables - a complete list is available on Modrinth.",
                    "§7- §2Redesigned most structures.",
                    "§7- §2Removed redundant recipes.",
                    "§7- §2Added Tall Dry Grass recipe.",
                    "§7- §2Balanced 'nether_shelter_1' to no longer spawn a 'Netherite Axe' with the 'Looting III' enchantment.",
                    "§7- §2Balanced loot tables for most structures.",
                    "§7- §2Added 5 new languages.",
                    //"§7- §2Added 1 experimental language (due to complex sentence structure differences).",
                    "",

                    // --- Bug Fixes ---
                    "§l§6§nBug Fixes",
                    "§7- §2Fixed a bug where in certain languages some translations went completely missing.",
                    "§7- §2Fixed a bug where nether shelters spawned on the Nether roof.",
                    "§7- §2Fixed a bug where identical structures spawned right next to each other.",
                    "§7- §2Fixed various unnoticed bugs.",
                    "",

                    // --- Thanks ---
                    "§l§6§nThanks",
                    "§9A big thank you to This_Pluto for hearing me out and suggesting and testing unofficial versions.",
                    "§9A big thank you to TheCityCrafter, EinFynn, wchtig_, Fortex, and Connplay for reading the Modrinth description and providing feedback,",
                    "§keven though I asked them nicely."
            ));
            commands.add("survival", survival);
            defaultConfig.add("commands", commands);

            if (SURVIVAL_CONFIG.exists()) {
                try (FileReader reader = new FileReader(SURVIVAL_CONFIG)) {
                    JsonObject userConfig = JsonParser.parseReader(reader).getAsJsonObject();

                    if (userConfig.has("commands") && userConfig.get("commands").isJsonObject()) {
                        JsonObject userSurvival = userConfig.getAsJsonObject("commands").getAsJsonObject("survival");
                        JsonObject defaultSurvival = survival; // Die Vorlage von oben

                        for (String key : defaultSurvival.keySet()) {
                            if (!userSurvival.has(key)) {
                                userSurvival.add(key, defaultSurvival.get(key));
                            }
                        }
                    }
                    defaultConfig = userConfig;
                }
            }

            try (FileWriter writer = new FileWriter(SURVIVAL_CONFIG)) {
                GSON.toJson(defaultConfig, writer);
            }

        } catch (IOException e) {
            System.err.println("Error creating or updating survival config: " + e.getMessage());
        }
    }

    public static void generateToggleConfig() {
        try {
            // 1. Immer eine Standard-Vorlage im Speicher erstellen
            JsonObject defaultConfig = new JsonObject();
            JsonArray defaultToggles = new JsonArray();
            defaultToggles.add("stone");
            defaultToggles.add("dirt");
            defaultToggles.add("oak_log");
            defaultToggles.add("timeplayed");
            defaultConfig.add("toggles", defaultToggles);

            // 2. Lade die bestehende Konfigurationsdatei, falls sie existiert
            if (TOGGLE_CONFIG.exists()) {
                try (FileReader reader = new FileReader(TOGGLE_CONFIG)) {
                    // Wenn die Datei existiert, lade sie und nutze sie als Basis
                    // (In diesem Fall wollen wir die Liste des Nutzers komplett übernehmen)
                    defaultConfig = JsonParser.parseReader(reader).getAsJsonObject();
                }
            }

            // 3. Schreibe die (entweder neue oder vom Nutzer geladene) Konfiguration zurück
            try (FileWriter writer = new FileWriter(TOGGLE_CONFIG)) {
                writer.write(GSON.toJson(defaultConfig));
            }
        } catch (IOException e) {
            System.err.println("Error creating or updating toggle config: " + e.getMessage());
        }
    }

    private static void generateConfConfig() {
        try {
            // 1. Erstelle immer eine Vorlage mit den neuesten Standardwerten im Speicher
            JsonObject defaultConfig = new JsonObject();
            defaultConfig.addProperty("ModVersion", ModVersion);
            defaultConfig.addProperty("ChatMsgFrequency", 10);
            defaultConfig.addProperty("Toggle Command", true);
            defaultConfig.addProperty("Default Statistik Category", "mined");
            defaultConfig.addProperty("InvertToggleFile", false);

            JsonObject magnetSettings = new JsonObject();
            magnetSettings.addProperty("enabled", false);
            magnetSettings.addProperty("hungerStrength", 5);
            magnetSettings.addProperty("radius", 5);
            defaultConfig.add("magnet", magnetSettings);

            // Der neue Block, der hinzugefügt werden soll!
            JsonObject backupSettings = new JsonObject();
            backupSettings.addProperty("enabled", true); // Standardwerte hier definieren
            backupSettings.addProperty("maxBackups", 5);
            backupSettings.addProperty("minBackupIntervalHours", 24);
            backupSettings.addProperty("backupOnVersionChange", true);
            defaultConfig.add("backup", backupSettings);

            JsonObject config = defaultConfig;
            // Color settings
            defaultConfig.addProperty("default_text_color", "GRAY");
            defaultConfig.addProperty("default_category_color", "GRAY");
            defaultConfig.addProperty("default_material_color", "GRAY");
            defaultConfig.addProperty("default_number_color", "GOLD");
            defaultConfig.addProperty("default_time_color", "AQUA");

// Kommentare
            defaultConfig.addProperty("#1", "Valid stats: mined, crafted, used, broken, picked_up, dropped");
            defaultConfig.addProperty("#2", "InvertToggleFile: true makes toggle.json a blocklist instead of allowlist");


            // 2. Lade die bestehende Konfigurationsdatei, falls sie existiert
            JsonObject existingConfig = new JsonObject();
            if (CONF_CONFIG.exists()) {
                try (FileReader reader = new FileReader(CONF_CONFIG)) {
                    existingConfig = JsonParser.parseReader(reader).getAsJsonObject();
                }
            }
            // 3. Verschmelze die bestehende Konfiguration mit der neuen Vorlage
            mergeJsonObjects(defaultConfig, existingConfig);
            defaultConfig.addProperty("ModVersion", ModVersion); // Sicherstellen, dass die Version immer aktuell ist

            // 4. Schreibe die vollständige, aktualisierte Konfiguration zurück in die Datei
            try (FileWriter writer = new FileWriter(CONF_CONFIG)) {
                GSON.toJson(defaultConfig, writer);
            }

        } catch (IOException e) {
            System.err.println("Error creating or updating conf config: " + e.getMessage());
        }
    }

    private static void mergeJsonObjects(JsonObject target, JsonObject source) {
        for (String key : source.keySet()) {
            if (target.has(key)) {
                if (target.get(key).isJsonObject() && source.get(key).isJsonObject()) {
                    mergeJsonObjects(target.getAsJsonObject(key), source.getAsJsonObject(key));
                } else {
                    target.add(key, source.get(key));
                }
            }

        }
    }

    private static void generateLanguageToggleConfig() {
        File languageToggleFile = new File(LANG_DIR, "language_files_toggle.json");
        try {
            JsonObject defaultConfig = new JsonObject();
            List<String> languageCodes = TranslationManager.getAvailableLanguageCodes();
            for (String langCode : languageCodes) {
                boolean isDefault = langCode.equals(ConfigReader.getDefaultLanguage());
                defaultConfig.addProperty(langCode, isDefault);
            }
            if (languageToggleFile.exists()) {
                try (FileReader reader = new FileReader(languageToggleFile)) {
                    JsonObject userConfig = JsonParser.parseReader(reader).getAsJsonObject();
                    for (String langCode : userConfig.keySet()) {
                        if (defaultConfig.has(langCode) && userConfig.has(langCode)) {
                            defaultConfig.addProperty(langCode, userConfig.get(langCode).getAsBoolean());
                        }
                    }
                }
            }
            try (FileWriter writer = new FileWriter(languageToggleFile)) {
                GSON.toJson(defaultConfig, writer);
            }
        } catch (IOException e) {
            System.err.println("Error creating or updating language toggle config: " + e.getMessage());
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