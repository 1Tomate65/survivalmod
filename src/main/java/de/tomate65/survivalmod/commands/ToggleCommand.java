package de.tomate65.survivalmod.commands;

import com.google.gson.*;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import de.tomate65.survivalmod.config.ConfigGenerator;
import de.tomate65.survivalmod.config.ConfigReader;
import de.tomate65.survivalmod.manager.MagnetManager;
import de.tomate65.survivalmod.togglerenderer.ToggleRenderer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.*;
import net.minecraft.registry.Registries;
import net.minecraft.util.Formatting;

import java.io.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static de.tomate65.survivalmod.togglerenderer.ToggleRenderer.getPlayerColor;
import static net.minecraft.server.command.CommandManager.*;

public class ToggleCommand {
    private static final File CONFIG_FILE = new File("config/survival/" + ConfigReader.getModVersion() + "/toggle.json");
    private static final File MAIN_CONFIG_FILE = new File("config/survival/" + ConfigReader.getModVersion() + "/conf.json");
    private static final File PLAYERDATA_DIR = new File("config/survival/" + ConfigReader.getModVersion() + "/playerdata");
    private static Set<String> availableToggles = new HashSet<>();
    private static boolean isCommandEnabled = true;
    private static String defaultStatCategory = "mined";

    private static final String[] MATERIAL_CATEGORIES = {"Mobs", "Blocks", "Items", "All"};
    public static final String[] STAT_CATEGORIES = {
            "mined", "crafted", "used", "broken", "picked_up", "dropped",
            "killed", "killed_by", "custom"
    };
    private static final String[] COLORS = {
            "BLACK", "DARK_BLUE", "DARK_GREEN", "DARK_AQUA", "DARK_RED",
            "DARK_PURPLE", "GOLD", "GRAY", "DARK_GRAY", "BLUE",
            "GREEN", "AQUA", "RED", "LIGHT_PURPLE", "YELLOW",
            "WHITE", "BROWN", "NONE"
    };

    private static final SuggestionProvider<ServerCommandSource> LANGUAGE_SUGGESTIONS = (context, builder) -> {
        ConfigReader.getAvailableLanguages().stream()
                .filter(ConfigReader::isLanguageEnabled)
                .forEach(builder::suggest);
        return CompletableFuture.completedFuture(builder.build());
    };

