package de.tomate65.survivalmod.translation;

import com.google.gson.JsonObject;

public class EnPtTranslationGenerator extends TranslationGenerator {
    @Override
    public String getLanguageCode() {
        return "en_pt";
    }

    @Override
    public JsonObject generateTranslations() {
        JsonObject translations = new JsonObject();

        // Time units
        translations.addProperty("time.years", "y, arr!");
        translations.addProperty("time.days", "d, matey!");
        translations.addProperty("time.hours", "h, aye!");
        translations.addProperty("time.minutes", "m, arr!");
        translations.addProperty("time.seconds", "s, ye scallywag!");

        // Command feedback
        translations.addProperty("command.disabled", "Command disabled, arr!");
        translations.addProperty("command.player_only", "Command must be executed by a player, ye scurvy dog!");
        translations.addProperty("command.invalid_language", "Invalid language code, walk the plank!");
        translations.addProperty("command.invalid_toggle", "This toggle be not allowed in current mode (%s), arr!");
        translations.addProperty("command.invalid_hex_color", "Invalid hex color format. Use RRGGBB or #RRGGBB, ye bilge rat!");
        translations.addProperty("command.error_saving", "Error savin' yer %s preference, arr!");
        translations.addProperty("command.reset_success", "Yer toggle preferences have been reset, aye!");
        translations.addProperty("command.reset_failed", "Failed to reset yer toggle preferences, walk the plank!");
        translations.addProperty("command.reset_none", "Ye didn't have any toggle preferences to reset, ye scallywag!");
        translations.addProperty("command.reloaded", "Configuration reloaded, arr!");
        translations.addProperty("command.no_content", "No content available fer this subcommand, matey!");
        translations.addProperty("command.no_subcommands", "No subcommands available. Check yer configuration, ye bilge rat!");
        translations.addProperty("command.available_commands", "Available subcommands:");
        translations.addProperty("command.usage", "Usage: %s, arr!");
        translations.addProperty("command.available_colors", "Available colors: %s");
        translations.addProperty("command.available_languages", "Available languages: %s");
        translations.addProperty("command.hex_format", "Hex format can be either RRGGBB or #RRGGBB, aye!");
        translations.addProperty("command.reset_partial", "Cleared yer %s settings, arr!");

        // Command success messages
        translations.addProperty("timeplayed.set", "Set timeplayed to display in %s, arr!");
        translations.addProperty("language.set", "Set language to %s, ye scurvy dog!");
        translations.addProperty("color.set", "Set %s color to %s, matey!");
        translations.addProperty("color.reset", "Reset %s color to default, aye!");
        translations.addProperty("toggle.set", "Set toggle %s to display in %s with %s statistics, arr!");

        // Color command parts
        translations.addProperty("color.set.part1", "Set ");
        translations.addProperty("color.set.part2", " color to ");
        translations.addProperty("color.reset.part1", "Reset ");
        translations.addProperty("color.reset.part2", " color to default, arr!");

        // Item names
        translations.addProperty("item.stone.name", "Stone, arr!");
        translations.addProperty("item.dirt.name", "Dirt, matey!");
        translations.addProperty("item.oak_log.name", "Oak Log, aye!");

        // Stat categories
        translations.addProperty("stat.timeplayed", "Time Played, arr!");
        translations.addProperty("stat.mined", "Mined");
        translations.addProperty("stat.crafted", "Crafted");
        translations.addProperty("stat.used", "Used");
        translations.addProperty("stat.broken", "Broken");
        translations.addProperty("stat.picked_up", "Picked Up");
        translations.addProperty("stat.dropped", "Dropped");
        translations.addProperty("stat.killed", "Killed");
        translations.addProperty("stat.killed_by", "Killed By");
        translations.addProperty("stat.custom", "Custom");

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
        translations.addProperty("help.general.title", "=== Toggle Command Help, arr! ===");
        translations.addProperty("help.general.materials", "/toggle help materials - Show available material categories, matey!");
        translations.addProperty("help.general.color", "/toggle help color - Show color customization help, aye!");
        translations.addProperty("help.general.clear", "/toggle help clear - Show how to reset yer settings, arr!");
        translations.addProperty("help.general.language", "/toggle help language - Show language options, ye scallywag!");
        translations.addProperty("help.general.usage", "Usage: /toggle <material> <location> [stat_category], arr!");

        translations.addProperty("help.materials.title", "=== Material Categories, aye! ===");
        translations.addProperty("help.materials.header", "Available material types:");
        translations.addProperty("help.materials.mobs", "Mobs - All livin' creatures (zombies, creepers, etc.), arr!");
        translations.addProperty("help.materials.blocks", "Blocks - All placeable blocks (stone, dirt, etc.), aye!");
        translations.addProperty("help.materials.items", "Items - All items (swords, grub, tools, etc.), matey!");
        translations.addProperty("help.materials.all", "All - Everythin' available, arr!");
        translations.addProperty("help.materials.usage", "Usage: /toggle <material_id> <actionbar|chat|title> [stat_category], aye!");

        translations.addProperty("help.color.title", "=== Color Customization, arr! ===");
        translations.addProperty("help.color.header", "Change colors fer different text elements:");
        translations.addProperty("help.color.text", "/toggle color text <color> - Main text color, matey!");
        translations.addProperty("help.color.category", "/toggle color category <color> - Stat category color, aye!");
        translations.addProperty("help.color.material", "/toggle color material <color> - Material name color, arr!");
        translations.addProperty("help.color.number", "/toggle color number <color> - Number color, ye scallywag!");
        translations.addProperty("help.color.time", "/toggle color time <color> - Playtime display color, aye!");
        translations.addProperty("help.color.colors", "Colors can be: RED, GREEN, BLUE, YELLOW, etc., arr!");
        translations.addProperty("help.color.hex", "Or use hex values: '#FF0000' (red), '#00FF00' (green), matey!");
        translations.addProperty("help.color.none", "Use 'NONE' to reset to default, aye!");

        translations.addProperty("help.language.title", "=== Language Options, aye! ===");
        translations.addProperty("help.language.available", "Available languages:");
        translations.addProperty("help.language.change", "Change yer display language:");
        translations.addProperty("help.language.command", "/toggle language <language_code>");
        translations.addProperty("help.language.example", "Example: /toggle language en_pt, arr!");
    }

