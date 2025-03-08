package de.tomate65.survivalmod.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ConfigReader {
    private static final File CONFIG_FILE = new File("config/survival/conf.json");
    public static void loadConfig() {
        if (!CONFIG_FILE.exists()) {
            System.err.println("Config file does not exist: " + CONFIG_FILE.getAbsolutePath());
            return;
        }

        try (FileReader reader = new FileReader(CONFIG_FILE)) {
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
    }
}