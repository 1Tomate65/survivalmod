package de.tomate65.survivalmod.translation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public abstract class TranslationGenerator {
    protected static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public abstract String getLanguageCode();
    public abstract JsonObject generateTranslations();

    public void generateLangFile(File langDir) {
        File langFile = new File(langDir, getLanguageCode() + ".json");
        if (!langFile.exists()) {
            try (FileWriter writer = new FileWriter(langFile)) {
                writer.write(GSON.toJson(generateTranslations()));
            } catch (IOException e) {
                System.err.println("Error creating language file: " + e.getMessage());
            }
        }
    }
}