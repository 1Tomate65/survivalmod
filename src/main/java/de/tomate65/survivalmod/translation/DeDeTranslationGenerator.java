package de.tomate65.survivalmod.translation;

import com.google.gson.JsonObject;

public class DeDeTranslationGenerator extends TranslationGenerator {
    @Override
    public String getLanguageCode() {
        return "de_de";
    }

    @Override
    public JsonObject generateTranslations() {
        JsonObject translations = new JsonObject();

        // Time units
        translations.addProperty("time.years", "J");
        translations.addProperty("time.days", "T");
        translations.addProperty("time.hours", "Std");
        translations.addProperty("time.minutes", "Min");
        translations.addProperty("time.seconds", "Sek");

        // Command feedback
        translations.addProperty("command.disabled", "Befehl deaktiviert");
        translations.addProperty("command.player_only", "Befehl kann nur von einem Spieler ausgeführt werden");
        translations.addProperty("command.language_disabled", "Sprache %s ist in der Konfiguration deaktiviert");
        translations.addProperty("command.invalid_language", "Ungültiger Sprachcode");
        translations.addProperty("command.invalid_toggle", "Diese Einstellung ist im aktuellen Modus nicht erlaubt (%s)");
        translations.addProperty("command.invalid_hex_color", "Ungültiges Hex-Farbformat. Verwende RRGGBB oder #RRGGBB");
        translations.addProperty("command.error_saving", "Fehler beim Speichern deiner %s Einstellung.");
        translations.addProperty("command.reset_success", "Deine Einstellungen wurden zurückgesetzt.");
        translations.addProperty("command.reset_failed", "Zurücksetzen der Einstellungen fehlgeschlagen.");
        translations.addProperty("command.reset_none", "Es gab keine Einstellungen zum Zurücksetzen.");
        translations.addProperty("command.reloaded", "Konfiguration neu geladen.");
        translations.addProperty("command.no_content", "Kein Inhalt für diesen Unterbefehl verfügbar.");
        translations.addProperty("command.no_subcommands", "Keine Unterbefehle verfügbar. Überprüfe deine Konfiguration.");
        translations.addProperty("command.available_commands", "Verfügbare Unterbefehle:");
        translations.addProperty("command.usage", "Verwendung: %s");
        translations.addProperty("command.available_colors", "Verfügbare Farben: %s");
        translations.addProperty("command.available_languages", "Verfügbare Sprachen: %s");
        translations.addProperty("command.hex_format", "Hex-Format kann entweder RRGGBB oder #RRGGBB sein");
        translations.addProperty("command.reset_partial", "Deine %s Einstellungen wurden gelöscht");

        // Command success messages
        translations.addProperty("timeplayed.set", "Spielzeit wird nun in %s angezeigt");
        translations.addProperty("language.set", "Sprache auf %s geändert");
        translations.addProperty("color.set", "Farbe für %s auf %s gesetzt");
        translations.addProperty("color.reset", "Farbe für %s auf Standard zurückgesetzt");
        translations.addProperty("toggle.set", "Einstellung %s wird nun in %s mit %s Statistiken angezeigt");

        // Color command parts
        translations.addProperty("color.set.part1", "Farbe für ");
        translations.addProperty("color.set.part2", " auf ");
        translations.addProperty("color.reset.part1", "Farbe für ");
        translations.addProperty("color.reset.part2", " auf Standard zurückgesetzt");

        // Item names
        translations.addProperty("item.stone.name", "Stein");
        translations.addProperty("item.dirt.name", "Erde");
        translations.addProperty("item.oak_log.name", "Eichenstamm");

        // Stat categories
        translations.addProperty("stat.timeplayed", "Spielzeit");
        translations.addProperty("stat.mined", "Abgebaut");
        translations.addProperty("stat.crafted", "Hergestellt");
        translations.addProperty("stat.used", "Benutzt");
        translations.addProperty("stat.broken", "Zerbrochen");
        translations.addProperty("stat.picked_up", "Aufgehoben");
        translations.addProperty("stat.dropped", "Fallengelassen");
        translations.addProperty("stat.killed", "Getötet");
        translations.addProperty("stat.killed_by", "Getötet von");
        translations.addProperty("stat.custom", "Benutzerdefiniert");

        // Help system
        addHelpTranslations(translations);

        // Magnet system
        addMagnetTranslations(translations);

        // Clear command
        addClearCommandTranslations(translations);

        // Set command
        addSetCommandTranslations(translations);

        return translations;
    }

