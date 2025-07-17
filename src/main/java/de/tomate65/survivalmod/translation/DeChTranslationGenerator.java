package de.tomate65.survivalmod.translation;

import com.google.gson.JsonObject;

public class DeChTranslationGenerator extends TranslationGenerator {
    @Override
    public String getLanguageCode() {
        return "de_ch";
    }

    @Override
    public JsonObject generateTranslations() {
        JsonObject translations = new JsonObject();

        // Time units (Zyt-Einheite)
        translations.addProperty("time.years", "J"); // Jahr
        translations.addProperty("time.days", "T");  // Tag
        translations.addProperty("time.hours", "Std"); // Stund
        translations.addProperty("time.minutes", "Min"); // Minut
        translations.addProperty("time.seconds", "Sek"); // Sekund

        // Command feedback (Befähl-Rückmäldig)
        translations.addProperty("command.disabled", "Befähl isch deaktiviert");
        translations.addProperty("command.player_only", "De Befähl cha nur vo öim Spieler usgführt werde");
        translations.addProperty("command.language_disabled", "D'Sprach %s isch i de Konfiguratïon deaktiviert");
        translations.addProperty("command.invalid_language", "Ungültige Sprachcode");
        translations.addProperty("command.invalid_toggle", "Dä Schalter isch im aktuelle Modus nöd erlaubt (%s)");
        translations.addProperty("command.invalid_hex_color", "Ungültigs Hex-Farbformat. Nimm RRGGBB oder #RRGGBB");
        translations.addProperty("command.error_saving", "Fähler bim Spichere vo dine %s Istellige.");
        translations.addProperty("command.reset_success", "Dini Schalter-Istellige sind zrugggsetzt worde.");
        translations.addProperty("command.reset_failed", "Schalter-Istellige zrugggsetze isch fehlgschlage.");
        translations.addProperty("command.reload_success", "D'Istellige sind erfolgrich neu glade worde.");
        translations.addProperty("command.reload_failed", "D'Istellige neu lade isch fehlgschlage.");
        translations.addProperty("command.backup_created", "Manuell Backup isch erfolgrich gmacht worde.");
        translations.addProperty("command.backup_failed", "Backup isch fehlgschlage: %s");

        // Help command (Hilf-Befähl)
        translations.addProperty("help.general", "Verfüegbari Befähl:");
        translations.addProperty("help.toggle", "/toggle - HUD-Aazeig umschalte");
        translations.addProperty("help.toggle.info", "/toggle info - Aktuelli Schalter-Istellige aazeige");
        translations.addProperty("help.toggle.set", "/toggle set <Typ> <Wärt> - Bestimmti Istellige setze");
        translations.addProperty("help.toggle.clear", "/toggle clear <Typ> - Bestimmti Istellige lösche");
        translations.addProperty("help.toggle.color", "/toggle color <Typ> <Farb> - Farb vom HUD-Text setze");
        translations.addProperty("help.toggle.language", "/toggle language <Sprachcode> - Sprach vom HUD setze");
        translations.addProperty("help.survival", "/survival - Survival-Info-Befähl");
        translations.addProperty("help.survival.subcommand", "/survival <Unterbefähl> - Bestimmti Survival-Info aazeige");
        translations.addProperty("help.recipetoggle", "/recipetoggle - Rezäpt aktiviere/deaktiviere");
        translations.addProperty("help.recipetoggle.enable", "/recipetoggle enable <Rezäpt-ID> - Bestimmts Rezäpt aktiviere");
        translations.addProperty("help.recipetoggle.disable", "/recipetoggle disable <Rezäpt-ID> - Bestimmts Rezäpt deaktiviere");
        translations.addProperty("help.recipetoggle.list", "/recipetoggle list - Verfüegbari Rezäpt uflischte");
        translations.addProperty("help.recipetoggle.enable_all", "/recipetoggle enable_all - Alli custom Rezäpt aktiviere");
        translations.addProperty("help.recipetoggle.disable_all", "/recipetoggle disable_all - Alli custom Rezäpt deaktiviere");
        translations.addProperty("help.backup", "/survival backup - Dini Istellige manuell sichere");

        // Toggle command feedback (Schalter-Befähl-Rückmäldig)
        translations.addProperty("command.clear.color.text", "Textfarb zrugggsetzt");
        translations.addProperty("command.clear.color.category", "Kategorie-Farb zrugggsetzt");
        translations.addProperty("command.clear.color.material", "Material-Farb zrugggsetzt");
        translations.addProperty("command.clear.color.number", "Zahle-Farb zrugggsetzt");
        translations.addProperty("command.clear.color.time", "Zyt-Farb zrugggsetzt");
        translations.addProperty("command.clear.toggle", "Schalter-Istellige zrugggsetzt");
        translations.addProperty("command.clear.language", "Sprach-Istellige zrugggsetzt");
        translations.addProperty("help.clear.partial", "/toggle clear <Typ> - Bestimmti Istellige lösche");
        translations.addProperty("help.clear.types", "Verfüegbari Type: color, toggle, language");
        translations.addProperty("help.clear.color_types", "Verfüegbari Farbtype: text, category, material, number, time");

        translations.addProperty("command.set.success", "%s uf %s gsetzt");
        translations.addProperty("command.set.usage", "Aawändig: /toggle set <Typ> <Wärt>");
        translations.addProperty("command.set.types", "Verfüegbari Type: object, location, category");
        translations.addProperty("command.set.invalid_stat_category", "Ungültigi Statistik-Kategorie");
        translations.addProperty("command.set.invalid_location", "Ungültige Ort");
        translations.addProperty("command.set.object", "Objekt uf %s gsetzt");
        translations.addProperty("command.set.location", "Ort uf %s gsetzt");
        translations.addProperty("command.set.category", "Statistik-Kategorie uf %s gsetzt");

        // Toggle info (Schalter-Info)
        translations.addProperty("toggle.info.current_object", "Aktuells Objekt: %s");
        translations.addProperty("toggle.info.current_location", "Aktuelli Ort: %s");
        translations.addProperty("toggle.info.current_category", "Aktuelli Kategorie: %s");
        translations.addProperty("toggle.info.current_language", "Aktuelli Sprach: %s");
        translations.addProperty("toggle.info.colors", "Farbe:");
        translations.addProperty("toggle.info.text_color", "Text: %s");
        translations.addProperty("toggle.info.category_color", "Kategorie: %s");
        translations.addProperty("toggle.info.material_color", "Material: %s");
        translations.addProperty("toggle.info.number_color", "Zahle: %s");
        translations.addProperty("toggle.info.time_color", "Zyt: %s");
        translations.addProperty("toggle.info.toggle_enabled", "Schalter isch aktiv");
        translations.addProperty("toggle.info.toggle_disabled", "Schalter isch inaktiv");

        // Toggle categories (Schalter-Kategorie)
        translations.addProperty("toggle.category.mined", "Abboue");
        translations.addProperty("toggle.category.broken", "Kaputt gmacht");
        translations.addProperty("toggle.category.crafted", "Hergschtellt");
        translations.addProperty("toggle.category.used", "Bruucht");
        translations.addProperty("toggle.category.picked_up", "Ufgno");
        translations.addProperty("toggle.category.dropped", "Abgleit");
        translations.addProperty("toggle.category.killed", "Tötet");
        translations.addProperty("toggle.category.killed_by", "Tötet vo");
        translations.addProperty("toggle.category.custom", "Individuell");
        translations.addProperty("toggle.category.time", "Zyt");
        translations.addProperty("toggle.category.magnet", "Magnet");

        // Toggle locations (Schalter-Ort)
        translations.addProperty("toggle.location.actionbar", "Actionbar");
        translations.addProperty("toggle.location.title", "Titel");
        translations.addProperty("toggle.location.subtitle", "Untertitel");
        translations.addProperty("toggle.location.chat", "Chat");

        // Recipe command feedback (Rezäpt-Befähl-Rückmäldig)
        translations.addProperty("recipe.toggle.enabled", "Rezäpt %s isch aktiviert. /reload usfüehre zum d'Änderige aazwende.");
        translations.addProperty("recipe.toggle.disabled", "Rezäpt %s isch deaktiviert. /reload usfüehre zum d'Änderige aazwende.");
        translations.addProperty("recipe.file_not_found", "Rezäpt-Datei nöd gfunde: %s");
        translations.addProperty("recipe.no_custom_recipes", "Kei custom Rezäpt gfunde.");
        translations.addProperty("recipe.available_recipes", "Verfüegbari Rezäpt:");
        translations.addProperty("recipe.status_enabled", "aktiviert");
        translations.addProperty("recipe.status_disabled", "deaktiviert");
        translations.addProperty("recipe.toggle_all_enabled", "Alli custom Rezäpt (%d) sind aktiviert. /reload usfüehre zum d'Änderige aazwende.");
        translations.addProperty("recipe.toggle_all_disabled", "Alli custom Rezäpt (%d) sind deaktiviert. /reload usfüehre zum d'Änderige aazwende.");

        // Survival Command (Survival-Befähl)
        translations.addProperty("survival.no_content", "Kei Inhalt für dä Unterbefähl verfüegbar.");
        translations.addProperty("survival.available_subcommands", "Verfüegbari Unterbefähl:");
        translations.addProperty("survival.no_subcommands", "Kei Unterbefähl verfüegbar. Überprüef dini Konfiguratïon.");
        translations.addProperty("survival.update_available", "Neui Versïon isch verfüegbar! Aktuell: %s, Neuscht: %s");
        translations.addProperty("survival.no_update", "Kei neui Updates.");
        translations.addProperty("survival.update_check_failed", "Aktualisierigsprüefig isch fehlgschlage.");

        return translations;
    }
}
