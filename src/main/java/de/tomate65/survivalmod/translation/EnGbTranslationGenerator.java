package de.tomate65.survivalmod.translation;

import com.google.gson.JsonObject;

public class EnGbTranslationGenerator extends TranslationGenerator {
    @Override
    public String getLanguageCode() {
        return "en_gb";
    }

    @Override
    public JsonObject generateTranslations() {
        JsonObject translations = new JsonObject();

        // Time units
        translations.addProperty("time.years", "y");
        translations.addProperty("time.days", "d");
        translations.addProperty("time.hours", "h");
        translations.addProperty("time.minutes", "m");
        translations.addProperty("time.seconds", "s");

        // Command feedback
        translations.addProperty("command.disabled", "Command disabled");
        translations.addProperty("command.player_only", "Command must be executed by a player");
        translations.addProperty("command.language_disabled", "Language %s is currently disabled in the config");
        translations.addProperty("command.invalid_language", "Invalid language code");
        translations.addProperty("command.invalid_toggle", "This toggle is not allowed in current mode (%s)");
        translations.addProperty("command.invalid_hex_color", "Invalid hex colour format. Use RRGGBB or #RRGGBB");
        translations.addProperty("command.error_saving", "Error saving your %s preference.");
        translations.addProperty("command.reset_success", "Your toggle preferences have been reset.");
        translations.addProperty("command.reset_failed", "Failed to reset your toggle preferences.");
        translations.addProperty("command.reset_none", "You didn't have any toggle preferences to reset.");
        translations.addProperty("command.reloaded", "Configuration reloaded.");
        translations.addProperty("command.no_content", "No content available for this subcommand.");
        translations.addProperty("command.no_subcommands", "No subcommands available. Check your configuration.");
        translations.addProperty("command.available_commands", "Available subcommands:");
        translations.addProperty("command.usage", "Usage: %s");
        translations.addProperty("command.available_colors", "Available colours: %s");
        translations.addProperty("command.available_languages", "Available languages: %s");
        translations.addProperty("command.hex_format", "Hex format can be either RRGGBB or #RRGGBB");
        translations.addProperty("command.reset_partial", "Cleared your %s settings");

        // Command success messages
        translations.addProperty("timeplayed.set", "Set timeplayed to display in %s");
        translations.addProperty("language.set", "Set language to %s");
        translations.addProperty("color.set", "Set %s colour to %s");
        translations.addProperty("color.reset", "Reset %s colour to default");
        translations.addProperty("toggle.set", "Set toggle %s to display in %s with %s statistics");

        // Color command parts
        translations.addProperty("color.set.part1", "Set ");
        translations.addProperty("color.set.part2", " colour to ");
        translations.addProperty("color.reset.part1", "Reset ");
        translations.addProperty("color.reset.part2", " colour to default");

        // Item names
        translations.addProperty("item.stone.name", "Stone");
        translations.addProperty("item.dirt.name", "Dirt");
        translations.addProperty("item.oak_log.name", "Oak Log");

        // Stat categories
        translations.addProperty("stat.timeplayed", "Time Played");
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
        translations.addProperty("help.general.title", "=== Toggle Command Help ===");
        translations.addProperty("help.general.materials", "/toggle help materials - Show available material categories");
        translations.addProperty("help.general.color", "/toggle help colour - Show colour customisation help");
        translations.addProperty("help.general.clear", "/toggle help clear - Show how to reset your settings");
        translations.addProperty("help.general.language", "/toggle help language - Show language options");
        translations.addProperty("help.general.usage", "Usage: /toggle <material> <location> [stat_category]");

        translations.addProperty("help.materials.title", "=== Material Categories ===");
        translations.addProperty("help.materials.header", "Available material types:");
        translations.addProperty("help.materials.mobs", "Mobs - All living entities (zombies, creepers, etc.)");
        translations.addProperty("help.materials.blocks", "Blocks - All placeable blocks (stone, dirt, etc.)");
        translations.addProperty("help.materials.items", "Items - All items (swords, food, tools, etc.)");
        translations.addProperty("help.materials.all", "All - Everything available");
        translations.addProperty("help.materials.usage", "Usage: /toggle <material_id> <actionbar|chat|title> [stat_category]");

        translations.addProperty("help.color.title", "=== Colour Customisation ===");
        translations.addProperty("help.color.header", "Change colours for different text elements:");
        translations.addProperty("help.color.text", "/toggle colour text <colour> - Main text colour");
        translations.addProperty("help.color.category", "/toggle colour category <colour> - Stat category colour");
        translations.addProperty("help.color.material", "/toggle colour material <colour> - Material name colour");
        translations.addProperty("help.color.number", "/toggle colour number <colour> - Number colour");
        translations.addProperty("help.color.time", "/toggle colour time <colour> - Playtime display colour");
        translations.addProperty("help.color.colors", "Colours can be: RED, GREEN, BLUE, YELLOW, etc.");
        translations.addProperty("help.color.hex", "Or use hex values: '#FF0000' (red), '#00FF00' (green)");
        translations.addProperty("help.color.none", "Use 'NONE' to reset to default");

        translations.addProperty("help.language.title", "=== Language Options ===");
        translations.addProperty("help.language.available", "Available languages:");
        translations.addProperty("help.language.change", "Change your display language:");
        translations.addProperty("help.language.command", "/toggle language <language_code>");
        translations.addProperty("help.language.example", "Example: /toggle language en_gb");

        translations.addProperty("survivalmod.info.running", "You're running %s of the Survival Mod");
        translations.addProperty("survivalmod.info.update_available", "There has been an update!");
        translations.addProperty("survivalmod.info.no_update", "There has been no update yet.");
        translations.addProperty("survivalmod.info.modrinth_link", "Click here to go to the Modrinth page");
    }

    private void addMagnetTranslations(JsonObject translations) {
        translations.addProperty("magnet.activated", "Magnet activated - Attracting items in a %d block radius");
        translations.addProperty("magnet.deactivated", "Magnet deactivated");
        translations.addProperty("magnet.already_active", "Magnet is already active");
        translations.addProperty("magnet.already_inactive", "Magnet is already inactive");
    }

    private void addClearCommandTranslations(JsonObject translations) {
        translations.addProperty("help.clear.title", "=== Reset Settings ===");
        translations.addProperty("help.clear.header", "Reset all your toggle preferences:");
        translations.addProperty("help.clear.command", "/toggle clear");
        translations.addProperty("help.clear.removes", "This will remove:");
        translations.addProperty("help.clear.toggles", "- Your current toggle settings");
        translations.addProperty("help.clear.colors", "- All colour preferences");
        translations.addProperty("help.clear.language", "- Language selection");
        translations.addProperty("help.clear.note", "You'll need to set everything up again after clearing.");

        translations.addProperty("command.clear.color.text", "Cleared text colour");
        translations.addProperty("command.clear.color.category", "Cleared category colour");
        translations.addProperty("command.clear.color.material", "Cleared material colour");
        translations.addProperty("command.clear.color.number", "Cleared number colour");
        translations.addProperty("command.clear.color.time", "Cleared time colour");
        translations.addProperty("command.clear.toggle", "Cleared toggle settings");
        translations.addProperty("command.clear.language", "Cleared language setting");
        translations.addProperty("help.clear.types", "Available types: colour, toggle, language");
        translations.addProperty("help.clear.color_types", "Available colour types: text, category, material, number, time");
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