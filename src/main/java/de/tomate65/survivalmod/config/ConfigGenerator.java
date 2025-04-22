package de.tomate65.survivalmod.config;

import com.google.gson.*;
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
        generateLangFiles();
    }

    private static void createDirectory(File dir) {
        if (!dir.exists() && !dir.mkdirs()) {
            System.err.println("Failed to create directory: " + dir.getAbsolutePath());
        }
    }

    private static void generateLangFiles() {
        // English (US)
        JsonObject enUsTranslations = new JsonObject();
        enUsTranslations.addProperty("time.years", "y");
        enUsTranslations.addProperty("time.days", "d");
        enUsTranslations.addProperty("time.hours", "h");
        enUsTranslations.addProperty("time.minutes", "m");
        enUsTranslations.addProperty("time.seconds", "s");
        enUsTranslations.addProperty("command.disabled", "Command disabled");
        enUsTranslations.addProperty("command.player_only", "Command must be executed by a player");
        enUsTranslations.addProperty("command.invalid_language", "Invalid language code");
        enUsTranslations.addProperty("command.invalid_toggle", "This toggle is not allowed in current mode (%s)");
        enUsTranslations.addProperty("command.invalid_hex_color", "Invalid hex color format. Use RRGGBB or #RRGGBB");
        enUsTranslations.addProperty("command.error_saving", "Error saving your %s preference.");
        enUsTranslations.addProperty("command.reset_success", "Your toggle preferences have been reset.");
        enUsTranslations.addProperty("command.reset_failed", "Failed to reset your toggle preferences.");
        enUsTranslations.addProperty("command.reset_none", "You didn't have any toggle preferences to reset.");
        enUsTranslations.addProperty("command.reloaded", "Configuration reloaded.");
        enUsTranslations.addProperty("command.no_content", "No content available for this subcommand.");
        enUsTranslations.addProperty("command.no_subcommands", "No subcommands available. Check your configuration.");
        enUsTranslations.addProperty("command.available_commands", "Available subcommands:");
        enUsTranslations.addProperty("command.usage", "Usage: %s");
        enUsTranslations.addProperty("command.available_colors", "Available colors: %s");
        enUsTranslations.addProperty("command.available_languages", "Available languages: %s");
        enUsTranslations.addProperty("command.hex_format", "Hex format can be either RRGGBB or #RRGGBB");
        enUsTranslations.addProperty("timeplayed.set", "Set timeplayed to display in %s");
        enUsTranslations.addProperty("language.set", "Set language to %s");
        enUsTranslations.addProperty("color.set", "Set %s color to %s");
        enUsTranslations.addProperty("color.reset", "Reset %s color to default");
        enUsTranslations.addProperty("color.set.part1", "Set ");
        enUsTranslations.addProperty("color.set.part2", " color to ");
        enUsTranslations.addProperty("color.reset.part1", "Reset ");
        enUsTranslations.addProperty("color.reset.part2", " color to default");
        enUsTranslations.addProperty("toggle.set", "Set toggle %s to display in %s with %s statistics");
        enUsTranslations.addProperty("item.stone.name", "Stone");
        enUsTranslations.addProperty("item.dirt.name", "Dirt");
        enUsTranslations.addProperty("item.oak_log.name", "Oak Log");
        enUsTranslations.addProperty("stat.timeplayed", "Time Played");
        enUsTranslations.addProperty("stat.mined", "Mined");
        enUsTranslations.addProperty("stat.crafted", "Crafted");
        enUsTranslations.addProperty("stat.used", "Used");
        enUsTranslations.addProperty("stat.broken", "Broken");
        enUsTranslations.addProperty("stat.picked_up", "Picked Up");
        enUsTranslations.addProperty("stat.dropped", "Dropped");
        enUsTranslations.addProperty("stat.killed", "Killed");
        enUsTranslations.addProperty("stat.killed_by", "Killed By");
        enUsTranslations.addProperty("stat.custom", "Custom");
        enUsTranslations.addProperty("help.general.title", "=== Toggle Command Help ===");
        enUsTranslations.addProperty("help.general.materials", "/toggle help materials - Show available material categories");
        enUsTranslations.addProperty("help.general.color", "/toggle help color - Show color customization help");
        enUsTranslations.addProperty("help.general.clear", "/toggle help clear - Show how to reset your settings");
        enUsTranslations.addProperty("help.general.language", "/toggle help language - Show language options");
        enUsTranslations.addProperty("help.general.usage", "Usage: /toggle <material> <location> [stat_category]");
        enUsTranslations.addProperty("help.materials.title", "=== Material Categories ===");
        enUsTranslations.addProperty("help.materials.header", "Available material types:");
        enUsTranslations.addProperty("help.materials.mobs", "Mobs - All living entities (zombies, creepers, etc.)");
        enUsTranslations.addProperty("help.materials.blocks", "Blocks - All placeable blocks (stone, dirt, etc.)");
        enUsTranslations.addProperty("help.materials.items", "Items - All items (swords, food, tools, etc.)");
        enUsTranslations.addProperty("help.materials.all", "All - Everything available");
        enUsTranslations.addProperty("help.materials.usage", "Usage: /toggle <material_id> <actionbar|chat|title> [stat_category]");
        enUsTranslations.addProperty("help.color.title", "=== Color Customization ===");
        enUsTranslations.addProperty("help.color.header", "Change colors for different text elements:");
        enUsTranslations.addProperty("help.color.text", "/toggle color text <color> - Main text color");
        enUsTranslations.addProperty("help.color.category", "/toggle color category <color> - Stat category color");
        enUsTranslations.addProperty("help.color.material", "/toggle color material <color> - Material name color");
        enUsTranslations.addProperty("help.color.number", "/toggle color number <color> - Number color");
        enUsTranslations.addProperty("help.color.time", "/toggle color time <color> - Playtime display color");
        enUsTranslations.addProperty("help.color.colors", "Colors can be: RED, GREEN, BLUE, YELLOW, etc.");
        enUsTranslations.addProperty("help.color.hex", "Or use hex values: '#FF0000' (red), '#00FF00' (green)");
        enUsTranslations.addProperty("help.color.none", "Use 'NONE' to reset to default");
        enUsTranslations.addProperty("help.clear.title", "=== Reset Settings ===");
        enUsTranslations.addProperty("help.clear.header", "Reset all your toggle preferences:");
        enUsTranslations.addProperty("help.clear.command", "/toggle clear");
        enUsTranslations.addProperty("help.clear.removes", "This will remove:");
        enUsTranslations.addProperty("help.clear.toggles", "- Your current toggle settings");
        enUsTranslations.addProperty("help.clear.colors", "- All color preferences");
        enUsTranslations.addProperty("help.clear.language", "- Language selection");
        enUsTranslations.addProperty("help.clear.note", "You'll need to set everything up again after clearing.");
        enUsTranslations.addProperty("help.language.title", "=== Language Options ===");
        enUsTranslations.addProperty("help.language.available", "Available languages:");
        enUsTranslations.addProperty("help.language.change", "Change your display language:");
        enUsTranslations.addProperty("help.language.command", "/toggle language <language_code>");
        enUsTranslations.addProperty("help.language.example", "Example: /toggle language en_us");
        createLangFile("en_us.json", enUsTranslations);

        // English (GB)
        JsonObject enGbTranslations = new JsonObject();
        enGbTranslations.addProperty("time.years", "y");
        enGbTranslations.addProperty("time.days", "d");
        enGbTranslations.addProperty("time.hours", "h");
        enGbTranslations.addProperty("time.minutes", "m");
        enGbTranslations.addProperty("time.seconds", "s");
        enGbTranslations.addProperty("command.disabled", "Command disabled");
        enGbTranslations.addProperty("command.player_only", "Command must be executed by a player");
        enGbTranslations.addProperty("command.invalid_language", "Invalid language code");
        enGbTranslations.addProperty("command.invalid_toggle", "This toggle is not allowed in current mode (%s)");
        enGbTranslations.addProperty("command.invalid_hex_color", "Invalid hex colour format. Use RRGGBB or #RRGGBB");
        enGbTranslations.addProperty("command.error_saving", "Error saving your %s preference.");
        enGbTranslations.addProperty("command.reset_success", "Your toggle preferences have been reset.");
        enGbTranslations.addProperty("command.reset_failed", "Failed to reset your toggle preferences.");
        enGbTranslations.addProperty("command.reset_none", "You didn't have any toggle preferences to reset.");
        enGbTranslations.addProperty("command.reloaded", "Configuration reloaded.");
        enGbTranslations.addProperty("command.no_content", "No content available for this subcommand.");
        enGbTranslations.addProperty("command.no_subcommands", "No subcommands available. Check your configuration.");
        enGbTranslations.addProperty("command.available_commands", "Available subcommands:");
        enGbTranslations.addProperty("command.usage", "Usage: %s");
        enGbTranslations.addProperty("command.available_colors", "Available colours: %s");
        enGbTranslations.addProperty("command.available_languages", "Available languages: %s");
        enGbTranslations.addProperty("command.hex_format", "Hex format can be either RRGGBB or #RRGGBB");
        enGbTranslations.addProperty("timeplayed.set", "Set timeplayed to display in %s");
        enGbTranslations.addProperty("language.set", "Set language to %s");
        enGbTranslations.addProperty("color.set", "Set %s colour to %s");
        enGbTranslations.addProperty("color.reset", "Reset %s colour to default");
        enGbTranslations.addProperty("toggle.set", "Set toggle %s to display in %s with %s statistics");
        enGbTranslations.addProperty("item.stone.name", "Stone");
        enGbTranslations.addProperty("item.dirt.name", "Dirt");
        enGbTranslations.addProperty("item.oak_log.name", "Oak Log");
        enGbTranslations.addProperty("color.set.part1", "Set ");
        enGbTranslations.addProperty("color.set.part2", " colour to ");
        enGbTranslations.addProperty("color.reset.part1", "Reset ");
        enGbTranslations.addProperty("color.reset.part2", " colour to default");
        enGbTranslations.addProperty("stat.mined", "Mined");
        enGbTranslations.addProperty("stat.crafted", "Crafted");
        enGbTranslations.addProperty("stat.used", "Used");
        enGbTranslations.addProperty("stat.broken", "Broken");
        enGbTranslations.addProperty("stat.picked_up", "Picked Up");
        enGbTranslations.addProperty("stat.dropped", "Dropped");
        enGbTranslations.addProperty("stat.killed", "Killed");
        enGbTranslations.addProperty("stat.killed_by", "Killed By");
        enGbTranslations.addProperty("stat.custom", "Custom");
        enGbTranslations.addProperty("stat.timeplayed", "Time Played");
        enGbTranslations.addProperty("help.general.title", "=== Toggle Command Help ===");
        enGbTranslations.addProperty("help.general.materials", "/toggle help materials - Show available material categories");
        enGbTranslations.addProperty("help.general.color", "/toggle help colour - Show colour customisation help");
        enGbTranslations.addProperty("help.general.clear", "/toggle help clear - Show how to reset your settings");
        enGbTranslations.addProperty("help.general.language", "/toggle help language - Show language options");
        enGbTranslations.addProperty("help.general.usage", "Usage: /toggle <material> <location> [stat_category]");
        enGbTranslations.addProperty("help.materials.title", "=== Material Categories ===");
        enGbTranslations.addProperty("help.materials.header", "Available material types:");
        enGbTranslations.addProperty("help.materials.mobs", "Mobs - All living entities (zombies, creepers, etc.)");
        enGbTranslations.addProperty("help.materials.blocks", "Blocks - All placeable blocks (stone, dirt, etc.)");
        enGbTranslations.addProperty("help.materials.items", "Items - All items (swords, food, tools, etc.)");
        enGbTranslations.addProperty("help.materials.all", "All - Everything available");
        enGbTranslations.addProperty("help.materials.usage", "Usage: /toggle <material_id> <actionbar|chat|title> [stat_category]");
        enGbTranslations.addProperty("help.color.title", "=== Colour Customisation ===");
        enGbTranslations.addProperty("help.color.header", "Change colours for different text elements:");
        enGbTranslations.addProperty("help.color.text", "/toggle colour text <colour> - Main text colour");
        enGbTranslations.addProperty("help.color.category", "/toggle colour category <colour> - Stat category colour");
        enGbTranslations.addProperty("help.color.material", "/toggle colour material <colour> - Material name colour");
        enGbTranslations.addProperty("help.color.number", "/toggle colour number <colour> - Number colour");
        enGbTranslations.addProperty("help.color.time", "/toggle colour time <colour> - Playtime display colour");
        enGbTranslations.addProperty("help.color.colors", "Colours can be: RED, GREEN, BLUE, YELLOW, etc.");
        enGbTranslations.addProperty("help.color.hex", "Or use hex values: '#FF0000' (red), '#00FF00' (green)");
        enGbTranslations.addProperty("help.color.none", "Use 'NONE' to reset to default");
        enGbTranslations.addProperty("help.clear.title", "=== Reset Settings ===");
        enGbTranslations.addProperty("help.clear.header", "Reset all your toggle preferences:");
        enGbTranslations.addProperty("help.clear.command", "/toggle clear");
        enGbTranslations.addProperty("help.clear.removes", "This will remove:");
        enGbTranslations.addProperty("help.clear.toggles", "- Your current toggle settings");
        enGbTranslations.addProperty("help.clear.colors", "- All colour preferences");
        enGbTranslations.addProperty("help.clear.language", "- Language selection");
        enGbTranslations.addProperty("help.clear.note", "You'll need to set everything up again after clearing.");
        enGbTranslations.addProperty("help.language.title", "=== Language Options ===");
        enGbTranslations.addProperty("help.language.available", "Available languages:");
        enGbTranslations.addProperty("help.language.change", "Change your display language:");
        enGbTranslations.addProperty("help.language.command", "/toggle language <language_code>");
        enGbTranslations.addProperty("help.language.example", "Example: /toggle language en_gb");
        createLangFile("en_gb.json", enGbTranslations);

        // German (de_de)
        JsonObject deTranslations = new JsonObject();
        deTranslations.addProperty("time.years", "J");
        deTranslations.addProperty("time.days", "T");
        deTranslations.addProperty("time.hours", "S");
        deTranslations.addProperty("time.minutes", "M");
        deTranslations.addProperty("time.seconds", "S");
        deTranslations.addProperty("command.disabled", "Befehl deaktiviert");
        deTranslations.addProperty("command.player_only", "Befehl muss von einem Spieler ausgeführt werden");
        deTranslations.addProperty("command.invalid_language", "Ungültiger Sprachcode");
        deTranslations.addProperty("command.invalid_toggle", "Dieser Toggle ist im aktuellen Modus nicht erlaubt (%s)");
        deTranslations.addProperty("command.invalid_hex_color", "Ungültiges Hex-Farbformat. Verwende RRGGBB oder #RRGGBB");
        deTranslations.addProperty("command.error_saving", "Fehler beim Speichern deiner %s Einstellung.");
        deTranslations.addProperty("command.reset_success", "Deine Toggle-Einstellungen wurden zurückgesetzt.");
        deTranslations.addProperty("command.reset_failed", "Zurücksetzen der Toggle-Einstellungen fehlgeschlagen.");
        deTranslations.addProperty("command.reset_none", "Du hattest keine Toggle-Einstellungen zum Zurücksetzen.");
        deTranslations.addProperty("command.reloaded", "Konfiguration neu geladen.");
        deTranslations.addProperty("command.no_content", "Kein Inhalt für diesen Unterbefehl verfügbar.");
        deTranslations.addProperty("command.no_subcommands", "Keine Unterbefehle verfügbar. Überprüfe deine Konfiguration.");
        deTranslations.addProperty("command.available_commands", "Verfügbare Unterbefehle:");
        deTranslations.addProperty("command.usage", "Verwendung: %s");
        deTranslations.addProperty("command.available_colors", "Verfügbare Farben: %s");
        deTranslations.addProperty("command.available_languages", "Verfügbare Sprachen: %s");
        deTranslations.addProperty("command.hex_format", "Hex-Format kann entweder RRGGBB oder #RRGGBB sein");
        deTranslations.addProperty("timeplayed.set", "Spielzeit wird in %s angezeigt");
        deTranslations.addProperty("language.set", "Sprache auf %s gesetzt");
        deTranslations.addProperty("color.set", "%s Farbe auf %s gesetzt");
        deTranslations.addProperty("color.reset", "%s Farbe auf Standard zurückgesetzt");
        deTranslations.addProperty("toggle.set", "Toggle %s wird in %s mit %s Statistiken angezeigt");
        deTranslations.addProperty("item.stone.name", "Stein");
        deTranslations.addProperty("item.dirt.name", "Erde");
        deTranslations.addProperty("item.oak_log.name", "Eichenholz");
        deTranslations.addProperty("color.set.part1", "Setze ");
        deTranslations.addProperty("color.set.part2", " Farbe auf ");
        deTranslations.addProperty("color.reset.part1", "Setze ");
        deTranslations.addProperty("color.reset.part2", " Farbe zurück auf Standard");
        deTranslations.addProperty("stat.mined", "Abgebaut");
        deTranslations.addProperty("stat.crafted", "Hergestellt");
        deTranslations.addProperty("stat.used", "Benutzt");
        deTranslations.addProperty("stat.broken", "Kaputt");
        deTranslations.addProperty("stat.picked_up", "Aufgehoben");
        deTranslations.addProperty("stat.dropped", "Fallen gelassen");
        deTranslations.addProperty("stat.killed", "Getötet");
        deTranslations.addProperty("stat.killed_by", "Getötet von");
        deTranslations.addProperty("stat.custom", "Benutzerdefiniert");
        deTranslations.addProperty("stat.timeplayed", "Spielzeit");
        deTranslations.addProperty("help.general.title", "=== Toggle-Befehl Hilfe ===");
        deTranslations.addProperty("help.general.materials", "/toggle help materials - Zeige verfügbare Materialkategorien");
        deTranslations.addProperty("help.general.color", "/toggle help color - Zeige Farbanpassungs-Hilfe");
        deTranslations.addProperty("help.general.clear", "/toggle help clear - Zeige wie man Einstellungen zurücksetzt");
        deTranslations.addProperty("help.general.language", "/toggle help language - Zeige Sprachoptionen");
        deTranslations.addProperty("help.general.usage", "Verwendung: /toggle <Material> <Ort> [Statistik-Kategorie]");
        deTranslations.addProperty("help.materials.title", "=== Materialkategorien ===");
        deTranslations.addProperty("help.materials.header", "Verfügbare Materialtypen:");
        deTranslations.addProperty("help.materials.mobs", "Kreaturen - Alle lebenden Wesen (Zombies, Creeper, etc.)");
        deTranslations.addProperty("help.materials.blocks", "Blöcke - Alle platzierbaren Blöcke (Stein, Erde, etc.)");
        deTranslations.addProperty("help.materials.items", "Gegenstände - Alle Items (Schwerter, Essen, Werkzeuge, etc.)");
        deTranslations.addProperty("help.materials.all", "Alle - Alles Verfügbare");
        deTranslations.addProperty("help.materials.usage", "Verwendung: /toggle <Material_ID> <actionbar|chat|title> [Statistik-Kategorie]");
        deTranslations.addProperty("help.color.title", "=== Farbanpassung ===");
        deTranslations.addProperty("help.color.header", "Ändere Farben für verschiedene Textelemente:");
        deTranslations.addProperty("help.color.text", "/toggle color text <Farbe> - Haupttextfarbe");
        deTranslations.addProperty("help.color.category", "/toggle color category <Farbe> - Statistik-Kategoriefarbe");
        deTranslations.addProperty("help.color.material", "/toggle color material <Farbe> - Materialnamenfarbe");
        deTranslations.addProperty("help.color.number", "/toggle color number <Farbe> - Zahlenfarbe");
        deTranslations.addProperty("help.color.time", "/toggle color time <Farbe> - Spielzeit-Anzeigefarbe");
        deTranslations.addProperty("help.color.colors", "Farben können sein: ROT, GRÜN, BLAU, GELB, etc.");
        deTranslations.addProperty("help.color.hex", "Oder Hex-Werte verwenden: '#FF0000' (rot), '#00FF00' (grün)");
        deTranslations.addProperty("help.color.none", "Verwende 'NONE' um auf Standard zurückzusetzen");
        deTranslations.addProperty("help.clear.title", "=== Einstellungen zurücksetzen ===");
        deTranslations.addProperty("help.clear.header", "Setze alle deine Toggle-Einstellungen zurück:");
        deTranslations.addProperty("help.clear.command", "/toggle clear");
        deTranslations.addProperty("help.clear.removes", "Dies entfernt:");
        deTranslations.addProperty("help.clear.toggles", "- Deine aktuellen Toggle-Einstellungen");
        deTranslations.addProperty("help.clear.colors", "- Alle Farbpräferenzen");
        deTranslations.addProperty("help.clear.language", "- Sprachauswahl");
        deTranslations.addProperty("help.clear.note", "Du musst alles nach dem Zurücksetzen neu einstellen.");
        deTranslations.addProperty("help.language.title", "=== Sprachoptionen ===");
        deTranslations.addProperty("help.language.available", "Verfügbare Sprachen:");
        deTranslations.addProperty("help.language.change", "Ändere deine Anzeigesprache:");
        deTranslations.addProperty("help.language.command", "/toggle language <Sprachcode>");
        deTranslations.addProperty("help.language.example", "Beispiel: /toggle language de_de");
        createLangFile("de_de.json", deTranslations);

        // French (fr_fr)
        JsonObject frTranslations = new JsonObject();
        frTranslations.addProperty("time.years", "a");
        frTranslations.addProperty("time.days", "j");
        frTranslations.addProperty("time.hours", "h");
        frTranslations.addProperty("time.minutes", "m");
        frTranslations.addProperty("time.seconds", "s");
        frTranslations.addProperty("command.disabled", "Commande désactivée");
        frTranslations.addProperty("command.player_only", "La commande doit être exécutée par un joueur");
        frTranslations.addProperty("command.invalid_language", "Code de langue invalide");
        frTranslations.addProperty("command.invalid_toggle", "Ce toggle n'est pas autorisé dans le mode actuel (%s)");
        frTranslations.addProperty("command.invalid_hex_color", "Format de couleur hexadécimal invalide. Utilisez RRGGBB ou #RRGGBB");
        frTranslations.addProperty("command.error_saving", "Erreur lors de l'enregistrement de votre préférence %s.");
        frTranslations.addProperty("command.reset_success", "Vos préférences de toggle ont été réinitialisées.");
        frTranslations.addProperty("command.reset_failed", "Échec de la réinitialisation de vos préférences de toggle.");
        frTranslations.addProperty("command.reset_none", "Vous n'aviez aucune préférence de toggle à réinitialiser.");
        frTranslations.addProperty("command.reloaded", "Configuration rechargée.");
        frTranslations.addProperty("command.no_content", "Aucun contenu disponible pour cette sous-commande.");
        frTranslations.addProperty("command.no_subcommands", "Aucune sous-commande disponible. Vérifiez votre configuration.");
        frTranslations.addProperty("command.available_commands", "Sous-commandes disponibles :");
        frTranslations.addProperty("command.usage", "Utilisation : %s");
        frTranslations.addProperty("command.available_colors", "Couleurs disponibles : %s");
        frTranslations.addProperty("command.available_languages", "Langues disponibles : %s");
        frTranslations.addProperty("command.hex_format", "Le format hexadécimal peut être RRGGBB ou #RRGGBB");
        frTranslations.addProperty("timeplayed.set", "Définir l'affichage du temps de jeu en %s");
        frTranslations.addProperty("language.set", "Définir la langue sur %s");
        frTranslations.addProperty("color.set", "Définir la couleur %s sur %s");
        frTranslations.addProperty("color.reset", "Réinitialiser la couleur %s par défaut");
        frTranslations.addProperty("toggle.set", "Définir le toggle %s pour afficher dans %s avec les statistiques %s");
        frTranslations.addProperty("item.stone.name", "Pierre");
        frTranslations.addProperty("item.dirt.name", "Terre");
        frTranslations.addProperty("item.oak_log.name", "Bûche de chêne");
        frTranslations.addProperty("color.set.part1", "Définir ");
        frTranslations.addProperty("color.set.part2", " couleur sur ");
        frTranslations.addProperty("color.reset.part1", "Réinitialiser ");
        frTranslations.addProperty("color.reset.part2", " couleur par défaut");
        frTranslations.addProperty("stat.mined", "Miné");
        frTranslations.addProperty("stat.crafted", "Fabriqué");
        frTranslations.addProperty("stat.used", "Utilisé");
        frTranslations.addProperty("stat.broken", "Cassé");
        frTranslations.addProperty("stat.picked_up", "Ramassé");
        frTranslations.addProperty("stat.dropped", "Jeté");
        frTranslations.addProperty("stat.killed", "Tué");
        frTranslations.addProperty("stat.killed_by", "Tué par");
        frTranslations.addProperty("stat.custom", "Personnalisé");
        frTranslations.addProperty("stat.timeplayed", "Temps de jeu");
        frTranslations.addProperty("help.general.title", "=== Aide de la commande Toggle ===");
        frTranslations.addProperty("help.general.materials", "/toggle help materials - Afficher les catégories de matériaux disponibles");
        frTranslations.addProperty("help.general.color", "/toggle help color - Afficher l'aide pour la personnalisation des couleurs");
        frTranslations.addProperty("help.general.clear", "/toggle help clear - Montrer comment réinitialiser vos paramètres");
        frTranslations.addProperty("help.general.language", "/toggle help language - Afficher les options de langue");
        frTranslations.addProperty("help.general.usage", "Utilisation: /toggle <matériel> <emplacement> [catégorie_stat]");
        frTranslations.addProperty("help.materials.title", "=== Catégories de matériaux ===");
        frTranslations.addProperty("help.materials.header", "Types de matériaux disponibles:");
        frTranslations.addProperty("help.materials.mobs", "Mobs - Toutes les entités vivantes (zombies, creepers, etc.)");
        frTranslations.addProperty("help.materials.blocks", "Blocs - Tous les blocs placables (pierre, terre, etc.)");
        frTranslations.addProperty("help.materials.items", "Objets - Tous les items (épées, nourriture, outils, etc.)");
        frTranslations.addProperty("help.materials.all", "Tout - Tout ce qui est disponible");
        frTranslations.addProperty("help.materials.usage", "Utilisation: /toggle <id_matériel> <actionbar|chat|title> [catégorie_stat]");
        frTranslations.addProperty("help.color.title", "=== Personnalisation des couleurs ===");
        frTranslations.addProperty("help.color.header", "Changez les couleurs pour différents éléments de texte:");
        frTranslations.addProperty("help.color.text", "/toggle color text <couleur> - Couleur du texte principal");
        frTranslations.addProperty("help.color.category", "/toggle color category <couleur> - Couleur de la catégorie de statistiques");
        frTranslations.addProperty("help.color.material", "/toggle color material <couleur> - Couleur du nom du matériau");
        frTranslations.addProperty("help.color.number", "/toggle color number <couleur> - Couleur des nombres");
        frTranslations.addProperty("help.color.time", "/toggle color time <couleur> - Couleur d'affichage du temps de jeu");
        frTranslations.addProperty("help.color.colors", "Couleurs possibles: ROUGE, VERT, BLEU, JAUNE, etc.");
        frTranslations.addProperty("help.color.hex", "Ou utilisez des valeurs hexadécimales: '#FF0000' (rouge), '#00FF00' (vert)");
        frTranslations.addProperty("help.color.none", "Utilisez 'NONE' pour réinitialiser à la valeur par défaut");
        frTranslations.addProperty("clear.title", "=== Réinitialisation des paramètres ===");
        frTranslations.addProperty("help.clear.header", "Réinitialisez toutes vos préférences de toggle:");
        frTranslations.addProperty("help.clear.command", "/toggle clear");
        frTranslations.addProperty("help.clear.removes", "Cela supprimera:");
        frTranslations.addProperty("help.clear.toggles", "- Vos paramètres de toggle actuels");
        frTranslations.addProperty("help.clear.colors", "- Toutes les préférences de couleur");
        frTranslations.addProperty("help.clear.language", "- Sélection de la langue");
        frTranslations.addProperty("help.clear.note", "Vous devrez tout reconfigurer après la réinitialisation.");
        frTranslations.addProperty("help.language.title", "=== Options de langue ===");
        frTranslations.addProperty("help.language.available", "Langues disponibles:");
        frTranslations.addProperty("help.language.change", "Changez votre langue d'affichage:");
        frTranslations.addProperty("help.language.command", "/toggle language <code_langue>");
        frTranslations.addProperty("help.language.example", "Exemple: /toggle language fr_fr");
        createLangFile("fr_fr.json", frTranslations);

        // Spanish (es_es)
        JsonObject esTranslations = new JsonObject();
        esTranslations.addProperty("time.years", "a");
        esTranslations.addProperty("time.days", "d");
        esTranslations.addProperty("time.hours", "h");
        esTranslations.addProperty("time.minutes", "m");
        esTranslations.addProperty("time.seconds", "s");
        esTranslations.addProperty("command.disabled", "Comando desactivado");
        esTranslations.addProperty("command.player_only", "El comando debe ser ejecutado por un jugador");
        esTranslations.addProperty("command.invalid_language", "Código de idioma inválido");
        esTranslations.addProperty("command.invalid_toggle", "Este toggle no está permitido en el modo actual (%s)");
        esTranslations.addProperty("command.invalid_hex_color", "Formato de color hexadecimal inválido. Usa RRGGBB o #RRGGBB");
        esTranslations.addProperty("command.error_saving", "Error al guardar tu preferencia %s.");
        esTranslations.addProperty("command.reset_success", "Tus preferencias de toggle han sido reiniciadas.");
        esTranslations.addProperty("command.reset_failed", "Error al reiniciar tus preferencias de toggle.");
        esTranslations.addProperty("command.reset_none", "No tenías preferencias de toggle para reiniciar.");
        esTranslations.addProperty("command.reloaded", "Configuración recargada.");
        esTranslations.addProperty("command.no_content", "No hay contenido disponible para este subcomando.");
        esTranslations.addProperty("command.no_subcommands", "No hay subcomandos disponibles. Revisa tu configuración.");
        esTranslations.addProperty("command.available_commands", "Subcomandos disponibles:");
        esTranslations.addProperty("command.usage", "Uso: %s");
        esTranslations.addProperty("command.available_colors", "Colores disponibles: %s");
        esTranslations.addProperty("command.available_languages", "Idiomas disponibles: %s");
        esTranslations.addProperty("command.hex_format", "El formato hexadecimal puede ser RRGGBB o #RRGGBB");
        esTranslations.addProperty("timeplayed.set", "Establecer tiempo jugado para mostrar en %s");
        esTranslations.addProperty("language.set", "Establecer idioma a %s");
        esTranslations.addProperty("color.set", "Establecer color %s a %s");
        esTranslations.addProperty("color.reset", "Reiniciar color %s a predeterminado");
        esTranslations.addProperty("toggle.set", "Establecer toggle %s para mostrar en %s con estadísticas %s");
        esTranslations.addProperty("item.stone.name", "Piedra");
        esTranslations.addProperty("item.dirt.name", "Tierra");
        esTranslations.addProperty("item.oak_log.name", "Tronco de roble");
        esTranslations.addProperty("color.set.part1", "Establecer ");
        esTranslations.addProperty("color.set.part2", " color a ");
        esTranslations.addProperty("color.reset.part1", "Reiniciar ");
        esTranslations.addProperty("color.reset.part2", " color a predeterminado");
        esTranslations.addProperty("stat.mined", "Minado");
        esTranslations.addProperty("stat.crafted", "Fabricado");
        esTranslations.addProperty("stat.used", "Usado");
        esTranslations.addProperty("stat.broken", "Roto");
        esTranslations.addProperty("stat.picked_up", "Recogido");
        esTranslations.addProperty("stat.dropped", "Tirado");
        esTranslations.addProperty("stat.killed", "Matado");
        esTranslations.addProperty("stat.killed_by", "Matado por");
        esTranslations.addProperty("stat.custom", "Personalizado");
        esTranslations.addProperty("stat.timeplayed", "Tiempo jugado");
        esTranslations.addProperty("help.general.title", "=== Ayuda del comando Toggle ===");
        esTranslations.addProperty("help.general.materials", "/toggle help materials - Mostrar categorías de materiales disponibles");
        esTranslations.addProperty("help.general.color", "/toggle help color - Mostrar ayuda para personalización de colores");
        esTranslations.addProperty("help.general.clear", "/toggle help clear - Mostrar cómo reiniciar tus configuraciones");
        esTranslations.addProperty("help.general.language", "/toggle help language - Mostrar opciones de idioma");
        esTranslations.addProperty("help.general.usage", "Uso: /toggle <material> <ubicación> [categoría_estadística]");
        esTranslations.addProperty("help.materials.title", "=== Categorías de materiales ===");
        esTranslations.addProperty("help.materials.header", "Tipos de materiales disponibles:");
        esTranslations.addProperty("help.help.materials.mobs", "Mobs - Todas las entidades vivas (zombies, creepers, etc.)");
        esTranslations.addProperty("help.materials.blocks", "Bloques - Todos los bloques colocables (piedra, tierra, etc.)");
        esTranslations.addProperty("help.materials.items", "Objetos - Todos los items (espadas, comida, herramientas, etc.)");
        esTranslations.addProperty("help.materials.all", "Todo - Todo lo disponible");
        esTranslations.addProperty("help.materials.usage", "Uso: /toggle <id_material> <actionbar|chat|title> [categoría_estadística]");
        esTranslations.addProperty("help.help.color.title", "=== Personalización de colores ===");
        esTranslations.addProperty("help.color.header", "Cambia colores para diferentes elementos de texto:");
        esTranslations.addProperty("help.color.text", "/toggle color text <color> - Color del texto principal");
        esTranslations.addProperty("help.color.category", "/toggle color category <color> - Color de la categoría de estadísticas");
        esTranslations.addProperty("help.help.color.material", "/toggle color material <color> - Color del nombre del material");
        esTranslations.addProperty("help.color.number", "/toggle color number <color> - Color de los números");
        esTranslations.addProperty("help.color.time", "/toggle color time <color> - Color del tiempo de juego");
        esTranslations.addProperty("help.color.colors", "Colores pueden ser: ROJO, VERDE, AZUL, AMARILLO, etc.");
        esTranslations.addProperty("help.color.hex", "O usa valores hexadecimales: '#FF0000' (rojo), '#00FF00' (verde)");
        esTranslations.addProperty("help.color.none", "Usa 'NONE' para reiniciar al predeterminado");
        esTranslations.addProperty("help.clear.title", "=== Reiniciar configuraciones ===");
        esTranslations.addProperty("help.clear.header", "Reinicia todas tus preferencias de toggle:");
        esTranslations.addProperty("help.clear.command", "/toggle clear");
        esTranslations.addProperty("help.clear.removes", "Esto eliminará:");
        esTranslations.addProperty("help.clear.toggles", "- Tus configuraciones actuales de toggle");
        esTranslations.addProperty("help.clear.colors", "- Todas las preferencias de color");
        esTranslations.addProperty("help.clear.language", "- Selección de idioma");
        esTranslations.addProperty("help.clear.note", "Necesitarás configurar todo de nuevo después de reiniciar.");
        esTranslations.addProperty("help.language.title", "=== Opciones de idioma ===");
        esTranslations.addProperty("help.language.available", "Idiomas disponibles:");
        esTranslations.addProperty("help.language.change", "Cambia tu idioma de visualización:");
        esTranslations.addProperty("help.language.command", "/toggle language <código_idioma>");
        esTranslations.addProperty("help.language.example", "Ejemplo: /toggle language es_es");
        createLangFile("es_es.json", esTranslations);

        // Dutch (nl_nl)
        JsonObject nlTranslations = new JsonObject();
        nlTranslations.addProperty("time.years", "j");
        nlTranslations.addProperty("time.days", "d");
        nlTranslations.addProperty("time.hours", "u");
        nlTranslations.addProperty("time.minutes", "m");
        nlTranslations.addProperty("time.seconds", "s");
        nlTranslations.addProperty("command.disabled", "Commando uitgeschakeld");
        nlTranslations.addProperty("command.player_only", "Commando moet door een speler worden uitgevoerd");
        nlTranslations.addProperty("command.invalid_language", "Ongeldige taalcode");
        nlTranslations.addProperty("command.invalid_toggle", "Deze toggle is niet toegestaan in de huidige modus (%s)");
        nlTranslations.addProperty("command.invalid_hex_color", "Ongeldig hex kleurformaat. Gebruik RRGGBB of #RRGGBB");
        nlTranslations.addProperty("command.error_saving", "Fout bij het opslaan van je %s voorkeur.");
        nlTranslations.addProperty("command.reset_success", "Je toggle voorkeuren zijn gereset.");
        nlTranslations.addProperty("command.reset_failed", "Resetten van je toggle voorkeuren mislukt.");
        nlTranslations.addProperty("command.reset_none", "Je had geen toggle voorkeuren om te resetten.");
        nlTranslations.addProperty("command.reloaded", "Configuratie herladen.");
        nlTranslations.addProperty("command.no_content", "Geen inhoud beschikbaar voor dit subcommando.");
        nlTranslations.addProperty("command.no_subcommands", "Geen subcommando's beschikbaar. Controleer je configuratie.");
        nlTranslations.addProperty("command.available_commands", "Beschikbare subcommando's:");
        nlTranslations.addProperty("command.usage", "Gebruik: %s");
        nlTranslations.addProperty("command.available_colors", "Beschikbare kleuren: %s");
        nlTranslations.addProperty("command.available_languages", "Beschikbare talen: %s");
        nlTranslations.addProperty("command.hex_format", "Hex-formaat kan RRGGBB of #RRGGBB zijn");
        nlTranslations.addProperty("timeplayed.set", "Speeltijd weergeven in %s");
        nlTranslations.addProperty("language.set", "Taal instellen op %s");
        nlTranslations.addProperty("color.set", "Stel %s kleur in op %s");
        nlTranslations.addProperty("color.reset", "Reset %s kleur naar standaard");
        nlTranslations.addProperty("toggle.set", "Stel toggle %s in om weer te geven in %s met %s statistieken");
        nlTranslations.addProperty("item.stone.name", "Steen");
        nlTranslations.addProperty("item.dirt.name", "Aarde");
        nlTranslations.addProperty("item.oak_log.name", "Eikenstam");
        nlTranslations.addProperty("color.set.part1", "Stel ");
        nlTranslations.addProperty("color.set.part2", " kleur in op ");
        nlTranslations.addProperty("color.reset.part1", "Reset ");
        nlTranslations.addProperty("color.reset.part2", " kleur naar standaard");
        nlTranslations.addProperty("stat.mined", "Gedolven");
        nlTranslations.addProperty("stat.crafted", "Vervaardigd");
        nlTranslations.addProperty("stat.used", "Gebruikt");
        nlTranslations.addProperty("stat.broken", "Gebroken");
        nlTranslations.addProperty("stat.picked_up", "Opgepakt");
        nlTranslations.addProperty("stat.dropped", "Gedropt");
        nlTranslations.addProperty("stat.killed", "Gedood");
        nlTranslations.addProperty("stat.killed_by", "Gedood door");
        nlTranslations.addProperty("stat.custom", "Aangepast");
        nlTranslations.addProperty("stat.timeplayed", "Speeltijd");
        nlTranslations.addProperty("help.general.title", "=== Toggle Commando Hulp ===");
        nlTranslations.addProperty("help.general.materials", "/toggle help materials - Toon beschikbare materiaalcategorieën");
        nlTranslations.addProperty("help.general.color", "/toggle help color - Toon kleuraanpassingshulp");
        nlTranslations.addProperty("help.general.clear", "/toggle help clear - Toon hoe je instellingen reset");
        nlTranslations.addProperty("help.general.language", "/toggle help language - Toon taalopties");
        nlTranslations.addProperty("help.general.usage", "Gebruik: /toggle <materiaal> <locatie> [statistiek_categorie]");
        nlTranslations.addProperty("help.materials.title", "=== Materiaalcategorieën ===");
        nlTranslations.addProperty("help.materials.header", "Beschikbare materiaalsoorten:");
        nlTranslations.addProperty("help.materials.mobs", "Mobs - Alle levende wezens (zombies, creepers, etc.)");
        nlTranslations.addProperty("help.materials.blocks", "Blokken - Alle plaatsbare blokken (steen, aarde, etc.)");
        nlTranslations.addProperty("help.materials.items", "Items - Alle items (zwaarden, eten, gereedschap, etc.)");
        nlTranslations.addProperty("help.materials.all", "Alles - Alles beschikbaar");
        nlTranslations.addProperty("help.materials.usage", "Gebruik: /toggle <materiaal_id> <actionbar|chat|title> [statistiek_categorie]");
        nlTranslations.addProperty("help.color.title", "=== Kleuraanpassing ===");
        nlTranslations.addProperty("help.color.header", "Verander kleuren voor verschillende tekstelementen:");
        nlTranslations.addProperty("help.color.text", "/toggle color text <kleur> - Hoofdtekstkleur");
        nlTranslations.addProperty("help.color.category", "/toggle color category <kleur> - Statistiekcategoriekleur");
        nlTranslations.addProperty("help.color.material", "/toggle color material <kleur> - Materiaalnaamkleur");
        nlTranslations.addProperty("help.color.number", "/toggle color number <kleur> - Cijferkleur");
        nlTranslations.addProperty("help.color.time", "/toggle color time <kleur> - Speeltijd weergavekleur");
        nlTranslations.addProperty("help.color.colors", "Kleuren kunnen zijn: ROOD, GROEN, BLAUW, GEEL, etc.");
        nlTranslations.addProperty("help.color.hex", "Of gebruik hex-waarden: '#FF0000' (rood), '#00FF00' (groen)");
        nlTranslations.addProperty("help.color.none", "Gebruik 'NONE' om terug te zetten naar standaard");
        nlTranslations.addProperty("help.clear.title", "=== Instellingen resetten ===");
        nlTranslations.addProperty("help.clear.header", "Reset al je toggle voorkeuren:");
        nlTranslations.addProperty("help.clear.command", "/toggle clear");
        nlTranslations.addProperty("help.clear.removes", "Dit verwijdert:");
        nlTranslations.addProperty("help.clear.toggles", "- Je huidige toggle instellingen");
        nlTranslations.addProperty("help.clear.colors", "- Alle kleurvoorkeuren");
        nlTranslations.addProperty("help.clear.language", "- Taalselectie");
        nlTranslations.addProperty("help.clear.note", "Je moet alles opnieuw instellen na het resetten.");
        nlTranslations.addProperty("help.language.title", "=== Taalopties ===");
        nlTranslations.addProperty("help.language.available", "Beschikbare talen:");
        nlTranslations.addProperty("help.language.change", "Verander je weergavetaal:");
        nlTranslations.addProperty("help.language.command", "/toggle language <taalcode>");
        nlTranslations.addProperty("help.language.example", "Voorbeeld: /toggle language nl_nl");
        createLangFile("nl_nl.json", nlTranslations);

        // Russian (ru_ru)
        JsonObject ruTranslations = new JsonObject();
        ruTranslations.addProperty("time.years", "г");
        ruTranslations.addProperty("time.days", "д");
        ruTranslations.addProperty("time.hours", "ч");
        ruTranslations.addProperty("time.minutes", "м");
        ruTranslations.addProperty("time.seconds", "с");
        ruTranslations.addProperty("command.disabled", "Команда отключена");
        ruTranslations.addProperty("command.player_only", "Команда должна выполняться игроком");
        ruTranslations.addProperty("command.invalid_language", "Неверный код языка");
        ruTranslations.addProperty("command.invalid_toggle", "Этот переключатель не разрешен в текущем режиме (%s)");
        ruTranslations.addProperty("command.invalid_hex_color", "Неверный формат hex-цвета. Используйте RRGGBB или #RRGGBB");
        ruTranslations.addProperty("command.error_saving", "Ошибка при сохранении вашего предпочтения %s.");
        ruTranslations.addProperty("command.reset_success", "Ваши настройки переключателя были сброшены.");
        ruTranslations.addProperty("command.reset_failed", "Не удалось сбросить настройки переключателя.");
        ruTranslations.addProperty("command.reset_none", "У вас не было настроек переключателя для сброса.");
        ruTranslations.addProperty("command.reloaded", "Конфигурация перезагружена.");
        ruTranslations.addProperty("command.no_content", "Нет содержимого для этой подкоманды.");
        ruTranslations.addProperty("command.no_subcommands", "Нет доступных подкоманд. Проверьте вашу конфигурацию.");
        ruTranslations.addProperty("command.available_commands", "Доступные подкоманды:");
        ruTranslations.addProperty("command.usage", "Использование: %s");
        ruTranslations.addProperty("command.available_colors", "Доступные цвета: %s");
        ruTranslations.addProperty("command.available_languages", "Доступные языки: %s");
        ruTranslations.addProperty("command.hex_format", "Hex-формат может быть RRGGBB или #RRGGBB");
        ruTranslations.addProperty("timeplayed.set", "Установить отображение игрового времени в %s");
        ruTranslations.addProperty("language.set", "Установить язык на %s");
        ruTranslations.addProperty("color.set", "Установить цвет %s на %s");
        ruTranslations.addProperty("color.reset", "Сбросить цвет %s на стандартный");
        ruTranslations.addProperty("toggle.set", "Установить переключатель %s для отображения в %s с %s статистикой");
        ruTranslations.addProperty("item.stone.name", "Камень");
        ruTranslations.addProperty("item.dirt.name", "Земля");
        ruTranslations.addProperty("item.oak_log.name", "Дубовое бревно");
        ruTranslations.addProperty("color.set.part1", "Установить ");
        ruTranslations.addProperty("color.set.part2", " цвет в ");
        ruTranslations.addProperty("color.reset.part1", "Сбросить ");
        ruTranslations.addProperty("color.reset.part2", " цвет на стандартный");
        ruTranslations.addProperty("stat.mined", "Добыто");
        ruTranslations.addProperty("stat.crafted", "Создано");
        ruTranslations.addProperty("stat.used", "Использовано");
        ruTranslations.addProperty("stat.broken", "Сломано");
        ruTranslations.addProperty("stat.picked_up", "Подобрано");
        ruTranslations.addProperty("stat.dropped", "Выброшено");
        ruTranslations.addProperty("stat.killed", "Убито");
        ruTranslations.addProperty("stat.killed_by", "Убито");
        ruTranslations.addProperty("stat.custom", "Пользовательский");
        ruTranslations.addProperty("stat.timeplayed", "Время игры");
        ruTranslations.addProperty("help.general.title", "=== Помощь по команде Toggle ===");
        ruTranslations.addProperty("help.general.materials", "/toggle help materials - Показать доступные категории материалов");
        ruTranslations.addProperty("help.general.color", "/toggle help color - Показать помощь по настройке цветов");
        ruTranslations.addProperty("help.general.clear", "/toggle help clear - Показать как сбросить настройки");
        ruTranslations.addProperty("help.general.language", "/toggle help language - Показать языковые опции");
        ruTranslations.addProperty("help.general.usage", "Использование: /toggle <материал> <место> [категория_статистики]");
        ruTranslations.addProperty("help.materials.title", "=== Категории материалов ===");
        ruTranslations.addProperty("help.materials.header", "Доступные типы материалов:");
        ruTranslations.addProperty("help.materials.mobs", "Мобы - Все живые существа (зомби, криперы и т.д.)");
        ruTranslations.addProperty("help.materials.blocks", "Блоки - Все размещаемые блоки (камень, земля и т.д.)");
        ruTranslations.addProperty("help.materials.items", "Предметы - Все предметы (мечи, еда, инструменты и т.д.)");
        ruTranslations.addProperty("help.materials.all", "Все - Все доступное");
        ruTranslations.addProperty("help.materials.usage", "Использование: /toggle <ID_материала> <actionbar|chat|title> [категория_статистики]");
        ruTranslations.addProperty("help.color.title", "=== Настройка цветов ===");
        ruTranslations.addProperty("help.color.header", "Измените цвета для разных элементов текста:");
        ruTranslations.addProperty("help.color.text", "/toggle color text <цвет> - Основной цвет текста");
        ruTranslations.addProperty("help.color.category", "/toggle color category <цвет> - Цвет категории статистики");
        ruTranslations.addProperty("help.color.material", "/toggle color material <цвет> - Цвет названия материала");
        ruTranslations.addProperty("help.color.number", "/toggle color number <цвет> - Цвет чисел");
        ruTranslations.addProperty("help.color.time", "/toggle color time <цвет> - Цвет отображения игрового времени");
        ruTranslations.addProperty("help.color.colors", "Цвета могут быть: КРАСНЫЙ, ЗЕЛЕНЫЙ, СИНИЙ, ЖЕЛТЫЙ и т.д.");
        ruTranslations.addProperty("help.color.hex", "Или используйте hex-значения: '#FF0000' (красный), '#00FF00' (зеленый)");
        ruTranslations.addProperty("help.color.none", "Используйте 'NONE' для сброса к значению по умолчанию");
        ruTranslations.addProperty("help.clear.title", "=== Сброс настроек ===");
        ruTranslations.addProperty("help.clear.header", "Сбросьте все ваши настройки переключателя:");
        ruTranslations.addProperty("help.clear.command", "/toggle clear");
        ruTranslations.addProperty("help.clear.removes", "Это удалит:");
        ruTranslations.addProperty("help.clear.toggles", "- Ваши текущие настройки переключателя");
        ruTranslations.addProperty("help.clear.colors", "- Все цветовые предпочтения");
        ruTranslations.addProperty("help.clear.language", "- Выбор языка");
        ruTranslations.addProperty("help.clear.note", "Вам нужно будет заново настроить все после сброса.");
        ruTranslations.addProperty("help.language.title", "=== Языковые опции ===");
        ruTranslations.addProperty("help.language.available", "Доступные языки:");
        ruTranslations.addProperty("help.language.change", "Измените ваш язык отображения:");
        ruTranslations.addProperty("help.language.command", "/toggle language <код_языка>");
        ruTranslations.addProperty("help.language.example", "Пример: /toggle language ru_ru");
        createLangFile("ru_ru.json", ruTranslations);

        // Hindi (hi_in)
        JsonObject hiTranslations = new JsonObject();
        hiTranslations.addProperty("time.years", "वर्ष");
        hiTranslations.addProperty("time.days", "दिन");
        hiTranslations.addProperty("time.hours", "घंटे");
        hiTranslations.addProperty("time.minutes", "मिनट");
        hiTranslations.addProperty("time.seconds", "सेकंड");
        hiTranslations.addProperty("command.disabled", "कमांड अक्षम");
        hiTranslations.addProperty("command.player_only", "कमांड केवल एक खिलाड़ी द्वारा निष्पादित की जानी चाहिए");
        hiTranslations.addProperty("command.invalid_language", "अमान्य भाषा कोड");
        hiTranslations.addProperty("command.invalid_toggle", "यह टॉगल वर्तमान मोड में अनुमति नहीं है (%s)");
        hiTranslations.addProperty("command.invalid_hex_color", "अमान्य हेक्स रंग प्रारूप। RRGGBB या #RRGGBB का उपयोग करें");
        hiTranslations.addProperty("command.error_saving", "आपकी %s प्राथमिकता सहेजने में त्रुटि।");
        hiTranslations.addProperty("command.reset_success", "आपकी टॉगल प्राथमिकताएँ रीसेट कर दी गई हैं।");
        hiTranslations.addProperty("command.reset_failed", "आपकी टॉगल प्राथमिकताएँ रीसेट करने में विफल।");
        hiTranslations.addProperty("command.reset_none", "रीसेट करने के लिए आपके पास कोई टॉगल प्राथमिकता नहीं थी।");
        hiTranslations.addProperty("command.reloaded", "कॉन्फ़िगरेशन पुनः लोड किया गया।");
        hiTranslations.addProperty("command.no_content", "इस सबकमांड के लिए कोई सामग्री उपलब्ध नहीं है।");
        hiTranslations.addProperty("command.no_subcommands", "कोई सबकमांड उपलब्ध नहीं है। अपनी कॉन्फ़िगरेशन जांचें।");
        hiTranslations.addProperty("command.available_commands", "उपलब्ध सबकमांड:");
        hiTranslations.addProperty("command.usage", "उपयोग: %s");
        hiTranslations.addProperty("command.available_colors", "उपलब्ध रंग: %s");
        hiTranslations.addProperty("command.available_languages", "उपलब्ध भाषाएँ: %s");
        hiTranslations.addProperty("command.hex_format", "हेक्स प्रारूप RRGGBB या #RRGGBB हो सकता है");
        hiTranslations.addProperty("timeplayed.set", "खेले गए समय को %s में प्रदर्शित करने के लिए सेट करें");
        hiTranslations.addProperty("language.set", "भाषा को %s पर सेट करें");
        hiTranslations.addProperty("color.set", "%s रंग को %s पर सेट करें");
        hiTranslations.addProperty("color.reset", "%s रंग को डिफ़ॉल्ट पर रीसेट करें");
        hiTranslations.addProperty("toggle.set", "टॉगल %s को %s में %s सांख्यिकी के साथ प्रदर्शित करने के लिए सेट करें");
        hiTranslations.addProperty("item.stone.name", "पत्थर");
        hiTranslations.addProperty("item.dirt.name", "मिट्टी");
        hiTranslations.addProperty("item.oak_log.name", "ओक लॉग");
        hiTranslations.addProperty("color.set.part1", "सेट ");
        hiTranslations.addProperty("color.set.part2", " रंग को ");
        hiTranslations.addProperty("color.reset.part1", "रीसेट ");
        hiTranslations.addProperty("color.reset.part2", " रंग डिफ़ॉल्ट पर");
        hiTranslations.addProperty("stat.mined", "खनन किया");
        hiTranslations.addProperty("stat.crafted", "निर्मित");
        hiTranslations.addProperty("stat.used", "उपयोग किया");
        hiTranslations.addProperty("stat.broken", "तोड़ा");
        hiTranslations.addProperty("stat.picked_up", "उठाया");
        hiTranslations.addProperty("stat.dropped", "गिराया");
        hiTranslations.addProperty("stat.killed", "मारा");
        hiTranslations.addProperty("stat.killed_by", "द्वारा मारा");
        hiTranslations.addProperty("stat.custom", "कस्टम");
        hiTranslations.addProperty("stat.timeplayed", "खेला गया समय");
        hiTranslations.addProperty("help.general.title", "=== टॉगल कमांड सहायता ===");
        hiTranslations.addProperty("help.general.materials", "/toggle help materials - उपलब्ध सामग्री श्रेणियाँ दिखाएँ");
        hiTranslations.addProperty("help.general.color", "/toggle help color - रंग अनुकूलन सहायता दिखाएँ");
        hiTranslations.addProperty("help.general.clear", "/toggle help clear - आपकी सेटिंग्स रीसेट करने का तरीका दिखाएँ");
        hiTranslations.addProperty("help.general.language", "/toggle help language - भाषा विकल्प दिखाएँ");
        hiTranslations.addProperty("help.general.usage", "उपयोग: /toggle <सामग्री> <स्थान> [सांख्यिकी_श्रेणी]");
        hiTranslations.addProperty("help.materials.title", "=== सामग्री श्रेणियाँ ===");
        hiTranslations.addProperty("help.materials.header", "उपलब्ध सामग्री प्रकार:");
        hiTranslations.addProperty("help.materials.mobs", "मॉब्स - सभी जीवित इकाइयाँ (ज़ोंबी, क्रीपर आदि)");
        hiTranslations.addProperty("help.materials.blocks", "ब्लॉक्स - सभी रखने योग्य ब्लॉक्स (पत्थर, मिट्टी आदि)");
        hiTranslations.addProperty("help.materials.items", "आइटम्स - सभी आइटम्स (तलवारें, भोजन, उपकरण आदि)");
        hiTranslations.addProperty("materials.all", "सभी - सब कुछ उपलब्ध");
        hiTranslations.addProperty("help.materials.usage", "उपयोग: /toggle <सामग्री_ID> <actionbar|chat|title> [सांख्यिकी_श्रेणी]");
        hiTranslations.addProperty("help.color.title", "=== रंग अनुकूलन ===");
        hiTranslations.addProperty("help.color.header", "विभिन्न पाठ तत्वों के लिए रंग बदलें:");
        hiTranslations.addProperty("help.color.text", "/toggle color text <रंग> - मुख्य पाठ रंग");
        hiTranslations.addProperty("help.color.category", "/toggle color category <रंग> - सांख्यिकी श्रेणी रंग");
        hiTranslations.addProperty("help.color.material", "/toggle color material <रंग> - सामग्री नाम रंग");
        hiTranslations.addProperty("help.color.number", "/toggle color number <रंग> - संख्या रंग");
        hiTranslations.addProperty("help.color.time", "/toggle color time <रंग> - खेलने का समय प्रदर्शन रंग");
        hiTranslations.addProperty("help.color.colors", "रंग हो सकते हैं: लाल, हरा, नीला, पीला आदि");
        hiTranslations.addProperty("help.color.hex", "या हेक्स मानों का उपयोग करें: '#FF0000' (लाल), '#00FF00' (हरा)");
        hiTranslations.addProperty("help.color.none", "डिफ़ॉल्ट पर रीसेट करने के लिए 'NONE' का उपयोग करें");
        hiTranslations.addProperty("help.clear.title", "=== सेटिंग्स रीसेट करें ===");
        hiTranslations.addProperty("help.clear.header", "अपनी सभी टॉगल प्राथमिकताएँ रीसेट करें:");
        hiTranslations.addProperty("help.clear.command", "/toggle clear");
        hiTranslations.addProperty("help.clear.removes", "यह हटाएगा:");
        hiTranslations.addProperty("help.clear.toggles", "- आपकी वर्तमान टॉगल सेटिंग्स");
        hiTranslations.addProperty("help.clear.colors", "- सभी रंग प्राथमिकताएँ");
        hiTranslations.addProperty("help.clear.language", "- भाषा चयन");
        hiTranslations.addProperty("help.clear.note", "रीसेट करने के बाद आपको सब कुछ फिर से सेट करना होगा।");
        hiTranslations.addProperty("help.language.title", "=== भाषा विकल्प ===");
        hiTranslations.addProperty("help.language.available", "उपलब्ध भाषाएँ:");
        hiTranslations.addProperty("help.language.change", "अपनी प्रदर्शन भाषा बदलें:");
        hiTranslations.addProperty("help.language.command", "/toggle language <भाषा_कोड>");
        hiTranslations.addProperty("help.language.example", "उदाहरण: /toggle language hi_in");
        createLangFile("hi_in.json", hiTranslations);

        // Pirate Speak (en_pt)
        JsonObject pirateTranslations = new JsonObject();
        pirateTranslations.addProperty("time.years", "y, arr!");
        pirateTranslations.addProperty("time.days", "d, matey!");
        pirateTranslations.addProperty("time.hours", "h, aye!");
        pirateTranslations.addProperty("time.minutes", "m, arr!");
        pirateTranslations.addProperty("time.seconds", "s, ye scallywag!");
        pirateTranslations.addProperty("command.disabled", "Command disabled, arr!");
        pirateTranslations.addProperty("command.player_only", "Command must be executed by a player, ye scurvy dog!");
        pirateTranslations.addProperty("command.invalid_language", "Invalid language code, walk the plank!");
        pirateTranslations.addProperty("command.invalid_toggle", "This toggle be not allowed in current mode (%s), arr!");
        pirateTranslations.addProperty("command.invalid_hex_color", "Invalid hex color format. Use RRGGBB or #RRGGBB, ye bilge rat!");
        pirateTranslations.addProperty("command.error_saving", "Error savin' yer %s preference, arr!");
        pirateTranslations.addProperty("command.reset_success", "Yer toggle preferences have been reset, aye!");
        pirateTranslations.addProperty("command.reset_failed", "Failed to reset yer toggle preferences, walk the plank!");
        pirateTranslations.addProperty("command.reset_none", "Ye didn't have any toggle preferences to reset, ye scallywag!");
        pirateTranslations.addProperty("command.reloaded", "Configuration reloaded, arr!");
        pirateTranslations.addProperty("command.no_content", "No content available fer this subcommand, matey!");
        pirateTranslations.addProperty("command.no_subcommands", "No subcommands available. Check yer configuration, ye bilge rat!");
        pirateTranslations.addProperty("command.available_commands", "Available subcommands:");
        pirateTranslations.addProperty("command.usage", "Usage: %s, arr!");
        pirateTranslations.addProperty("command.available_colors", "Available colors: %s");
        pirateTranslations.addProperty("command.available_languages", "Available languages: %s");
        pirateTranslations.addProperty("command.hex_format", "Hex format can be either RRGGBB or #RRGGBB, aye!");
        pirateTranslations.addProperty("timeplayed.set", "Set timeplayed to display in %s, arr!");
        pirateTranslations.addProperty("language.set", "Set language to %s, ye scurvy dog!");
        pirateTranslations.addProperty("color.set", "Set %s color to %s, matey!");
        pirateTranslations.addProperty("color.reset", "Reset %s color to default, aye!");
        pirateTranslations.addProperty("toggle.set", "Set toggle %s to display in %s with %s statistics, arr!");
        pirateTranslations.addProperty("item.stone.name", "Stone, arr!");
        pirateTranslations.addProperty("item.dirt.name", "Dirt, matey!");
        pirateTranslations.addProperty("item.oak_log.name", "Oak Log, aye!");
        pirateTranslations.addProperty("color.set.part1", "Set ");
        pirateTranslations.addProperty("color.set.part2", " color to ");
        pirateTranslations.addProperty("color.reset.part1", "Reset ");
        pirateTranslations.addProperty("color.reset.part2", " color to default, arr!");
        pirateTranslations.addProperty("stat.mined", "Mined");
        pirateTranslations.addProperty("stat.crafted", "Crafted");
        pirateTranslations.addProperty("stat.used", "Used");
        pirateTranslations.addProperty("stat.broken", "Broken");
        pirateTranslations.addProperty("stat.picked_up", "Picked Up");
        pirateTranslations.addProperty("stat.dropped", "Dropped");
        pirateTranslations.addProperty("stat.killed", "Killed");
        pirateTranslations.addProperty("stat.killed_by", "Killed By");
        pirateTranslations.addProperty("stat.custom", "Custom");
        pirateTranslations.addProperty("stat.timeplayed", "Time Played, arr!");
        pirateTranslations.addProperty("help.general.title", "=== Toggle Command Help, arr! ===");
        pirateTranslations.addProperty("help.general.materials", "/toggle help materials - Show available material categories, matey!");
        pirateTranslations.addProperty("help.general.color", "/toggle help color - Show color customization help, aye!");
        pirateTranslations.addProperty("help.general.clear", "/toggle help clear - Show how to reset yer settings, arr!");
        pirateTranslations.addProperty("help.general.language", "/toggle help language - Show language options, ye scallywag!");
        pirateTranslations.addProperty("help.general.usage", "Usage: /toggle <material> <location> [stat_category], arr!");
        pirateTranslations.addProperty("help.materials.title", "=== Material Categories, aye! ===");
        pirateTranslations.addProperty("help.materials.header", "Available material types:");
        pirateTranslations.addProperty("help.materials.mobs", "Mobs - All livin' creatures (zombies, creepers, etc.), arr!");
        pirateTranslations.addProperty("help.materials.blocks", "Blocks - All placeable blocks (stone, dirt, etc.), aye!");
        pirateTranslations.addProperty("help.materials.items", "Items - All items (swords, grub, tools, etc.), matey!");
        pirateTranslations.addProperty("help.materials.all", "All - Everythin' available, arr!");
        pirateTranslations.addProperty("help.materials.usage", "Usage: /toggle <material_id> <actionbar|chat|title> [stat_category], aye!");
        pirateTranslations.addProperty("help.color.title", "=== Color Customization, arr! ===");
        pirateTranslations.addProperty("help.color.header", "Change colors fer different text elements:");
        pirateTranslations.addProperty("help.color.text", "/toggle color text <color> - Main text color, matey!");
        pirateTranslations.addProperty("help.color.category", "/toggle color category <color> - Stat category color, aye!");
        pirateTranslations.addProperty("help.color.material", "/toggle color material <color> - Material name color, arr!");
        pirateTranslations.addProperty("help.color.number", "/toggle color number <color> - Number color, ye scallywag!");
        pirateTranslations.addProperty("help.color.time", "/toggle color time <color> - Playtime display color, aye!");
        pirateTranslations.addProperty("color.colors", "Colors can be: RED, GREEN, BLUE, YELLOW, etc., arr!");
        pirateTranslations.addProperty("help.color.hex", "Or use hex values: '#FF0000' (red), '#00FF00' (green), matey!");
        pirateTranslations.addProperty("help.color.none", "Use 'NONE' to reset to default, aye!");
        pirateTranslations.addProperty("help.clear.title", "=== Reset Settings, arr! ===");
        pirateTranslations.addProperty("help.clear.header", "Reset all yer toggle preferences:");
        pirateTranslations.addProperty("help.clear.command", "/toggle clear");
        pirateTranslations.addProperty("help.clear.removes", "This will remove:");
        pirateTranslations.addProperty("help.clear.toggles", "- Yer current toggle settings");
        pirateTranslations.addProperty("help.clear.colors", "- All color preferences");
        pirateTranslations.addProperty("help.clear.language", "- Language selection");
        pirateTranslations.addProperty("help.clear.note", "Ye'll need to set everythin' up again after clearin', arr!");
        pirateTranslations.addProperty("help.language.title", "=== Language Options, aye! ===");
        pirateTranslations.addProperty("help.language.available", "Available languages:");
        pirateTranslations.addProperty("help.language.change", "Change yer display language:");
        pirateTranslations.addProperty("help.language.command", "/toggle language <language_code>");
        pirateTranslations.addProperty("help.language.example", "Example: /toggle language en_pt, arr!");
        createLangFile("en_pt.json", pirateTranslations);

        // Chinese Simplified (zh_cn)
        JsonObject zhCnTranslations = new JsonObject();
        zhCnTranslations.addProperty("time.years", "年");
        zhCnTranslations.addProperty("time.days", "天");
        zhCnTranslations.addProperty("time.hours", "小时");
        zhCnTranslations.addProperty("time.minutes", "分钟");
        zhCnTranslations.addProperty("time.seconds", "秒");
        zhCnTranslations.addProperty("command.disabled", "命令已禁用");
        zhCnTranslations.addProperty("command.player_only", "必须由玩家执行该命令");
        zhCnTranslations.addProperty("command.invalid_language", "无效的语言代码");
        zhCnTranslations.addProperty("command.invalid_toggle", "当前模式不允许此切换(%s)");
        zhCnTranslations.addProperty("command.invalid_hex_color", "无效的十六进制颜色格式。使用RRGGBB或#RRGGBB");
        zhCnTranslations.addProperty("command.error_saving", "保存您的%s首选项时出错。");
        zhCnTranslations.addProperty("command.reset_success", "您的切换首选项已重置。");
        zhCnTranslations.addProperty("command.reset_failed", "重置切换首选项失败。");
        zhCnTranslations.addProperty("command.reset_none", "您没有任何切换首选项可重置。");
        zhCnTranslations.addProperty("command.reloaded", "配置已重新加载。");
        zhCnTranslations.addProperty("command.no_content", "此子命令没有可用内容。");
        zhCnTranslations.addProperty("command.no_subcommands", "没有可用的子命令。请检查您的配置。");
        zhCnTranslations.addProperty("command.available_commands", "可用的子命令:");
        zhCnTranslations.addProperty("command.usage", "用法: %s");
        zhCnTranslations.addProperty("command.available_colors", "可用颜色: %s");
        zhCnTranslations.addProperty("command.available_languages", "可用语言: %s");
        zhCnTranslations.addProperty("command.hex_format", "十六进制格式可以是RRGGBB或#RRGGBB");
        zhCnTranslations.addProperty("timeplayed.set", "将游戏时间显示设置为%s");
        zhCnTranslations.addProperty("language.set", "将语言设置为%s");
        zhCnTranslations.addProperty("color.set", "将%s颜色设置为%s");
        zhCnTranslations.addProperty("color.reset", "将%s颜色重置为默认");
        zhCnTranslations.addProperty("toggle.set", "将切换%s设置为在%s显示%s统计信息");
        zhCnTranslations.addProperty("item.stone.name", "石头");
        zhCnTranslations.addProperty("item.dirt.name", "泥土");
        zhCnTranslations.addProperty("item.oak_log.name", "橡木原木");
        zhCnTranslations.addProperty("color.set.part1", "设置 ");
        zhCnTranslations.addProperty("color.set.part2", " 颜色为 ");
        zhCnTranslations.addProperty("color.reset.part1", "重置 ");
        zhCnTranslations.addProperty("color.reset.part2", " 颜色为默认");
        zhCnTranslations.addProperty("stat.mined", "挖掘");
        zhCnTranslations.addProperty("stat.crafted", "合成");
        zhCnTranslations.addProperty("stat.used", "使用");
        zhCnTranslations.addProperty("stat.broken", "破坏");
        zhCnTranslations.addProperty("stat.picked_up", "拾取");
        zhCnTranslations.addProperty("stat.dropped", "丢弃");
        zhCnTranslations.addProperty("stat.killed", "击杀");
        zhCnTranslations.addProperty("stat.killed_by", "被击杀");
        zhCnTranslations.addProperty("stat.custom", "自定义");
        zhCnTranslations.addProperty("stat.timeplayed", "游戏时间");
        zhCnTranslations.addProperty("help.general.title", "=== 切换命令帮助 ===");
        zhCnTranslations.addProperty("help.general.materials", "/toggle help materials - 显示可用的材料类别");
        zhCnTranslations.addProperty("help.general.color", "/toggle help color - 显示颜色自定义帮助");
        zhCnTranslations.addProperty("help.general.clear", "/toggle help clear - 显示如何重置您的设置");
        zhCnTranslations.addProperty("help.general.language", "/toggle help language - 显示语言选项");
        zhCnTranslations.addProperty("help.general.usage", "用法: /toggle <材料> <位置> [统计类别]");
        zhCnTranslations.addProperty("help.materials.title", "=== 材料类别 ===");
        zhCnTranslations.addProperty("help.materials.header", "可用的材料类型:");
        zhCnTranslations.addProperty("help.materials.mobs", "生物 - 所有活着的实体(僵尸, 爬行者等)");
        zhCnTranslations.addProperty("help.materials.blocks", "方块 - 所有可放置的方块(石头, 泥土等)");
        zhCnTranslations.addProperty("help.materials.items", "物品 - 所有物品(剑, 食物, 工具等)");
        zhCnTranslations.addProperty("help.materials.all", "全部 - 所有可用的");
        zhCnTranslations.addProperty("help.materials.usage", "用法: /toggle <材料ID> <actionbar|chat|title> [统计类别]");
        zhCnTranslations.addProperty("help.color.title", "=== 颜色自定义 ===");
        zhCnTranslations.addProperty("help.color.header", "更改不同文本元素的颜色:");
        zhCnTranslations.addProperty("help.color.text", "/toggle color text <颜色> - 主要文本颜色");
        zhCnTranslations.addProperty("help.color.category", "/toggle color category <颜色> - 统计类别颜色");
        zhCnTranslations.addProperty("help.color.material", "/toggle color material <颜色> - 材料名称颜色");
        zhCnTranslations.addProperty("help.color.number", "/toggle color number <颜色> - 数字颜色");
        zhCnTranslations.addProperty("help.color.time", "/toggle color time <颜色> - 游戏时间显示颜色");
        zhCnTranslations.addProperty("help.color.colors", "颜色可以是: 红色, 绿色, 蓝色, 黄色等");
        zhCnTranslations.addProperty("help.color.hex", "或使用十六进制值: '#FF0000' (红色), '#00FF00' (绿色)");
        zhCnTranslations.addProperty("help.color.none", "使用'NONE'重置为默认值");
        zhCnTranslations.addProperty("help.clear.title", "=== 重置设置 ===");
        zhCnTranslations.addProperty("help.clear.header", "重置所有您的切换首选项:");
        zhCnTranslations.addProperty("help.clear.command", "/toggle clear");
        zhCnTranslations.addProperty("help.clear.removes", "这将删除:");
        zhCnTranslations.addProperty("help.clear.toggles", "- 您当前的切换设置");
        zhCnTranslations.addProperty("help.clear.colors", "- 所有颜色首选项");
        zhCnTranslations.addProperty("help.clear.language", "- 语言选择");
        zhCnTranslations.addProperty("help.clear.note", "重置后您需要重新设置所有内容。");
        zhCnTranslations.addProperty("help.language.title", "=== 语言选项 ===");
        zhCnTranslations.addProperty("help.language.available", "可用的语言:");
        zhCnTranslations.addProperty("help.language.change", "更改您的显示语言:");
        zhCnTranslations.addProperty("help.language.command", "/toggle language <语言代码>");
        zhCnTranslations.addProperty("help.language.example", "示例: /toggle language zh_cn");
        createLangFile("zh_cn.json", zhCnTranslations);
    }

    private static void createLangFile(String filename, JsonObject translations) {
        File langFile = new File(LANG_DIR, filename);
        if (!langFile.exists()) {
            try (FileWriter writer = new FileWriter(langFile)) {
                writer.write(GSON.toJson(translations));
            } catch (IOException e) {
                System.err.println("Error creating language file: " + e.getMessage());
            }
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

                config.addProperty("ChatMsgFrequency", 10);
                config.addProperty("Toggle Command", true);
                config.addProperty("Default Statistik Category", "mined");
                config.addProperty("InvertToggleFile", false);

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