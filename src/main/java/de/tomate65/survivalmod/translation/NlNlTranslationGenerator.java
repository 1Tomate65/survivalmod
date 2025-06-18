package de.tomate65.survivalmod.translation;

import com.google.gson.JsonObject;

public class NlNlTranslationGenerator extends TranslationGenerator {
    @Override
    public String getLanguageCode() {
        return "nl_nl";
    }

    @Override
    public JsonObject generateTranslations() {
        JsonObject translations = new JsonObject();

        // Time units
        translations.addProperty("time.years", "jr");
        translations.addProperty("time.days", "d");
        translations.addProperty("time.hours", "u");
        translations.addProperty("time.minutes", "min");
        translations.addProperty("time.seconds", "sec");

        // Command feedback
        translations.addProperty("command.disabled", "Commando uitgeschakeld");
        translations.addProperty("command.player_only", "Commando moet door een speler worden uitgevoerd");
        translations.addProperty("command.invalid_language", "Ongeldige taalcode");
        translations.addProperty("command.language_disabled", "Taal %s is momenteel uitgeschakeld in de configuratie");
        translations.addProperty("command.invalid_toggle", "Deze toggle is niet toegestaan in de huidige modus (%s)");
        translations.addProperty("command.invalid_hex_color", "Ongeldig hex kleurformaat. Gebruik RRGGBB of #RRGGBB");
        translations.addProperty("command.error_saving", "Fout bij opslaan van je %s voorkeur");
        translations.addProperty("command.reset_success", "Je toggle voorkeuren zijn gereset");
        translations.addProperty("command.reset_failed", "Resetten van je toggle voorkeuren mislukt");
        translations.addProperty("command.reset_none", "Je had geen toggle voorkeuren om te resetten");
        translations.addProperty("command.reloaded", "Configuratie herladen");
        translations.addProperty("command.no_content", "Geen inhoud beschikbaar voor dit subcommando");
        translations.addProperty("command.no_subcommands", "Geen subcommando's beschikbaar. Controleer je configuratie");
        translations.addProperty("command.available_commands", "Beschikbare subcommando's:");
        translations.addProperty("command.usage", "Gebruik: %s");
        translations.addProperty("command.available_colors", "Beschikbare kleuren: %s");
        translations.addProperty("command.available_languages", "Beschikbare talen: %s");
        translations.addProperty("command.hex_format", "Hex formaat kan RRGGBB of #RRGGBB zijn");
        translations.addProperty("command.reset_partial", "Je %s instellingen zijn gewist");
        translations.addProperty("command.set_usage.title", "=== Gebruik van Set Commando ===");
        translations.addProperty("command.set_usage.object", "/toggle set object <object_id>");
        translations.addProperty("command.set_usage.location", "/toggle set location <actionbar|chat|title>");
        translations.addProperty("command.set_usage.category", "/toggle set category <statistieken_categorie>");

        translations.addProperty("message.action.mined", "gedolven");
        translations.addProperty("message.action.crafted", "gemaakt");
        translations.addProperty("message.action.used", "gebruikt");
        translations.addProperty("message.action.broken", "gebroken");
        translations.addProperty("message.action.picked_up", "opgepakt");
        translations.addProperty("message.action.dropped", "laten vallen");
        translations.addProperty("message.action.killed", "gedood");
        translations.addProperty("message.action.killed_by", "gedood door");
        translations.addProperty("message.action.custom", "een mijlpaal bereikt in");
        translations.addProperty("message.you", "Je hebt");
        translations.addProperty("message.have", "");
        translations.addProperty("message.plural", "s");
        translations.addProperty("message.exclamation", "!");

        // Command success messages
        translations.addProperty("timeplayed.set", "Speeltijd ingesteld om weer te geven in %s");
        translations.addProperty("language.set", "Taal ingesteld op %s");
        translations.addProperty("color.set", "%s kleur ingesteld op %s");
        translations.addProperty("color.reset", "%s kleur gereset naar standaard");
        translations.addProperty("toggle.set", "Toggle %s ingesteld om weer te geven in %s met %s statistieken");

        // Color command parts
        translations.addProperty("color.set.part1", "Ingesteld ");
        translations.addProperty("color.set.part2", " kleur op ");
        translations.addProperty("color.reset.part1", "Gereset ");
        translations.addProperty("color.reset.part2", " kleur naar standaard");

        // Item names
        translations.addProperty("item.stone.name", "Steen");
        translations.addProperty("item.dirt.name", "Aarde");
        translations.addProperty("item.oak_log.name", "Eikenstam");

        // Stat categories
        translations.addProperty("stat.timeplayed", "Speeltijd");
        translations.addProperty("stat.mined", "Gedolven");
        translations.addProperty("stat.crafted", "Gemaakt");
        translations.addProperty("stat.used", "Gebruikt");
        translations.addProperty("stat.broken", "Gebroken");
        translations.addProperty("stat.picked_up", "Opgepakt");
        translations.addProperty("stat.dropped", "Laten vallen");
        translations.addProperty("stat.killed", "Gedood");
        translations.addProperty("stat.killed_by", "Gedood door");
        translations.addProperty("stat.custom", "Aangepast");

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
        translations.addProperty("help.general.title", "=== Toggle Commando Hulp ===");
        translations.addProperty("help.general.materials", "/toggle help materials - Toon beschikbare materiaalcategorieën");
        translations.addProperty("help.general.color", "/toggle help color - Toon kleuraanpassingshulp");
        translations.addProperty("help.general.clear", "/toggle help clear - Toon hoe je je instellingen kunt resetten");
        translations.addProperty("help.general.language", "/toggle help language - Toon taalopties");
        translations.addProperty("help.general.usage", "Gebruik: /toggle <materiaal> <locatie> [statistiek_categorie]");

        translations.addProperty("help.materials.title", "=== Materiaalcategorieën ===");
        translations.addProperty("help.materials.header", "Beschikbare materiaaltypen:");
        translations.addProperty("help.materials.mobs", "Mobs - Alle levende entiteiten (zombies, creepers, etc.)");
        translations.addProperty("help.materials.blocks", "Blokken - Alle plaatsbare blokken (steen, aarde, etc.)");
        translations.addProperty("help.materials.items", "Items - Alle items (zwaarden, voedsel, gereedschap, etc.)");
        translations.addProperty("help.materials.all", "Alles - Alles wat beschikbaar is");
        translations.addProperty("help.materials.usage", "Gebruik: /toggle <materiaal_id> <actionbar|chat|title> [statistiek_categorie]");

        translations.addProperty("help.color.title", "=== Kleuraanpassing ===");
        translations.addProperty("help.color.header", "Verander kleuren voor verschillende tekstelementen:");
        translations.addProperty("help.color.text", "/toggle color text <kleur> - Hoofdtekstkleur");
        translations.addProperty("help.color.category", "/toggle color category <kleur> - Statistiekcategoriekleur");
        translations.addProperty("help.color.material", "/toggle color material <kleur> - Materiaalnaamkleur");
        translations.addProperty("help.color.number", "/toggle color number <kleur> - Getalkleur");
        translations.addProperty("help.color.time", "/toggle color time <kleur> - Speeltijdweergavekleur");
        translations.addProperty("help.color.colors", "Kleuren kunnen zijn: RED, GREEN, BLUE, YELLOW, etc.");
        translations.addProperty("help.color.hex", "Of gebruik hex-waarden: '#FF0000' (rood), '#00FF00' (groen)");
        translations.addProperty("help.color.none", "Gebruik 'NONE' om naar standaard te resetten");

        translations.addProperty("help.language.title", "=== Taalopties ===");
        translations.addProperty("help.language.available", "Beschikbare talen:");
        translations.addProperty("help.language.change", "Verander je weergavetaal:");
        translations.addProperty("help.language.command", "/toggle language <taalcode>");
        translations.addProperty("help.language.example", "Voorbeeld: /toggle language nl_nl");

        translations.addProperty("survivalmod.info.running", "Je gebruikt %s van de Survival Mod");
        translations.addProperty("survivalmod.info.update_available", "Er is een update beschikbaar!");
        translations.addProperty("survivalmod.info.no_update", "Er is nog geen update beschikbaar.");
        translations.addProperty("survivalmod.info.modrinth_link", "Klik hier om naar de Modrinth pagina te gaan");
    }