    private void addMagnetTranslations(JsonObject translations) {
        translations.addProperty("magnet.activated", "Magnet activated - Attractin' items in a %d block radius, arr!");
        translations.addProperty("magnet.deactivated", "Magnet deactivated");
        translations.addProperty("magnet.already_active", "Magnet be already active");
        translations.addProperty("magnet.already_inactive", "Magnet be already inactive");
    }

    private void addClearCommandTranslations(JsonObject translations) {
        translations.addProperty("help.clear.title", "=== Reset Settings, arr! ===");
        translations.addProperty("help.clear.header", "Reset all yer toggle preferences:");
        translations.addProperty("help.clear.command", "/toggle clear");
        translations.addProperty("help.clear.removes", "This will remove:");
        translations.addProperty("help.clear.toggles", "- Yer current toggle settings");
        translations.addProperty("help.clear.colors", "- All color preferences");
        translations.addProperty("help.clear.language", "- Language selection");
        translations.addProperty("help.clear.note", "Ye'll need to set everythin' up again after clearin', arr!");

        translations.addProperty("command.clear.color.text", "Cleared text color");
        translations.addProperty("command.clear.color.category", "Cleared category color");
        translations.addProperty("command.clear.color.material", "Cleared material color");
        translations.addProperty("command.clear.color.number", "Cleared number color");
        translations.addProperty("command.clear.color.time", "Cleared time color");
        translations.addProperty("command.clear.toggle", "Cleared toggle settings");
        translations.addProperty("command.clear.language", "Cleared language setting");
        translations.addProperty("help.clear.types", "Available types: color, toggle, language");
        translations.addProperty("help.clear.color_types", "Available color types: text, category, material, number, time");
    }

    private void addSetCommandTranslations(JsonObject translations) {
        translations.addProperty("command.set.success", "Set %s to %s");
        translations.addProperty("command.set.usage", "Usage: /toggle set <type> <value>");
        translations.addProperty("command.set.types", "Available types: object, location, category");
        translations.addProperty("command.set.invalid_stat_category", "Invalid stat category");
        translations.addProperty("command.set.invalid_location", "Invalid location");
        translations.addProperty("command.set.object", "Set object to %s");
        translations.addProperty("command.set.location", "Set location to %s");
        translations.addProperty("command.set.category", "Set stat category to %s");
    }
}