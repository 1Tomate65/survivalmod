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
    private static final File LANG_DIR = new File("config/survival/lang");
    private static final File SURVIVAL_CONFIG = new File(CONFIG_DIR, "survival.json");
    private static final File TOGGLE_CONFIG = new File(CONFIG_DIR, "toggle.json");
    private static final File CONF_CONFIG = new File(CONFIG_DIR, "conf.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File EN_US_LANG = new File(LANG_DIR, "en_us.json");
    private static final File DE_DE_LANG = new File(LANG_DIR, "de_de.json");

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
        generateConfConfig();
        generateLangFiles();

        if (!CONFIG_DIR_recipe.exists()) {
            boolean dirsCreated = CONFIG_DIR_recipe.mkdirs();
            if (!dirsCreated) {
                System.err.println("Failed to create config directory: " + CONFIG_DIR_recipe.getAbsolutePath());
                return;
            }
        }
    }

    private static void generateLangFiles() {
        if (!LANG_DIR.exists()) {
            boolean dirsCreated = LANG_DIR.mkdirs();
            if (!dirsCreated) {
                System.err.println("Failed to create lang directory: " + LANG_DIR.getAbsolutePath());
                return;
            }
        }

        // English (default)
        if (!EN_US_LANG.exists()) {
            try (FileWriter writer = new FileWriter(EN_US_LANG)) {
                JsonObject translations = new JsonObject();
                translations.addProperty("stat.mined", "Mined");
                translations.addProperty("stat.crafted", "Crafted");
                translations.addProperty("stat.used", "Used");
                translations.addProperty("stat.broken", "Broken");
                translations.addProperty("stat.picked_up", "Picked Up");
                translations.addProperty("stat.dropped", "Dropped");
                translations.addProperty("stat.killed", "Killed");
                translations.addProperty("stat.killed_by", "Killed By");
                translations.addProperty("stat.custom", "Custom");
                translations.addProperty("message.you", "You");
                translations.addProperty("message.have", "have");
                translations.addProperty("message.action.mined", "mined");
                translations.addProperty("message.action.crafted", "crafted");
                translations.addProperty("message.action.used", "used");
                translations.addProperty("message.action.broken", "broke");
                translations.addProperty("message.action.picked_up", "picked up");
                translations.addProperty("message.action.dropped", "dropped");
                translations.addProperty("message.plural", "s");
                translations.addProperty("message.exclamation", "!");

                writer.write(GSON.toJson(translations));
                System.out.println("Successfully wrote English translations to " + EN_US_LANG.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Error writing English translations: " + e.getMessage());
            }
        }

        // German
        if (!DE_DE_LANG.exists()) {
            try (FileWriter writer = new FileWriter(DE_DE_LANG)) {
                JsonObject translations = new JsonObject();
                translations.addProperty("stat.mined", "Abgebaut");
                translations.addProperty("stat.crafted", "Hergestellt");
                translations.addProperty("stat.used", "Benutzt");
                translations.addProperty("stat.broken", "Kaputt");
                translations.addProperty("stat.picked_up", "Aufgehoben");
                translations.addProperty("stat.dropped", "Fallengelassen");
                translations.addProperty("stat.killed", "Getötet");
                translations.addProperty("stat.killed_by", "Getötet von");
                translations.addProperty("stat.custom", "Benutzerdefiniert");
                translations.addProperty("message.you", "Du");
                translations.addProperty("message.have", "hast");
                translations.addProperty("message.action.mined", "abgebaut");
                translations.addProperty("message.action.crafted", "hergestellt");
                translations.addProperty("message.action.used", "benutzt");
                translations.addProperty("message.action.broken", "kaputt gemacht");
                translations.addProperty("message.action.picked_up", "aufgehoben");
                translations.addProperty("message.action.dropped", "fallengelassen");
                translations.addProperty("message.plural", "en");
                translations.addProperty("message.exclamation", "!");

                writer.write(GSON.toJson(translations));
                System.out.println("Successfully wrote German translations to " + DE_DE_LANG.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Error writing German translations: " + e.getMessage());
            }
        }

        // French
        File FR_FR_LANG = new File(LANG_DIR, "fr_fr.json");
        if (!FR_FR_LANG.exists()) {
            try (FileWriter writer = new FileWriter(FR_FR_LANG)) {
                JsonObject translations = new JsonObject();
                translations.addProperty("stat.mined", "Miné");
                translations.addProperty("stat.crafted", "Fabriqué");
                translations.addProperty("stat.used", "Utilisé");
                translations.addProperty("stat.broken", "Cassé");
                translations.addProperty("stat.picked_up", "Ramassé");
                translations.addProperty("stat.dropped", "Jeté");
                translations.addProperty("stat.killed", "Tué");
                translations.addProperty("stat.killed_by", "Tué par");
                translations.addProperty("stat.custom", "Personnalisé");
                translations.addProperty("message.you", "Vous");
                translations.addProperty("message.have", "avez");
                translations.addProperty("message.action.mined", "miné");
                translations.addProperty("message.action.crafted", "fabriqué");
                translations.addProperty("message.action.used", "utilisé");
                translations.addProperty("message.action.broken", "cassé");
                translations.addProperty("message.action.picked_up", "ramassé");
                translations.addProperty("message.action.dropped", "jeté");
                translations.addProperty("message.plural", "s");
                translations.addProperty("message.exclamation", "!");

                writer.write(GSON.toJson(translations));
                System.out.println("Successfully wrote French translations to " + FR_FR_LANG.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Error writing French translations: " + e.getMessage());
            }
        }

        // Spanish
        File ES_ES_LANG = new File(LANG_DIR, "es_es.json");
        if (!ES_ES_LANG.exists()) {
            try (FileWriter writer = new FileWriter(ES_ES_LANG)) {
                JsonObject translations = new JsonObject();
                translations.addProperty("stat.mined", "Extraído");
                translations.addProperty("stat.crafted", "Fabricado");
                translations.addProperty("stat.used", "Usado");
                translations.addProperty("stat.broken", "Roto");
                translations.addProperty("stat.picked_up", "Recogido");
                translations.addProperty("stat.dropped", "Tirado");
                translations.addProperty("stat.killed", "Matado");
                translations.addProperty("stat.killed_by", "Muerto por");
                translations.addProperty("stat.custom", "Personalizado");
                translations.addProperty("message.you", "Tú");
                translations.addProperty("message.have", "has");
                translations.addProperty("message.action.mined", "extraído");
                translations.addProperty("message.action.crafted", "fabricado");
                translations.addProperty("message.action.used", "usado");
                translations.addProperty("message.action.broken", "roto");
                translations.addProperty("message.action.picked_up", "recogido");
                translations.addProperty("message.action.dropped", "tirado");
                translations.addProperty("message.plural", "s");
                translations.addProperty("message.exclamation", "!");

                writer.write(GSON.toJson(translations));
                System.out.println("Successfully wrote Spanish translations to " + ES_ES_LANG.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Error writing Spanish translations: " + e.getMessage());
            }
        }

        // Dutch
        File NL_NL_LANG = new File(LANG_DIR, "nl_nl.json");
        if (!NL_NL_LANG.exists()) {
            try (FileWriter writer = new FileWriter(NL_NL_LANG)) {
                JsonObject translations = new JsonObject();
                translations.addProperty("stat.mined", "Gedolven");
                translations.addProperty("stat.crafted", "Gemaakt");
                translations.addProperty("stat.used", "Gebruikt");
                translations.addProperty("stat.broken", "Gebroken");
                translations.addProperty("stat.picked_up", "Opgepakt");
                translations.addProperty("stat.dropped", "Laten vallen");
                translations.addProperty("stat.killed", "Gedood");
                translations.addProperty("stat.killed_by", "Gedood door");
                translations.addProperty("stat.custom", "Aangepast");
                translations.addProperty("message.you", "Jij");
                translations.addProperty("message.have", "hebt");
                translations.addProperty("message.action.mined", "gedolven");
                translations.addProperty("message.action.crafted", "gemaakt");
                translations.addProperty("message.action.used", "gebruikt");
                translations.addProperty("message.action.broken", "gebroken");
                translations.addProperty("message.action.picked_up", "opgepakt");
                translations.addProperty("message.action.dropped", "laten vallen");
                translations.addProperty("message.plural", "en");
                translations.addProperty("message.exclamation", "!");

                writer.write(GSON.toJson(translations));
                System.out.println("Successfully wrote Dutch translations to " + NL_NL_LANG.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Error writing Dutch translations: " + e.getMessage());
            }
        }
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
                info.add("There is no a or b");
                info.add("");
                info.add("§6§nFeatures in Progress");
                info.add("§8- §r/toggle");
                info.add("§7 - §rThe working part is working");
                info.add("§7 - §rBut has some hidden function");
                info.add("§8- §rRecipes per config file");
                info.add("§7 - §rJust an Idea");
                info.add("§8- §6Playground World");
                info.add("§7 - §aThe Idea is,");
                info.add("§7 - §aYou can experiment with the mod and changing it to your liken.");
                info.add("§7 - §aAnd giving you a sense of what the mods actually all ads");
                info.add("§7 - §aYou'll find every default recipe");
                info.add("§7 - §aYou'll find every default Structure");
                info.add("§7 - §aYou'll find every default Command");

                changelog.add("§7§l§nChangelog");
                changelog.add("");
                changelog.add("§7-§r §aReworked §7/§2toggle");
                changelog.add("§7-§r §aMade Toggle command Display colorble per Player");
                changelog.add("§7-§r §aReworked config files");
                changelog.add("§7-§r §aFirst Showcase World");
                changelog.add("§8 - §7(§2Alpha Version 0.0.1§7)§r");

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
                defaultToggles.add("dirt");
                defaultToggles.add("oak_log");

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

                confJson.addProperty("ChatMsgFrequency", 10);
                //confJson.addProperty("Generate Recipes", false);
                //confJson.addProperty("Generate Structures", false);
                confJson.addProperty("Toggle Command", true);
                //confJson.addProperty("Number of crafts remain", -1);
                confJson.addProperty("#1", "Valid Default Statistics are:");
                confJson.addProperty("#2", "mined, crafted, used, broken, picked_up, dropped, killed, killed_by, custom");
                confJson.addProperty("Default Statistik Category", "mined");

                // New color properties
                confJson.addProperty("default_text_color", "GRAY");
                confJson.addProperty("default_category_color", "GRAY");
                confJson.addProperty("default_material_color", "GRAY");
                confJson.addProperty("default_number_color", "GOLD");

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