    private void addMagnetTranslations(JsonObject translations) {
        translations.addProperty("magnet.activated", "Magneet geactiveerd - Trekt items aan binnen %d blokken");
        translations.addProperty("magnet.deactivated", "Magneet gedeactiveerd");
        translations.addProperty("magnet.already_active", "Magneet is al actief");
        translations.addProperty("magnet.already_inactive", "Magneet is al inactief");
    }

    private void addClearCommandTranslations(JsonObject translations) {
        translations.addProperty("help.clear.title", "=== Instellingen Wissen ===");
        translations.addProperty("help.clear.header", "Reset je toggle voorkeuren:");
        translations.addProperty("help.clear.command", "/toggle clear - Reset ALLE instellingen");
        translations.addProperty("help.clear.partial_command", "/toggle clear <type> - Reset specifieke instellingen");
        translations.addProperty("help.clear.removes", "Volledig wissen verwijdert:");
        translations.addProperty("help.clear.toggles", "- Je huidige toggle instellingen");
        translations.addProperty("help.clear.colors", "- Alle kleurvoorkeuren");
        translations.addProperty("help.clear.language", "- Taalselectie");
        translations.addProperty("help.clear.partial_removes", "Gedeeltelijke wisopties:");
        translations.addProperty("help.clear.partial_colors", "- /toggle clear color - Alle kleuren");
        translations.addProperty("help.clear.partial_color_types", "- /toggle clear color <type> - Specifieke kleur");
        translations.addProperty("help.clear.partial_toggle", "- /toggle clear toggle - Alleen toggle instellingen");
        translations.addProperty("help.clear.partial_language", "- /toggle clear language - Alleen taal");
        translations.addProperty("help.clear.note", "Je moet alles opnieuw instellen na het wissen");

        translations.addProperty("command.clear.color.text", "Tekstkleur gewist");
        translations.addProperty("command.clear.color.category", "Categoriekleur gewist");
        translations.addProperty("command.clear.color.material", "Materiaalkleur gewist");
        translations.addProperty("command.clear.color.number", "Getalkleur gewist");
        translations.addProperty("command.clear.color.time", "Tijdkleur gewist");
        translations.addProperty("command.clear.toggle", "Toggle instellingen gewist");
        translations.addProperty("command.clear.language", "Taalinstelling gewist");
        translations.addProperty("help.clear.partial", "/toggle clear <type> - Wis specifieke instellingen");
        translations.addProperty("help.clear.types", "Beschikbare types: color, toggle, language");
        translations.addProperty("help.clear.color_types", "Beschikbare kleurtypes: text, category, material, number, time");
    }

    private void addSetCommandTranslations(JsonObject translations) {
        translations.addProperty("command.set.success", "%s ingesteld op %s");
        translations.addProperty("command.set.usage", "Gebruik: /toggle set <type> <waarde>");
        translations.addProperty("command.set.types", "Beschikbare types: object, location, category");
        translations.addProperty("command.set.invalid_stat_category", "Ongeldige statistiekcategorie");
        translations.addProperty("command.set.invalid_location", "Ongeldige locatie");
        translations.addProperty("command.set.object", "Object ingesteld op %s");
        translations.addProperty("command.set.location", "Locatie ingesteld op %s");
        translations.addProperty("command.set.category", "Statistiekcategorie ingesteld op %s");
    }
}