    public static void register() {
        loadConfig();
        loadMainConfig();
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            registerCommands(dispatcher);
        });
    }

    private static int clearPlayerConfig(CommandContext<ServerCommandSource> context, String type) {
        ServerPlayerEntity player = getPlayerOrError(context);
        if (player == null) return 0;

        UUID playerId = player.getUuid();
        File playerFile = new File(PLAYERDATA_DIR, playerId.toString() + ".json");

        if (!playerFile.exists()) {
            sendFeedback(context, "command.reset_none");
            return 1;
        }

        try {
            JsonObject playerData;
            try (FileReader reader = new FileReader(playerFile)) {
                playerData = JsonParser.parseReader(reader).getAsJsonObject();
            }

            // Keep essential data
            JsonObject newData = new JsonObject();
            newData.addProperty("uuid", playerId.toString());
            newData.addProperty("playername", player.getName().getString());

            // Preserve magnet state if not clearing everything
            if (!type.equals("all") && playerData.has("magnet_enabled")) {
                newData.addProperty("magnet_enabled", playerData.get("magnet_enabled").getAsBoolean());
            }

            // Handle specific clear types
            switch (type) {
                case "all":
                    // Already handled by keeping only uuid and name
                    break;
                case "colors":
                    // Keep everything except colors
                    copyPropertyIfExists(playerData, newData, "toggle");
                    copyPropertyIfExists(playerData, newData, "toggle_location");
                    copyPropertyIfExists(playerData, newData, "stat_category");
                    copyPropertyIfExists(playerData, newData, "language");
                    copyPropertyIfExists(playerData, newData, "magnet_enabled");
                    break;
                // Add other cases as needed
            }

            try (FileWriter writer = new FileWriter(playerFile)) {
                new GsonBuilder().setPrettyPrinting().create().toJson(newData, writer);
            }

            ToggleRenderer.clearCache(playerId);
            sendFeedback(context, "command.reset_success");
            return 1;
        } catch (Exception e) {
            sendError(context, player, "command.reset_failed");
            return 0;
        }
    }

    /*private static void sendFeedback(CommandContext<ServerCommandSource> context, ServerPlayerEntity player, String translationKey, String... args) {
        String lang = ToggleRenderer.getPlayerLanguage(player);
        String message = ConfigReader.translate(translationKey, lang);
        if (args.length > 0) {
            message = String.format(message, (Object[]) args);
        }
        context.getSource().sendFeedback(() ->
                        Text.literal(message).styled(style -> style.withColor(Formatting.GREEN)),
                false
        );
    }*/

    private static void sendFeedback(CommandContext<ServerCommandSource> context, String translationKey, String... args) {
        String message = ConfigReader.translate(translationKey, ConfigReader.getDefaultLanguage());
        if (args.length > 0) {
            message = String.format(message, (Object[]) args);
        }
        String finalMessage = message;
        context.getSource().sendFeedback(() ->
                        Text.literal(finalMessage).styled(style -> style.withColor(Formatting.GREEN)),
                false
        );
    }

    private static void sendError(CommandContext<ServerCommandSource> context, ServerPlayerEntity player, String translationKey, String... args) {
        String lang = ToggleRenderer.getPlayerLanguage(player);
        String message = ConfigReader.translate(translationKey, lang);
        if (args.length > 0) {
            message = String.format(message, (Object[]) args);
        }
        context.getSource().sendError(Text.literal(message));
    }


    private static void copyPropertyIfExists(JsonObject source, JsonObject target, String property) {
        if (source.has(property)) {
            target.add(property, source.get(property));
        }
    }

    private static int clearPlayerConfigWithType(CommandContext<ServerCommandSource> context, String type) {
        ServerPlayerEntity player = getPlayerOrError(context);
        if (player == null) return 0;

        UUID playerId = player.getUuid();
        File playerFile = new File(PLAYERDATA_DIR, playerId.toString() + ".json");

        if (!playerFile.exists()) {
            context.getSource().sendFeedback(() ->
                    Text.literal(ConfigReader.translate("command.reset_none",
                                    ToggleRenderer.getPlayerLanguage(player)))
                            .styled(style -> style.withColor(Formatting.GREEN)), false);
            return 1;
        }

        try {
            JsonObject playerData;
            try (FileReader reader = new FileReader(playerFile)) {
                playerData = JsonParser.parseReader(reader).getAsJsonObject();
            }

            switch (type) {
                case "color":
                    playerData.remove("text_color");
                    playerData.remove("category_color");
                    playerData.remove("material_color");
                    playerData.remove("number_color");
                    playerData.remove("time_color");
                    break;
                case "toggle":
                    playerData.remove("toggle");
                    break;
                case "toggle_location":
                    playerData.remove("toggle_location");
                    break;
                case "stat_category":
                    playerData.remove("stat_category");
                    break;
                case "language":
                    playerData.remove("language");
                    break;
                default:
                    // For specific color types
                    if (type.startsWith("color_")) {
                        String colorType = type.substring(6);
                        playerData.remove(colorType + "_color");
                    }
                    break;
            }

            try (FileWriter writer = new FileWriter(playerFile)) {
                new GsonBuilder().setPrettyPrinting().create().toJson(playerData, writer);
            }

            ToggleRenderer.clearCache(playerId);
            context.getSource().sendFeedback(() ->
                    Text.literal(String.format(ConfigReader.translate("command.reset_partial",
                                    ToggleRenderer.getPlayerLanguage(player)), type))
                            .styled(style -> style.withColor(Formatting.GREEN)), false);
            return 1;
        } catch (Exception e) {
            context.getSource().sendError(Text.literal(ConfigReader.translate("command.reset_failed",
                    ToggleRenderer.getPlayerLanguage(player))));
            return 0;
        }
    }

    private static void loadConfig() {
        if (!CONFIG_FILE.exists()) {
            ConfigGenerator.generateToggleConfig();
        }

        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            JsonObject config = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray toggles = config.getAsJsonArray("toggles");
            availableToggles.clear();
            for (int i = 0; i < toggles.size(); i++) {
                availableToggles.add(toggles.get(i).getAsString());
            }
        } catch (IOException | JsonParseException e) {
            System.err.println("Error reading toggle configuration: " + e.getMessage());
        }
    }

    private static void loadMainConfig() {
        if (!MAIN_CONFIG_FILE.exists()) return;

        try (FileReader reader = new FileReader(MAIN_CONFIG_FILE)) {
            JsonObject config = JsonParser.parseReader(reader).getAsJsonObject();
            if (config.has("Toggle Command")) {
                isCommandEnabled = config.get("Toggle Command").getAsBoolean();
            }
            if (config.has("Default Statistik Category")) {
                defaultStatCategory = config.get("Default Statistik Category").getAsString();
            }
        } catch (IOException | JsonParseException e) {
            System.err.println("Error reading main configuration: " + e.getMessage());
        }
    }

    public static boolean isToggleAllowed(String toggle) {
        if (ConfigReader.isInvertedToggleMode()) {
            return !availableToggles.contains(toggle);
        } else {
            return availableToggles.contains(toggle);
        }
    }

    public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("toggle")
                .executes(ToggleCommand::showToggleUsage)
                .then(literal("set")
                        .executes(context -> showSetUsage(context))
                        .then(literal("object")
                                .then(argument("item", StringArgumentType.string())
                                        .suggests((context, builder) -> {
                                            Set<String> allToggles = getAllAvailableToggles();
                                            allToggles.forEach(builder::suggest);
                                            return builder.buildFuture();
                                        })
                                        .executes(context -> setPlayerProperty(
                                                context,
                                                "toggle",
                                                StringArgumentType.getString(context, "item")
                                        ))
                                ))
                        .then(literal("location")
                                .then(argument("location", StringArgumentType.string())
                                      .suggests((context, builder) -> {
                                         builder.suggest("actionbar");
                                       builder.suggest("chat");
                                       builder.suggest("title");
                                       return builder.buildFuture();
                                       })
                                       .executes(context -> setPlayerProperty(
                                       context,
                                       "toggle_location",
                                       StringArgumentType.getString(context, "location")))
                                        ))
                        .then(literal("category")
                                .then(argument("category", StringArgumentType.string())
                                        .suggests((context, builder) -> {
                                         for (String category : STAT_CATEGORIES) {
                                         builder.suggest(category);
                                         }
                                                            return builder.buildFuture();
                                         })
                                         .executes(context -> setPlayerProperty(
                                         context,
                                         "stat_category",
                                         StringArgumentType.getString(context, "category"))))))
                .then(literal("clear")
                        .executes(ToggleCommand::clearPlayerConfig)
                        .then(literal("color")
                                .executes(context -> clearPlayerConfigWithType(context, "color"))
                                .then(literal("text")
                                        .executes(context -> clearPlayerConfigWithType(context, "color_text")))
                                .then(literal("category")
                                        .executes(context -> clearPlayerConfigWithType(context, "color_category")))
                                .then(literal("material")
                                        .executes(context -> clearPlayerConfigWithType(context, "color_material")))
                                .then(literal("number")
                                        .executes(context -> clearPlayerConfigWithType(context, "color_number")))
                                .then(literal("time")
                                        .executes(context -> clearPlayerConfigWithType(context, "color_time"))))
                        .then(literal("toggles")
                                .executes(context -> clearPlayerConfigWithType(context, "toggle")))
                        .then(literal("location")
                                 .executes(context -> clearPlayerConfigWithType(context, "toggle_location")))
                        .then(literal("stat_category")
                                .executes(context -> clearPlayerConfigWithType(context, "stat_category"))))
                .then(literal("help")
                        .executes(context -> showGeneralHelp(context))
                        .then(literal("materials")
                                .executes(context -> showMaterialsHelp(context)))
                        .then(literal("color")
                                .executes(context -> showColorHelp(context)))
                        .then(literal("clear")
                                .then(literal("toggle")
                                        .executes(context -> clearPlayerConfigWithType(context, "toggle")))
                                .then(literal("language")
                                        .executes(context -> clearPlayerConfigWithType(context, "language"))))
                        .then(literal("language")
                                .executes(context -> showLanguageHelp(context))))
                .then(literal("color")
                        .executes(context -> showColorUsage(context)))
                .then(literal("magnet")
                        .executes(context -> showMagnetUsage(context))
                        .then(argument("state", BoolArgumentType.bool())
                                .executes(context -> setMagnetState(
                                        context,
                                        BoolArgumentType.getBool(context, "state")
                                ))))
                        .then(literal("language")
                                .executes(context -> showLanguageUsage(context))
                                .then(argument("lang", StringArgumentType.word())
                                        .suggests(LANGUAGE_SUGGESTIONS)
                                        .executes(context -> setPlayerLanguage(context, StringArgumentType.getString(context, "lang"))))));

        Set<String> togglesToRegister = ConfigReader.isInvertedToggleMode() ?
                new HashSet<>(getAllAvailableToggles()) {{
                    removeAll(availableToggles);
                }} :
                availableToggles;

        togglesToRegister.forEach(toggle -> registerToggleCommand(dispatcher, toggle));
        registerColorCommands(dispatcher);
    }

    private static int setPlayerProperty(CommandContext<ServerCommandSource> context, String property, String value) {
        ServerPlayerEntity player = getPlayerOrError(context);
        if (player == null) return 0;

        UUID playerId = player.getUuid();
        File playerFile = new File(PLAYERDATA_DIR, playerId.toString() + ".json");

        try {
            PLAYERDATA_DIR.mkdirs();
            JsonObject playerData = new JsonObject();

            if (playerFile.exists()) {
                try (FileReader reader = new FileReader(playerFile)) {
                    playerData = JsonParser.parseReader(reader).getAsJsonObject();
                }
            }

            // Special handling for toggle property to ensure proper formatting
            if (property.equals("toggle")) {
                if (!isToggleAllowed(value)) {
                    String mode = ConfigReader.isInvertedToggleMode() ? "blocklist" : "allowlist";
                    context.getSource().sendError(Text.literal(String.format(ConfigReader.translate("command.invalid_toggle",
                            ToggleRenderer.getPlayerLanguage(player)), mode)));
                    return 0;
                }

                // Ensure we're using the correct format for the toggle (with namespace)
                if (!value.contains(":")) {
                    value = "minecraft:" + value;
                }
            }

            // Special handling for stat_category to validate
            if (property.equals("stat_category") && !Arrays.asList(STAT_CATEGORIES).contains(value)) {
                context.getSource().sendError(Text.literal(String.format(ConfigReader.translate("command.invalid_stat_category",
                        ToggleRenderer.getPlayerLanguage(player)))));
                return 0;
            }

            // Special handling for toggle_location to validate
            if (property.equals("toggle_location") &&
                    !Arrays.asList("actionbar", "chat", "title").contains(value)) {
                context.getSource().sendError(Text.literal(String.format(ConfigReader.translate("command.invalid_location",
                        ToggleRenderer.getPlayerLanguage(player)))));
                return 0;
            }

            playerData.addProperty(property, value);
            playerData.addProperty("uuid", playerId.toString());
            playerData.addProperty("playername", player.getName().getString());

            try (FileWriter writer = new FileWriter(playerFile)) {
                new GsonBuilder().setPrettyPrinting().create().toJson(playerData, writer);
            }

            ToggleRenderer.clearCache(playerId);

            String lang = ToggleRenderer.getPlayerLanguage(player);
            String message = String.format(ConfigReader.translate("command.set_success", lang),
                    property, value);

            context.getSource().sendFeedback(() ->
                            Text.literal(message).styled(style -> style.withColor(Formatting.GREEN)),
                    false);
            return 1;
        } catch (IOException | JsonParseException e) {
            System.err.println("Error setting player property: " + e.getMessage());
            context.getSource().sendError(Text.literal(String.format(ConfigReader.translate("command.error_saving",
                    ToggleRenderer.getPlayerLanguage(player)), property)));
            return 0;
        }
    }

    // Add this method to show set command usage
    private static int showSetUsage(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = getPlayerOrError(context);
        if (player == null) return 0;

        String lang = ToggleRenderer.getPlayerLanguage(player);

        sendFormattedMessage(context, player, "command.set_usage.title", Formatting.GOLD);
        sendFormattedMessage(context, player, "command.set_usage.object", Formatting.GREEN);
        sendFormattedMessage(context, player, "command.set_usage.location", Formatting.GREEN);
        sendFormattedMessage(context, player, "command.set_usage.category", Formatting.GREEN);

        return 1;
    }

    private static int clearPlayerConfig(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = getPlayerOrError(context);
        if (player == null) return 0;

        UUID playerId = player.getUuid();
        File playerFile = new File(PLAYERDATA_DIR, playerId.toString() + ".json");

        if (!playerFile.exists()) {
            sendFeedback(context, "command.reset_none");
            return 1;
        }

        try {
            // Create new config with only essential data
            JsonObject newData = new JsonObject();
            newData.addProperty("uuid", playerId.toString());
            newData.addProperty("playername", player.getName().getString());

            try (FileWriter writer = new FileWriter(playerFile)) {
                new GsonBuilder().setPrettyPrinting().create().toJson(newData, writer);
            }

            ToggleRenderer.clearCache(playerId);
            sendFeedback(context, "command.reset_success");
            return 1;
        } catch (Exception e) {
            sendError(context, player, "command.reset_failed");
            return 0;
        }
    }

    private static int showMagnetUsage(CommandContext<ServerCommandSource> context) {
        if (!MagnetManager.isMagnetAllowed()) {
            context.getSource().sendError(Text.literal(ConfigReader.translate("command.disabled",
                    ConfigReader.getDefaultLanguage())));
            return 0;
        }

        context.getSource().sendFeedback(() ->
                Text.literal(String.format(ConfigReader.translate("command.usage",
                                ConfigReader.getDefaultLanguage()), "/toggle magnet <true|false>"))
                        .styled(style -> style.withColor(Formatting.GRAY)), false);
        return 1;
    }

    private static int setMagnetState(CommandContext<ServerCommandSource> context, boolean state) {
        ServerPlayerEntity player = getPlayerOrError(context);
        if (player == null) return 0;

        if (!MagnetManager.isMagnetAllowed()) {
            context.getSource().sendError(Text.literal(ConfigReader.translate("command.disabled",
                    ToggleRenderer.getPlayerLanguage(player))));
            return 0;
        }

        boolean currentState = MagnetManager.getMagnetState(player.getUuid());
        String lang = ToggleRenderer.getPlayerLanguage(player);

        // Check if we're trying to change to the same state
        if (currentState == state) {
            String messageKey = state ? "magnet.already_active" : "magnet.already_inactive";
            TextColor textColor = ToggleRenderer.parseColor(ToggleRenderer.getPlayerColor(player, "text"));

            context.getSource().sendFeedback(() ->
                            Text.literal(ConfigReader.translate(messageKey, lang))
                                    .styled(style -> style.withColor(textColor)),
                    false);
            return 1;
        }

        MagnetManager.setMagnetState(player, state);

        if (state) {
            double scale = MagnetManager.getPlayerScale(player);
            int effectiveRadius = (int) Math.round(MagnetManager.getBaseRadius() * scale);
            TextColor textColor = ToggleRenderer.parseColor(ToggleRenderer.getPlayerColor(player, "text"));

            String message = String.format(
                    ConfigReader.translate("magnet.activated", lang),
                    effectiveRadius
            );

            context.getSource().sendFeedback(() ->
                            Text.literal(message)
                                    .styled(style -> style.withColor(textColor)),
                    false);
        } else {
            TextColor textColor = ToggleRenderer.parseColor(ToggleRenderer.getPlayerColor(player, "text"));

            context.getSource().sendFeedback(() ->
                            Text.literal(ConfigReader.translate("magnet.deactivated", lang))
                                    .styled(style -> style.withColor(textColor)),
                    false);
        }
        return 1;
    }

    private static int showToggleUsage(CommandContext<ServerCommandSource> context) {
        if (!isCommandEnabled) {
            context.getSource().sendError(Text.literal(ConfigReader.translate("command.disabled", ConfigReader.getDefaultLanguage())));
            return 0;
        }

        String mode = ConfigReader.isInvertedToggleMode() ?
                "BLACKLIST (all except " + availableToggles.size() + " blocked)" :
                "WHITELIST (" + availableToggles.size() + " allowed)";

        context.getSource().sendFeedback(() ->
                Text.literal("Toggle Command - Current Mode: " + mode + "\n" +
                                "Usage: /toggle <toggle> <location> [stat_category]\n" +
                                "Use /toggle help for more information")
                        .styled(style -> style.withColor(Formatting.GRAY)), false);
        return 1;
    }

    private static int showGeneralHelp(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = getPlayerOrError(context);
        if (player == null) return 0;

        String lang = ToggleRenderer.getPlayerLanguage(player);

        sendFormattedMessage(context, player, "help.general.title", Formatting.GOLD);
        sendFormattedMessage(context, player, "help.general.materials", Formatting.GREEN);
        sendFormattedMessage(context, player, "help.general.color", Formatting.GREEN);
        sendFormattedMessage(context, player, "help.general.clear", Formatting.GREEN);
        sendFormattedMessage(context, player, "help.general.language", Formatting.GREEN);
        sendFormattedMessage(context, player, "help.general.usage", Formatting.YELLOW);
        return 1;
    }

    private static int showMaterialsHelp(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = getPlayerOrError(context);
        if (player == null) return 0;

        sendFormattedMessage(context, player, "help.materials.title", Formatting.GOLD);
        sendFormattedMessage(context, player, "help.materials.header", Formatting.GRAY);
        sendFormattedMessage(context, player, "help.materials.mobs", Formatting.AQUA);
        sendFormattedMessage(context, player, "help.materials.blocks", Formatting.AQUA);
        sendFormattedMessage(context, player, "help.materials.items", Formatting.AQUA);
        sendFormattedMessage(context, player, "help.materials.all", Formatting.AQUA);
        sendFormattedMessage(context, player, "help.materials.usage", Formatting.YELLOW);
        return 1;
    }

    private static int showColorHelp(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = getPlayerOrError(context);
        if (player == null) return 0;

        sendFormattedMessage(context, player, "help.color.title", Formatting.GOLD);
        sendFormattedMessage(context, player, "help.color.header", Formatting.GRAY);
        sendFormattedMessage(context, player, "help.color.text", Formatting.GREEN);
        sendFormattedMessage(context, player, "help.color.category", Formatting.GREEN);
        sendFormattedMessage(context, player, "help.color.material", Formatting.GREEN);
        sendFormattedMessage(context, player, "help.color.number", Formatting.GREEN);
        sendFormattedMessage(context, player, "help.color.time", Formatting.GREEN);
        sendFormattedMessage(context, player, "help.color.colors", Formatting.GRAY);
        sendFormattedMessage(context, player, "help.color.hex", Formatting.GRAY);
        sendFormattedMessage(context, player, "help.color.none", Formatting.GRAY);

        context.getSource().sendFeedback(() ->
                Text.literal(ConfigReader.translate("command.hex_format", ToggleRenderer.getPlayerLanguage(player)))
                        .styled(style -> style.withColor(Formatting.GRAY)), false);
        return 1;
    }

    private static int showClearHelp(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = getPlayerOrError(context);
        if (player == null) return 0;

        sendFormattedMessage(context, player, "help.clear.title", Formatting.GOLD);
        sendFormattedMessage(context, player, "help.clear.header", Formatting.GRAY);
        sendFormattedMessage(context, player, "help.clear.command", Formatting.RED);
        sendFormattedMessage(context, player, "help.clear.removes", Formatting.GRAY);
        sendFormattedMessage(context, player, "help.clear.toggles", Formatting.GRAY);
        sendFormattedMessage(context, player, "help.clear.colors", Formatting.GRAY);
        sendFormattedMessage(context, player, "help.clear.language", Formatting.GRAY);
        sendFormattedMessage(context, player, "help.clear.note", Formatting.YELLOW);
        return 1;
    }

    private static int showLanguageHelp(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = getPlayerOrError(context);
        if (player == null) return 0;

        sendFormattedMessage(context, player, "help.language.title", Formatting.GOLD);
        sendFormattedMessage(context, player, "help.language.available", Formatting.GRAY);

        ConfigReader.getAvailableLanguages().stream()
                .filter(ConfigReader::isLanguageEnabled)
                .forEach(lang -> context.getSource().sendFeedback(() ->
                        Text.literal("- " + lang).styled(style -> style.withColor(Formatting.AQUA)), false));

        sendFormattedMessage(context, player, "help.language.change", Formatting.GRAY);
        sendFormattedMessage(context, player, "help.language.command", Formatting.GREEN);
        sendFormattedMessage(context, player, "help.language.example", Formatting.YELLOW);
        return 1;
    }

    private static ServerPlayerEntity getPlayerOrError(CommandContext<ServerCommandSource> context) {
        if (!(context.getSource().getEntity() instanceof ServerPlayerEntity player)) {
            context.getSource().sendError(Text.literal(ConfigReader.translate("command.player_only", ConfigReader.getDefaultLanguage())));
            return null;
        }
        return player;
    }

    private static void sendFormattedMessage(CommandContext<ServerCommandSource> context,
                                             ServerPlayerEntity player,
                                             String key,
                                             Formatting color) {
        String lang = ToggleRenderer.getPlayerLanguage(player);
        String message = ConfigReader.translate(key, lang);
        context.getSource().sendFeedback(() ->
                Text.literal(message).styled(style -> style.withColor(color)), false);
    }

    private static int showColorUsage(CommandContext<ServerCommandSource> context) {
        context.getSource().sendFeedback(() ->
                Text.literal(String.format(ConfigReader.translate("command.usage", ConfigReader.getDefaultLanguage()),
                                "/toggle color <type> <color>"))
                        .styled(style -> style.withColor(Formatting.GRAY)), false);
        return 1;
    }

    private static int showLanguageUsage(CommandContext<ServerCommandSource> context) {
        String enabledLanguages = ConfigReader.getAvailableLanguages().stream()
                .filter(ConfigReader::isLanguageEnabled)
                .collect(Collectors.joining(", "));

        context.getSource().sendFeedback(() ->
                Text.literal(String.format(ConfigReader.translate("command.available_languages",
                                ConfigReader.getDefaultLanguage()), enabledLanguages))
                        .styled(style -> style.withColor(Formatting.GRAY)), false);
        return 1;
    }

    private static void registerToggleCommand(CommandDispatcher<ServerCommandSource> dispatcher, String toggle) {
        if (toggle.equals("timeplayed")) {
            dispatcher.register(literal("toggle")
                    .then(literal(toggle)
                            .executes(context -> setTimePlayedToggle(context, "actionbar"))));
        } else {
            dispatcher.register(literal("toggle")
                    .then(literal(toggle)
                            .then(literal("actionbar")
                                    .executes(context -> setToggleWithStats(context, toggle, "actionbar", defaultStatCategory))
                                    .then(literal("mined").executes(context -> setToggleWithStats(context, toggle, "actionbar", "mined")))
                                    .then(literal("crafted").executes(context -> setToggleWithStats(context, toggle, "actionbar", "crafted")))
                                    .then(literal("used").executes(context -> setToggleWithStats(context, toggle, "actionbar", "used")))
                                    .then(literal("broken").executes(context -> setToggleWithStats(context, toggle, "actionbar", "broken")))
                                    .then(literal("picked_up").executes(context -> setToggleWithStats(context, toggle, "actionbar", "picked_up")))
                                    .then(literal("dropped").executes(context -> setToggleWithStats(context, toggle, "actionbar", "dropped")))
                                    .then(literal("killed").executes(context -> setToggleWithStats(context, toggle, "actionbar", "killed")))
                                    .then(literal("killed_by").executes(context -> setToggleWithStats(context, toggle, "actionbar", "killed_by")))
                            )
                            .then(literal("chat")
                                    .executes(context -> setToggleWithStats(context, toggle, "chat", defaultStatCategory))
                                    .then(literal("mined").executes(context -> setToggleWithStats(context, toggle, "chat", "mined")))
                                    .then(literal("crafted").executes(context -> setToggleWithStats(context, toggle, "chat", "crafted")))
                                    .then(literal("used").executes(context -> setToggleWithStats(context, toggle, "chat", "used")))
                                    .then(literal("broken").executes(context -> setToggleWithStats(context, toggle, "chat", "broken")))
                                    .then(literal("picked_up").executes(context -> setToggleWithStats(context, toggle, "chat", "picked_up")))
                                    .then(literal("dropped").executes(context -> setToggleWithStats(context, toggle, "chat", "dropped")))
                                    .then(literal("killed").executes(context -> setToggleWithStats(context, toggle, "chat", "killed")))
                                    .then(literal("killed_by").executes(context -> setToggleWithStats(context, toggle, "chat", "killed_by")))
                            )
                            .then(literal("title")
                                    .executes(context -> setToggleWithStats(context, toggle, "title", defaultStatCategory))
                                    .then(literal("mined").executes(context -> setToggleWithStats(context, toggle, "title", "mined")))
                                    .then(literal("crafted").executes(context -> setToggleWithStats(context, toggle, "title", "crafted")))
                                    .then(literal("used").executes(context -> setToggleWithStats(context, toggle, "title", "used")))
                                    .then(literal("broken").executes(context -> setToggleWithStats(context, toggle, "title", "broken")))
                                    .then(literal("picked_up").executes(context -> setToggleWithStats(context, toggle, "title", "picked_up")))
                                    .then(literal("dropped").executes(context -> setToggleWithStats(context, toggle, "title", "dropped")))
                                    .then(literal("killed").executes(context -> setToggleWithStats(context, toggle, "title", "killed")))
                                    .then(literal("killed_by").executes(context -> setToggleWithStats(context, toggle, "title", "killed_by")))
                            )
                    ));
        }
    }

    private static int setTimePlayedToggle(CommandContext<ServerCommandSource> context, String location) {
        ServerPlayerEntity player = getPlayerOrError(context);
        if (player == null) return 0;

        UUID playerId = player.getUuid();
        File playerFile = new File(PLAYERDATA_DIR, playerId.toString() + ".json");

        try {
            PLAYERDATA_DIR.mkdirs();
            JsonObject playerData = new JsonObject();

            if (playerFile.exists()) {
                try (FileReader reader = new FileReader(playerFile)) {
                    playerData = JsonParser.parseReader(reader).getAsJsonObject();
                }
            }

            playerData.addProperty("uuid", playerId.toString());
            playerData.addProperty("playername", player.getName().getString());
            playerData.addProperty("toggle", "timeplayed");
            playerData.addProperty("toggle_location", location);
            playerData.addProperty("stat_category", "custom");

            try (FileWriter writer = new FileWriter(playerFile)) {
                new GsonBuilder().setPrettyPrinting().create().toJson(playerData, writer);
            }

            ToggleRenderer.clearCache(playerId);
            context.getSource().sendFeedback(() ->
                    Text.literal(String.format(ConfigReader.translate("timeplayed.set", ToggleRenderer.getPlayerLanguage(player)), location))
                            .styled(style -> style.withColor(Formatting.GREEN)), false);
            return 1;
        } catch (IOException | JsonParseException e) {
            System.err.println("Error handling player toggle settings: " + e.getMessage());
            context.getSource().sendError(Text.literal(String.format(ConfigReader.translate("command.error_saving",
                    ToggleRenderer.getPlayerLanguage(player)), "toggle")));
            return 0;
        }
    }

    private static int setPlayerLanguage(CommandContext<ServerCommandSource> context, String language) {
        ServerPlayerEntity player = getPlayerOrError(context);
        if (player == null) return 0;

        if (!ConfigReader.getAvailableLanguages().contains(language)) {
            context.getSource().sendError(Text.literal(ConfigReader.translate("command.invalid_language",
                    ToggleRenderer.getPlayerLanguage(player))));
            return 0;
        }

        // Add check for enabled language
        if (!ConfigReader.isLanguageEnabled(language)) {
            context.getSource().sendError(Text.literal(ConfigReader.translate("command.language_disabled",
                    ToggleRenderer.getPlayerLanguage(player))));
            return 0;
        }

        UUID playerId = player.getUuid();
        File playerFile = new File(PLAYERDATA_DIR, playerId.toString() + ".json");

        try {
            PLAYERDATA_DIR.mkdirs();
            JsonObject playerData = new JsonObject();

            if (playerFile.exists()) {
                try (FileReader reader = new FileReader(playerFile)) {
                    playerData = JsonParser.parseReader(reader).getAsJsonObject();
                }
            }

            playerData.addProperty("language", language);
            playerData.addProperty("uuid", playerId.toString());
            playerData.addProperty("playername", player.getName().getString());

            try (FileWriter writer = new FileWriter(playerFile)) {
                new GsonBuilder().setPrettyPrinting().create().toJson(playerData, writer);
            }

            ToggleRenderer.clearCache(playerId);
            context.getSource().sendFeedback(() ->
                    Text.literal(String.format(ConfigReader.translate("language.set", language), language))
                            .styled(style -> style.withColor(Formatting.GREEN)), false);
            return 1;
        } catch (IOException | JsonParseException e) {
            System.err.println("Error handling player language settings: " + e.getMessage());
            context.getSource().sendError(Text.literal(String.format(ConfigReader.translate("command.error_saving",
                    ToggleRenderer.getPlayerLanguage(player)), "language")));
            return 0;
        }
    }

    private static void registerColorCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        for (String colorType : new String[]{"text", "category", "material", "number", "time"}) {
            dispatcher.register(
                    literal("toggle")
                            .then(literal("color")
                                    .then(literal(colorType)
                                            .executes(context -> {
                                                context.getSource().sendFeedback(() ->
                                                        Text.literal(String.format(ConfigReader.translate("command.usage",
                                                                                ConfigReader.getDefaultLanguage()),
                                                                        "/toggle color " + colorType + " <color> (name or hex #RRGGBB)\n" +
                                                                                String.format(ConfigReader.translate("command.available_colors",
                                                                                                ConfigReader.getDefaultLanguage()),
                                                                                        String.join(", ", COLORS))))
                                                                .styled(style -> style.withColor(Formatting.GRAY)), false);
                                                return 1;
                                            })
                                            .then(argument("color", StringArgumentType.string())
                                                    .suggests((context, builder) -> {
                                                        for (String color : COLORS) {
                                                            builder.suggest(color);
                                                        }
                                                        return builder.buildFuture();
                                                    })
                                                    .executes(context -> setColor(context, colorType, StringArgumentType.getString(context, "color")))
                                            ))));
        }
    }

    private static int setColor(CommandContext<ServerCommandSource> context, String colorType, String color) {
        ServerPlayerEntity player = getPlayerOrError(context);
        if (player == null) return 0;

        // Convert color name to code before saving
        String processedColor = color;
        if (color.equalsIgnoreCase("BROWN")) {
            processedColor = "#835432"; // Convert BROWN to its hex code
        } else if (color.matches("^[0-9a-fA-F]{6}$")) {
            processedColor = "#" + color; // Add # prefix if missing
        } else if (color.startsWith("#") && !color.matches("^#[0-9a-fA-F]{6}$")) {
            context.getSource().sendError(Text.literal(ConfigReader.translate("command.invalid_hex_color",
                    ToggleRenderer.getPlayerLanguage(player))));
            return 0;
        }

        UUID playerId = player.getUuid();
        File playerFile = new File(PLAYERDATA_DIR, playerId.toString() + ".json");

        try {
            PLAYERDATA_DIR.mkdirs();
            JsonObject playerData = new JsonObject();

            if (playerFile.exists()) {
                try (FileReader reader = new FileReader(playerFile)) {
                    playerData = JsonParser.parseReader(reader).getAsJsonObject();
                }
            }

            String propertyName = colorType + "_color";
            playerData.addProperty(propertyName, processedColor);

            try (FileWriter writer = new FileWriter(playerFile)) {
                new GsonBuilder().setPrettyPrinting().create().toJson(playerData, writer);
            }

            ToggleRenderer.clearCache(playerId);

            String lang = ToggleRenderer.getPlayerLanguage(player);
            TextColor textColor = parseColor(processedColor);
            String displayColor = processedColor.startsWith("#") ? processedColor.substring(1) : processedColor;

            // Create styled message with different colors for different parts
            MutableText message = Text.empty();

            if (processedColor.equalsIgnoreCase("NONE")) {
                // For reset messages
                message.append(Text.literal(ConfigReader.translate("color.reset.part1", lang))
                        .styled(style -> style.withColor(parseColor(getPlayerColor(player, "text")))));
                message.append(Text.literal(colorType))
                        .styled(style -> style.withColor(parseColor(getPlayerColor(player, "category"))));
                message.append(Text.literal(ConfigReader.translate("color.reset.part2", lang))
                        .styled(style -> style.withColor(parseColor(getPlayerColor(player, "text")))));
            } else {
                // For set messages
                message.append(Text.literal(ConfigReader.translate("color.set.part1", lang))
                        .styled(style -> style.withColor(parseColor(getPlayerColor(player, "text")))));
                message.append(Text.literal(colorType))
                        .styled(style -> style.withColor(parseColor(getPlayerColor(player, "category"))));
                message.append(Text.literal(ConfigReader.translate("color.set.part2", lang))
                        .styled(style -> style.withColor(parseColor(getPlayerColor(player, "text")))));
                message.append(Text.literal(displayColor.toUpperCase()))
                        .styled(style -> style.withColor(textColor));
            }

            context.getSource().sendFeedback(() -> message, false);
            return 1;
        } catch (IOException | JsonParseException e) {
            System.err.println("Error handling player color settings: " + e.getMessage());
            context.getSource().sendError(Text.literal(String.format(ConfigReader.translate("command.error_saving",
                    ToggleRenderer.getPlayerLanguage(player)), "color")));
            return 0;
        }
    }

    public static String getColorCode(String colorName) {
        if (colorName.startsWith("#") && colorName.length() == 7) {
            return colorName;
        }

        switch (colorName) {
            case "BLACK": return "0";
            case "DARK_BLUE": return "1";
            case "DARK_GREEN": return "2";
            case "DARK_AQUA": return "3";
            case "DARK_RED": return "4";
            case "DARK_PURPLE": return "5";
            case "GOLD": return "6";
            case "GRAY": return "7";
            case "DARK_GRAY": return "8";
            case "BLUE": return "9";
            case "GREEN": return "a";
            case "AQUA": return "b";
            case "RED": return "c";
            case "LIGHT_PURPLE": return "d";
            case "YELLOW": return "e";
            case "WHITE": return "f";
            case "BROWN": return "#835432";
            case "&": return "&";
            default: return "r";
        }
    }

    private static TextColor parseColor(String colorCode) {
        if (colorCode == null || colorCode.equalsIgnoreCase("NONE")) {
            return TextColor.fromFormatting(Formatting.WHITE);
        }

        if (colorCode.matches("^[0-9a-fA-F]{6}$")) {
            try {
                return TextColor.fromRgb(Integer.parseInt(colorCode, 16));
            } catch (NumberFormatException e) {
                return TextColor.fromFormatting(Formatting.WHITE);
            }
        } else if (colorCode.startsWith("#") && colorCode.matches("^#[0-9a-fA-F]{6}$")) {
            try {
                return TextColor.fromRgb(Integer.parseInt(colorCode.substring(1), 16));
            } catch (NumberFormatException e) {
                return TextColor.fromFormatting(Formatting.WHITE);
            }
        }

        if (colorCode.startsWith("§")) {
            Formatting formatting = Formatting.byCode(colorCode.charAt(1));
            if (formatting != null && formatting.isColor()) {
                return TextColor.fromFormatting(formatting);
            }
        } else if (colorCode.startsWith("&")) {
            Formatting formatting = Formatting.byCode(colorCode.charAt(1));
            if (formatting != null && formatting.isColor()) {
                return TextColor.fromFormatting(formatting);
            }
        }

        Formatting formatting = Formatting.byName(colorCode.toUpperCase());
        if (formatting != null && formatting.isColor()) {
            return TextColor.fromFormatting(formatting);
        }

        return TextColor.fromFormatting(Formatting.WHITE);
    }

    private static int setToggleWithStats(CommandContext<ServerCommandSource> context, String toggle, String location, String statCategory) {
        ServerPlayerEntity player = getPlayerOrError(context);
        if (player == null) return 0;

        if (!isToggleAllowed(toggle)) {
            String mode = ConfigReader.isInvertedToggleMode() ? "blocklist" : "allowlist";
            context.getSource().sendError(Text.literal(String.format(ConfigReader.translate("command.invalid_toggle",
                    ToggleRenderer.getPlayerLanguage(player)), mode)));
            return 0;
        }

        // Validate stat category
        if (!Arrays.asList(STAT_CATEGORIES).contains(statCategory)) {
            statCategory = defaultStatCategory;
        }

        UUID playerId = player.getUuid();
        File playerFile = new File(PLAYERDATA_DIR, playerId.toString() + ".json");

        try {
            PLAYERDATA_DIR.mkdirs();
            JsonObject playerData = new JsonObject();

            if (playerFile.exists()) {
                try (FileReader reader = new FileReader(playerFile)) {
                    playerData = JsonParser.parseReader(reader).getAsJsonObject();
                }
            }

            // Ensure we're using the correct format for the toggle (with namespace)
            String formattedToggle;
            if (!toggle.contains(":")) {
                formattedToggle = "minecraft:" + toggle;
            } else {
                formattedToggle = toggle;
            }

            playerData.addProperty("uuid", playerId.toString());
            playerData.addProperty("playername", player.getName().getString());
            playerData.addProperty("toggle", formattedToggle);
            playerData.addProperty("toggle_location", location);
            playerData.addProperty("stat_category", statCategory);

            try (FileWriter writer = new FileWriter(playerFile)) {
                new GsonBuilder().setPrettyPrinting().create().toJson(playerData, writer);
            }

            ToggleRenderer.clearCache(playerId);
            String finalStatCategory = statCategory;
            context.getSource().sendFeedback(() ->
                    Text.literal(String.format(ConfigReader.translate("toggle.set",
                                    ToggleRenderer.getPlayerLanguage(player)), formattedToggle, location, finalStatCategory))
                            .styled(style -> style.withColor(Formatting.GREEN)), false);
            return 1;
        } catch (IOException | JsonParseException e) {
            System.err.println("Error handling player toggle settings: " + e.getMessage());
            context.getSource().sendError(Text.literal(String.format(ConfigReader.translate("command.error_saving",
                    ToggleRenderer.getPlayerLanguage(player)), "toggle")));
            return 0;
        }
    }


    private static Set<String> getAllItemIds() {
        Set<String> ids = new HashSet<>();
        Registries.ITEM.getIds().forEach(id -> ids.add(id.toString()));
        return ids;
    }

    private static Set<String> getAllBlockIds() {
        Set<String> ids = new HashSet<>();
        Registries.BLOCK.getIds().forEach(id -> ids.add(id.toString()));
        return ids;
    }

    private static Set<String> getAllEntityIds() {
        Set<String> ids = new HashSet<>();
        Registries.ENTITY_TYPE.getIds().forEach(id -> ids.add(id.toString()));
        return ids;
    }

    public static Set<String> getAllAvailableToggles() {
        Set<String> allToggles = new HashSet<>();
        allToggles.addAll(getAllItemIds());
        allToggles.addAll(getAllBlockIds());
        allToggles.addAll(getAllEntityIds());
        allToggles.add("timeplayed");
        return allToggles;
    }
}