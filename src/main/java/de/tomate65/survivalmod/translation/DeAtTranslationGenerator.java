package de.tomate65.survivalmod.translation;

import com.google.gson.JsonObject;

public class DeAtTranslationGenerator extends TranslationGenerator {
    @Override
    public String getLanguageCode() {
        return "de_at";
    }

    @Override
    public JsonObject generateTranslations() {
        JsonObject translations = new JsonObject();

        // Time units (Zeiteinheiten)
        translations.addProperty("time.years", "J"); // Jahr
        translations.addProperty("time.days", "T");  // Tag
        translations.addProperty("time.hours", "Std"); // Stund
        translations.addProperty("time.minutes", "Min"); // Minutn
        translations.addProperty("time.seconds", "Sek"); // Sekundn

        // Command feedback (Befehls-Feedback)
        translations.addProperty("command.disabled", "Befehl is deaktiviert");
        translations.addProperty("command.player_only", "Da Befehl deaf nur von an Spieler ausgfüahrt wern");
        translations.addProperty("command.language_disabled", "De Sproch %s is grod in da Konfig deaktiviert");
        translations.addProperty("command.invalid_language", "Ungültiga Sprochcode");
        translations.addProperty("command.invalid_toggle", "Des Umschaltn is im aktuelln Modus ned erlaubt (%s)");
        translations.addProperty("command.invalid_hex_color", "Ungültigs Hex-Farbformat. Vawend RRGGBB oda #RRGGBB");
        translations.addProperty("command.error_saving", "Föhler beim Speichern vo deina %s Eistellung.");
        translations.addProperty("command.reset_success", "Deine Umschalt-Eistellungen san zruckgsetzt worn.");
        translations.addProperty("command.reset_failed", "Umschalt-Eistellungen zrucksetzn is föhgschlogn.");
        translations.addProperty("command.reload_success", "De Eistellungen san erfolgreich neich glodn worn.");
        translations.addProperty("command.reload_failed", "Eistellungen neich lodn is föhgschlogn.");
        translations.addProperty("command.backup_created", "Manuelles Backup is erfolgreich erstellt worn.");
        translations.addProperty("command.backup_failed", "Backup is föhgschlogn: %s");

        // Help command (Hilfe-Befehl)
        translations.addProperty("help.general", "Verfügbare Befehle:");
        translations.addProperty("help.toggle", "/toggle - HUD-Anzeige umschaltn");
        translations.addProperty("help.toggle.info", "/toggle info - Aktuelle Umschalt-Eistellungen anzeign");
        translations.addProperty("help.toggle.set", "/toggle set <Typ> <Wert> - Bestimmte Eistellungen festlegn");
        translations.addProperty("help.toggle.clear", "/toggle clear <Typ> - Bestimmte Eistellungen löschen");
        translations.addProperty("help.toggle.color", "/toggle color <Typ> <Farbe> - Farb vom HUD-Text festlegn");
        translations.addProperty("help.toggle.language", "/toggle language <Sprochcode> - Sproch vom HUD festlegn");
        translations.addProperty("help.survival", "/survival - Survival-Info-Befehl");
        translations.addProperty("help.survival.subcommand", "/survival <Unterbefehl> - Bestimmte Survival-Info anzeign");
        translations.addProperty("help.recipetoggle", "/recipetoggle - Rezepte aktiviern/deaktiviern");
        translations.addProperty("help.recipetoggle.enable", "/recipetoggle enable <Rezept-ID> - Bestimmtes Rezept aktiviern");
        translations.addProperty("help.recipetoggle.disable", "/recipetoggle disable <Rezept-ID> - Bestimmtes Rezept deaktiviern");
        translations.addProperty("help.recipetoggle.list", "/recipetoggle list - Verfügbare Rezepte auflistn");
        translations.addProperty("help.recipetoggle.enable_all", "/recipetoggle enable_all - Olle benutzerdefinierten Rezepte aktiviern");
        translations.addProperty("help.recipetoggle.disable_all", "/recipetoggle disable_all - Olle benutzerdefinierten Rezepte deaktiviern");
        translations.addProperty("help.backup", "/survival backup - Deine Eistellungen manuell sichern");

        // Toggle command feedback (Umschalt-Befehl-Feedback)
        translations.addProperty("command.clear.color.text", "Textfarb zruckgsetzt");
        translations.addProperty("command.clear.color.category", "Kategorie-Farb zruckgsetzt");
        translations.addProperty("command.clear.color.material", "Material-Farb zruckgsetzt");
        translations.addProperty("command.clear.color.number", "Zoin-Farb zruckgsetzt");
        translations.addProperty("command.clear.color.time", "Zeit-Farb zruckgsetzt");
        translations.addProperty("command.clear.toggle", "Umschalt-Eistellungen zruckgsetzt");
        translations.addProperty("command.clear.language", "Sproch-Einstellung zruckgsetzt");
        translations.addProperty("help.clear.partial", "/toggle clear <Typ> - Bestimmte Eistellungen löschen");
        translations.addProperty("help.clear.types", "Verfügbare Typen: color, toggle, language");
        translations.addProperty("help.clear.color_types", "Verfügbare Farbtypen: text, category, material, number, time");

        translations.addProperty("command.set.success", "%s auf %s gsetzt");
        translations.addProperty("command.set.usage", "Vawendung: /toggle set <Typ> <Wert>");
        translations.addProperty("command.set.types", "Verfügbare Typen: object, location, category");
        translations.addProperty("command.set.invalid_stat_category", "Ungültige Statistik-Kategorie");
        translations.addProperty("command.set.invalid_location", "Ungültiger Ort");
        translations.addProperty("command.set.object", "Objekt auf %s gsetzt");
        translations.addProperty("command.set.location", "Ort auf %s gsetzt");
        translations.addProperty("command.set.category", "Statistik-Kategorie auf %s gsetzt");

        // Toggle info (Umschalt-Info)
        translations.addProperty("toggle.info.current_object", "Aktuelles Objekt: %s");
        translations.addProperty("toggle.info.current_location", "Aktueller Ort: %s");
        translations.addProperty("toggle.info.current_category", "Aktuelle Kategorie: %s");
        translations.addProperty("toggle.info.current_language", "Aktuelle Sproch: %s");
        translations.addProperty("toggle.info.colors", "Farben:");
        translations.addProperty("toggle.info.text_color", "Text: %s");
        translations.addProperty("toggle.info.category_color", "Kategorie: %s");
        translations.addProperty("toggle.info.material_color", "Material: %s");
        translations.addProperty("toggle.info.number_color", "Zoin: %s");
        translations.addProperty("toggle.info.time_color", "Zeit: %s");
        translations.addProperty("toggle.info.toggle_enabled", "Umschaltn is aktiv");
        translations.addProperty("toggle.info.toggle_disabled", "Umschaltn is inaktiv");

        // Toggle categories (Umschalt-Kategorien)
        translations.addProperty("toggle.category.mined", "Obbaut");
        translations.addProperty("toggle.category.broken", "Zabrochn");
        translations.addProperty("toggle.category.crafted", "Hergestellt");
        translations.addProperty("toggle.category.used", "Vawendt");
        translations.addProperty("toggle.category.picked_up", "Aufghobn");
        translations.addProperty("toggle.category.dropped", "Folln lossn");
        translations.addProperty("toggle.category.killed", "Tötet");
        translations.addProperty("toggle.category.killed_by", "Tötet von");
        translations.addProperty("toggle.category.custom", "Benutzerdefiniert");
        translations.addProperty("toggle.category.time", "Zeit");
        translations.addProperty("toggle.category.magnet", "Magnet");

        // Toggle locations (Umschalt-Orte)
        translations.addProperty("toggle.location.actionbar", "Actionbar");
        translations.addProperty("toggle.location.title", "Titel");
        translations.addProperty("toggle.location.subtitle", "Untertitel");
        translations.addProperty("toggle.location.chat", "Chat");

        // Recipe command feedback (Rezept-Befehl-Feedback)
        translations.addProperty("recipe.toggle.enabled", "Rezept %s is aktiviert. /reload ausführa, um de Änderungen anzuwenden.");
        translations.addProperty("recipe.toggle.disabled", "Rezept %s is deaktiviert. /reload ausführa, um de Änderungen anzuwenden.");
        translations.addProperty("recipe.file_not_found", "Rezeptdatei ned gfundn: %s");
        translations.addProperty("recipe.no_custom_recipes", "Koane benutzerdefinierten Rezepte gfundn.");
        translations.addProperty("recipe.available_recipes", "Verfügbare Rezepte:");
        translations.addProperty("recipe.status_enabled", "aktiviert");
        translations.addProperty("recipe.status_disabled", "deaktiviert");
        translations.addProperty("recipe.toggle_all_enabled", "Olle benutzerdefinierten Rezepte (%d) san aktiviert. /reload ausführa, um de Änderungen anzuwenden.");
        translations.addProperty("recipe.toggle_all_disabled", "Olle benutzerdefinierten Rezepte (%d) san deaktiviert. /reload ausführa, um de Änderungen anzuwenden.");

        // Survival Command (Survival-Befehl)
        translations.addProperty("survival.no_content", "Koana Inhalt für diesen Unterbefehl verfügbar.");
        translations.addProperty("survival.available_subcommands", "Verfügbare Unterbefehle:");
        translations.addProperty("survival.no_subcommands", "Koane Unterbefehle verfügbar. Überprüf deine Konfiguration.");
        translations.addProperty("survival.update_available", "Neiche Version is verfügbar! Aktuell: %s, Neichste: %s");
        translations.addProperty("survival.no_update", "Koane neichen Updates.");
        translations.addProperty("survival.update_check_failed", "Update-Überprüfung is föhgschlogn.");

        return translations;
    }
}
