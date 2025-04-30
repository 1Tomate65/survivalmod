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

        // Tijdseenheden
        translations.addProperty("time.years", "jr");
        translations.addProperty("time.days", "d");
        translations.addProperty("time.hours", "u");
        translations.addProperty("time.minutes", "min");
        translations.addProperty("time.seconds", "s");

        // Itemnamen
        translations.addProperty("item.stone.name", "Steen");
        translations.addProperty("item.dirt.name", "Aarde");
        translations.addProperty("item.oak_log.name", "Eikenstam");

        // Statistieken categorieën
        translations.addProperty("stat.timeplayed", "Speeltijd");
        translations.addProperty("stat.mined", "Gegraven");
        translations.addProperty("stat.crafted", "Gemaakt");
        translations.addProperty("stat.used", "Gebruikt");
        translations.addProperty("stat.broken", "Gebroken");
        translations.addProperty("stat.picked_up", "Opgepakt");
        translations.addProperty("stat.dropped", "Laten vallen");
        translations.addProperty("stat.killed", "Gedood");
        translations.addProperty("stat.killed_by", "Gedood door");
        translations.addProperty("stat.custom", "Aangepast");

        // Hulp systeem
        addHelpTranslations(translations);

        // Magneet systeem
        addMagnetTranslations(translations);

        // Clear commando
        addClearCommandTranslations(translations);

        // Set commando
        addSetCommandTranslations(translations);

        return translations;
    }

    private void addHelpTranslations(JsonObject translations) {
        translations.addProperty("help.general.title", "=== Toggle Commando Hulp ===");
        translations.addProperty("help.general.materials", "/toggle help materials - Toon beschikbare materiaalcategorieën");
        translations.addProperty("help.general.color", "/toggle help color - Toon hulp voor kleuraanpassing");
        translations.addProperty("help.general.clear", "/toggle help clear - Toon hoe je instellingen reset");
        translations.addProperty("help.general.language", "/toggle help language - Toon taalopties");
        translations.addProperty("help.general.usage", "Gebruik: /toggle <materiaal> <locatie> [statistiek_categorie]");

        translations.addProperty("help.materials.title", "=== Materiaalcategorieën ===");
        translations.addProperty("help.materials.header", "Beschikbare materiaaltypes:");
        translations.addProperty("help.materials.mobs", "Mobs - Alle levende wezens (zombies, creepers, etc.)");
        translations.addProperty("help.materials.blocks", "Blokken - Alle plaatsbare blokken (steen, aarde, etc.)");
        translations.addProperty("help.materials.items", "Items - Alle voorwerpen (zwaarden, eten, gereedschap, etc.)");
        translations.addProperty("help.materials.all", "Alles - Alles wat beschikbaar is");
        translations.addProperty("help.materials.usage", "Gebruik: /toggle <materiaal_id> <actiebalk|chat|titel> [statistiek_categorie]");

        translations.addProperty("help.color.title", "=== Kleuraanpassing ===");
        translations.addProperty("help.color.header", "Verander kleuren voor verschillende tekstelementen:");
        translations.addProperty("help.color.text", "/toggle color text <kleur> - Hoofdtekstkleur");
        translations.addProperty("help.color.category", "/toggle color category <kleur> - Statistiekcategorie kleur");
        translations.addProperty("help.color.material", "/toggle color material <kleur> - Materiaalnaam kleur");
        translations.addProperty("help.color.number", "/toggle color number <kleur> - Getalkleur");
        translations.addProperty("help.color.time", "/toggle color time <kleur> - Speeltijd weergave kleur");
        translations.addProperty("help.color.colors", "Kleuren kunnen zijn: ROOD, GROEN, BLAUW, GEEL, etc.");
        translations.addProperty("help.color.hex", "Of gebruik hex waarden: '#FF0000' (rood), '#00FF00' (groen)");
        translations.addProperty("help.color.none", "Gebruik 'NONE' om terug te zetten naar standaard");

        translations.addProperty("help.language.title", "=== Taalopties ===");
        translations.addProperty("help.language.available", "Beschikbare talen:");
        translations.addProperty("help.language.change", "Verander je weergavetaal:");
        translations.addProperty("help.language.command", "/toggle language <taalcode>");
        translations.addProperty("help.language.example", "Voorbeeld: /toggle language nl_nl");
    }

    private void addMagnetTranslations(JsonObject translations) {
        translations.addProperty("magnet.activated", "Magneet geactiveerd - Trekt items aan binnen %d blokken");
        translations.addProperty("magnet.deactivated", "Magneet gedeactiveerd");
        translations.addProperty("magnet.already_active", "Magneet is al actief");
        translations.addProperty("magnet.already_inactive", "Magneet is al inactief");
    }

    private void addClearCommandTranslations(JsonObject translations) {
        translations.addProperty("help.clear.title", "=== Instellingen Resetten ===");
        translations.addProperty("help.clear.header", "Reset je voorkeuren:");
        translations.addProperty("help.clear.command", "/toggle clear - Reset ALLE instellingen");
        translations.addProperty("help.clear.partial_command", "/toggle clear <type> - Reset specifieke instellingen");
        translations.addProperty("help.clear.removes", "Volledige reset verwijdert:");
        translations.addProperty("help.clear.toggles", "- Je huidige instellingen");
        translations.addProperty("help.clear.colors", "- Alle kleurvoorkeuren");
        translations.addProperty("help.clear.language", "- Taalselectie");
        translations.addProperty("help.clear.partial_removes", "Gedeeltelijke reset opties:");
        translations.addProperty("help.clear.partial_colors", "- /toggle clear color - Alle kleuren");
        translations.addProperty("help.clear.partial_color_types", "- /toggle clear color <type> - Specifieke kleur");
        translations.addProperty("help.clear.partial_toggle", "- /toggle clear toggle - Alleen instellingen");
        translations.addProperty("help.clear.partial_language", "- /toggle clear language - Alleen taal");
        translations.addProperty("help.clear.note", "Je moet alles opnieuw instellen na het resetten.");

        translations.addProperty("command.clear.color.text", "Tekstkleur gereset");
        translations.addProperty("command.clear.color.category", "Categorie kleur gereset");
        translations.addProperty("command.clear.color.material", "Materiaalkleur gereset");
        translations.addProperty("command.clear.color.number", "Getalkleur gereset");
        translations.addProperty("command.clear.color.time", "Tijdkleur gereset");
        translations.addProperty("command.clear.toggle", "Instellingen gereset");
        translations.addProperty("command.clear.language", "Taalinstelling gereset");
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