    private void addHelpTranslations(JsonObject translations) {
        translations.addProperty("help.general.title", "=== Toggle-Befehlshilfe ===");
        translations.addProperty("help.general.materials", "/toggle help materials - Zeigt verfügbare Materialkategorien");
        translations.addProperty("help.general.color", "/toggle help color - Zeigt Hilfe zur Farbanpassung");
        translations.addProperty("help.general.clear", "/toggle help clear - Zeigt wie Einstellungen zurückgesetzt werden");
        translations.addProperty("help.general.language", "/toggle help language - Zeigt Sprachoptionen");
        translations.addProperty("help.general.usage", "Verwendung: /toggle <Material> <Ort> [Statistik-Kategorie]");

        translations.addProperty("help.materials.title", "=== Materialkategorien ===");
        translations.addProperty("help.materials.header", "Verfügbare Materialtypen:");
        translations.addProperty("help.materials.mobs", "Kreaturen - Alle lebenden Wesen (Zombies, Creeper, etc.)");
        translations.addProperty("help.materials.blocks", "Blöcke - Alle platzierbaren Blöcke (Stein, Erde, etc.)");
        translations.addProperty("help.materials.items", "Gegenstände - Alle Items (Schwerter, Essen, Werkzeuge, etc.)");
        translations.addProperty("help.materials.all", "Alles - Alles Verfügbare");
        translations.addProperty("help.materials.usage", "Verwendung: /toggle <Material-ID> <Aktionsleiste|Chat|Titel> [Statistik-Kategorie]");

        translations.addProperty("help.color.title", "=== Farbanpassung ===");
        translations.addProperty("help.color.header", "Farben für verschiedene Textelemente ändern:");
        translations.addProperty("help.color.text", "/toggle color text <Farbe> - Haupttextfarbe");
        translations.addProperty("help.color.category", "/toggle color category <Farbe> - Statistik-Kategorienfarbe");
        translations.addProperty("help.color.material", "/toggle color material <Farbe> - Materialnamenfarbe");
        translations.addProperty("help.color.number", "/toggle color number <Farbe> - Zahlenfarbe");
        translations.addProperty("help.color.time", "/toggle color time <Farbe> - Spielzeitanzeigefarbe");
        translations.addProperty("help.color.colors", "Farben können sein: ROT, GRÜN, BLAU, GELB, etc.");
        translations.addProperty("help.color.hex", "Oder Hex-Werte verwenden: '#FF0000' (rot), '#00FF00' (grün)");
        translations.addProperty("help.color.none", "'NONE' verwenden, um auf Standard zurückzusetzen");

        translations.addProperty("help.language.title", "=== Sprachoptionen ===");
        translations.addProperty("help.language.available", "Verfügbare Sprachen:");
        translations.addProperty("help.language.change", "Ändere deine Anzeigesprache:");
        translations.addProperty("help.language.command", "/toggle language <Sprachcode>");
        translations.addProperty("help.language.example", "Beispiel: /toggle language de_de");

        translations.addProperty("survivalmod.info.running", "Du verwendest %s des Survival Mods");
        translations.addProperty("survivalmod.info.update_available", "Es gibt ein Update!");
        translations.addProperty("survivalmod.info.no_update", "Es gibt noch kein Update.");
        translations.addProperty("survivalmod.info.modrinth_link", "Klicke hier, um zur Modrinth-Seite zu gehen");
    }

    private void addMagnetTranslations(JsonObject translations) {
        translations.addProperty("magnet.activated", "Magnet aktiviert - Sammelt Items in einem Radius von %d Blöcken");
        translations.addProperty("magnet.deactivated", "Magnet deaktiviert");
        translations.addProperty("magnet.already_active", "Magnet ist bereits aktiv");
        translations.addProperty("magnet.already_inactive", "Magnet ist bereits inaktiv");
    }

    private void addClearCommandTranslations(JsonObject translations) {
        translations.addProperty("help.clear.title", "=== Einstellungen zurücksetzen ===");
        translations.addProperty("help.clear.header", "Setze deine Einstellungen zurück:");
        translations.addProperty("help.clear.command", "/toggle clear - Setze ALLE Einstellungen zurück");
        translations.addProperty("help.clear.partial_command", "/toggle clear <Typ> - Setze bestimmte Einstellungen zurück");
        translations.addProperty("help.clear.removes", "Komplettes Zurücksetzen entfernt:");
        translations.addProperty("help.clear.toggles", "- Deine aktuellen Einstellungen");
        translations.addProperty("help.clear.colors", "- Alle Farbpräferenzen");
        translations.addProperty("help.clear.language", "- Sprachauswahl");
        translations.addProperty("help.clear.partial_removes", "Teilweises Zurücksetzen:");
        translations.addProperty("help.clear.partial_colors", "- /toggle clear color - Alle Farben");
        translations.addProperty("help.clear.partial_color_types", "- /toggle clear color <Typ> - Bestimmte Farbe");
        translations.addProperty("help.clear.partial_toggle", "- /toggle clear toggle - Nur Einstellungen");
        translations.addProperty("help.clear.partial_language", "- /toggle clear language - Nur Sprache");
        translations.addProperty("help.clear.note", "Du musst alles nach dem Zurücksetzen neu einstellen.");

        translations.addProperty("command.clear.color.text", "Textfarbe zurückgesetzt");
        translations.addProperty("command.clear.color.category", "Kategorienfarbe zurückgesetzt");
        translations.addProperty("command.clear.color.material", "Materialfarbe zurückgesetzt");
        translations.addProperty("command.clear.color.number", "Zahlenfarbe zurückgesetzt");
        translations.addProperty("command.clear.color.time", "Zeitfarbe zurückgesetzt");
        translations.addProperty("command.clear.toggle", "Einstellungen zurückgesetzt");
        translations.addProperty("command.clear.language", "Spracheinstellung zurückgesetzt");
        translations.addProperty("help.clear.partial", "/toggle clear <Typ> - Bestimmte Einstellungen löschen");
        translations.addProperty("help.clear.types", "Verfügbare Typen: color, toggle, language");
        translations.addProperty("help.clear.color_types", "Verfügbare Farbtypen: text, category, material, number, time");
    }

    private void addSetCommandTranslations(JsonObject translations) {
        translations.addProperty("command.set.success", "%s auf %s gesetzt");
        translations.addProperty("command.set.usage", "Verwendung: /toggle set <Typ> <Wert>");
        translations.addProperty("command.set.types", "Verfügbare Typen: object, location, category");
        translations.addProperty("command.set.invalid_stat_category", "Ungültige Statistik-Kategorie");
        translations.addProperty("command.set.invalid_location", "Ungültiger Ort");
        translations.addProperty("command.set.object", "Objekt auf %s gesetzt");
        translations.addProperty("command.set.location", "Ort auf %s gesetzt");
        translations.addProperty("command.set.category", "Statistik-Kategorie auf %s gesetzt");
    }
}