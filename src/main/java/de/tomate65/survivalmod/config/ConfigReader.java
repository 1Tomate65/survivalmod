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

    private static boolean generateRecipes = false;
    private static boolean generateStructures = false;
    private static boolean toggleCommandEnabled = false;
    private static int chatMsgFrequency = 0;
    private static String defaultStatCategory = "mined";

    private static String defaultTextColor = "GRAY";
    private static String defaultCategoryColor = "GRAY";
    private static String defaultMaterialColor = "GRAY";
    private static String defaultNumberColor = "GOLD";

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

            if (config.has("ChatMsgFrequency")) {
                chatMsgFrequency = config.get("ChatMsgFrequency").getAsInt();
                System.out.println("Loaded ChatMsgFrequency from conf.json: " + chatMsgFrequency);
            }

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

            // Load color settings
            defaultTextColor = config.has("default_text_color")
                    ? config.get("default_text_color").getAsString()
                    : "GRAY";
            defaultCategoryColor = config.has("default_category_color")
                    ? config.get("default_category_color").getAsString()
                    : "BLUE";
            defaultMaterialColor = config.has("default_material_color")
                    ? config.get("default_material_color").getAsString()
                    : "GREEN";
            defaultNumberColor = config.has("default_number_color")
                    ? config.get("default_number_color").getAsString()
                    : "GOLD";

            System.out.println("Generate Recipes: " + generateRecipes);
            System.out.println("Generate Structures: " + generateStructures);
            System.out.println("Toggle Command Enabled: " + toggleCommandEnabled);
            System.out.println("Default Stat Category: " + defaultStatCategory);
            System.out.println("Default Chat Msg Frequency: " + chatMsgFrequency);
            System.out.println("Default Text Color: " + defaultTextColor);
            System.out.println("Default Category Color: " + defaultCategoryColor);
            System.out.println("Default Material Color: " + defaultMaterialColor);
            System.out.println("Default Number Color: " + defaultNumberColor);

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

    public static boolean isToggleCommandEnabled() {
        return toggleCommandEnabled;
    }

    public static String getDefaultStatCategory() {
        return defaultStatCategory;
    }

    public static int getChatMsgFrequency() {
        return chatMsgFrequency;
    }

    public static String getDefaultTextColor() {
        return defaultTextColor;
    }

    public static String getDefaultCategoryColor() {
        return defaultCategoryColor;
    }

    public static String getDefaultMaterialColor() {
        return defaultMaterialColor;
    }

    public static String getDefaultNumberColor() {
        return defaultNumberColor;
    }
}