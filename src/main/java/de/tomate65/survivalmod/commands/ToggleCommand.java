package de.tomate65.survivalmod.commands;

import com.google.gson.*;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import de.tomate65.survivalmod.config.ConfigGenerator;
import de.tomate65.survivalmod.config.ConfigReader;
import de.tomate65.survivalmod.togglerenderer.ToggleRenderer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static net.minecraft.server.command.CommandManager.*;

public class ToggleCommand {
    private static final File CONFIG_FILE = new File("config/survival/toggle.json");
    private static final File MAIN_CONFIG_FILE = new File("config/survival/conf.json");
    private static final File PLAYERDATA_DIR = new File("config/survival/playerdata");
    private static Set<String> availableToggles = new HashSet<>();
    private static boolean isCommandEnabled = true;
    private static String defaultStatCategory = "mined";

    private static final String[] STAT_CATEGORIES = {
            "mined", "crafted", "used", "broken", "picked_up", "dropped",
            "killed", "killed_by", "custom"
    };

    private static final String[] COLORS = {
            "BLACK", "DARK_BLUE", "DARK_GREEN", "DARK_AQUA", "DARK_RED",
            "DARK_PURPLE", "GOLD", "GRAY", "DARK_GRAY", "BLUE",
            "GREEN", "AQUA", "RED", "LIGHT_PURPLE", "YELLOW",
            "WHITE", "NONE"
    };

    static void loadConfig() {
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
            System.out.println("Loaded toggles: " + availableToggles);
        } catch (IOException | JsonParseException e) {
            System.err.println("Error reading toggle configuration: " + e.getMessage());
        }
    }

    public static void register() {
        loadConfig();
        loadMainConfig();

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated, dedicated2) -> {
            registerCommands(dispatcher);
        });
    }

    private static void loadMainConfig() {
        if (!MAIN_CONFIG_FILE.exists()) {
            return;
        }

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

    private static final SuggestionProvider<ServerCommandSource> LANGUAGE_SUGGESTIONS =
            (context, builder) -> {
                for (String lang : ConfigReader.getAvailableLanguages()) {
                    builder.suggest(lang);
                }
                return CompletableFuture.completedFuture(builder.build());
            };

    private static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                literal("toggle")
                        .executes(context -> {
                            if (!isCommandEnabled) {
                                context.getSource().sendError(Text.literal("This Command is Disabled, please contact an admin if you think this is a mistake"));
                                return 0;
                            }
                            context.getSource().sendFeedback(() -> Text.literal("Usage: /toggle <toggle> <location> [stat_category]"), false);
                            return 1;
                        })
                        .then(literal("clear").executes(context -> {
                            if (!isCommandEnabled) {
                                context.getSource().sendError(Text.literal("This Command is Disabled, please contact an admin if you think this is a mistake"));
                                return 0;
                            }
                            return clearPlayerConfig(context);
                        }))
                        .then(literal("color")
                                .executes(context -> {
                                    context.getSource().sendFeedback(() -> Text.literal("Usage: /toggle color <text|category|material|number> <color>"), false);
                                    return 1;
                                })
                        )
                        .then(literal("language")
                                .executes(context -> {
                                    context.getSource().sendFeedback(() -> Text.literal("Available languages: " + String.join(", ", ConfigReader.getAvailableLanguages())), false);
                                    return 1;
                                })
                                .then(argument("lang", StringArgumentType.word())
                                        .suggests(LANGUAGE_SUGGESTIONS)
                                        .executes(context -> {
                                            String lang = StringArgumentType.getString(context, "lang");
                                            return setPlayerLanguage(context, lang);
                                        })
                                )));

        registerColorCommands(dispatcher);

        for (String toggle : availableToggles) {
            dispatcher.register(
                    literal("toggle")
                            .then(literal(toggle)
                                    /*.then(literal("scoreboard")
                                            .executes(context -> setToggleWithStats(context, toggle, "scoreboard", defaultStatCategory))
                                            .then(literal("mined").executes(context -> setToggleWithStats(context, toggle, "scoreboard", "mined")))
                                            .then(literal("crafted").executes(context -> setToggleWithStats(context, toggle, "scoreboard", "crafted")))
                                            .then(literal("used").executes(context -> setToggleWithStats(context, toggle, "scoreboard", "used")))
                                            .then(literal("broken").executes(context -> setToggleWithStats(context, toggle, "scoreboard", "broken")))
                                            .then(literal("picked_up").executes(context -> setToggleWithStats(context, toggle, "scoreboard", "picked_up")))
                                            .then(literal("dropped").executes(context -> setToggleWithStats(context, toggle, "scoreboard", "dropped")))
                                            .then(literal("killed").executes(context -> setToggleWithStats(context, toggle, "scoreboard", "killed")))
                                            .then(literal("killed_by").executes(context -> setToggleWithStats(context, toggle, "scoreboard", "killed_by")))
                                            .then(literal("custom").executes(context -> setToggleWithStats(context, toggle, "scoreboard", "custom")))
                                    )*/ //scoreboard not implemented
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
                                            .then(literal("custom").executes(context -> setToggleWithStats(context, toggle, "actionbar", "custom")))
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
                                            .then(literal("custom").executes(context -> setToggleWithStats(context, toggle, "chat", "custom")))
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
                                            .then(literal("custom").executes(context -> setToggleWithStats(context, toggle, "title", "custom")))
                                    )

            ));
        }
    }

    private static int setPlayerLanguage(CommandContext<ServerCommandSource> context, String language) {
        ServerCommandSource source = context.getSource();
        if (!(source.getEntity() instanceof ServerPlayerEntity)) {
            source.sendError(Text.literal("This command can only be executed by a player."));
            return 0;
        }

        ServerPlayerEntity player = (ServerPlayerEntity) source.getEntity();
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

            context.getSource().sendFeedback(() -> Text.literal("§aSet language to §e" + language), false);
            return 1;
        } catch (IOException | JsonParseException e) {
            System.err.println("Error handling player language settings: " + e.getMessage());
            context.getSource().sendError(Text.literal("Error saving your language preference."));
            return 0;
        }
    }


    private static void registerColorCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        for (String colorType : new String[]{"text", "category", "material", "number"}) {
            dispatcher.register(
                    literal("toggle")
                            .then(literal("color")
                                    .then(literal(colorType)
                                            .executes(context -> {
                                                context.getSource().sendError(Text.literal("Please specify a color"));
                                                return 0;
                                            })
                                            .then(literal("NONE").executes(context -> setColor(context, colorType, "NONE")))
                                            .then(literal("BLACK").executes(context -> setColor(context, colorType, "BLACK")))
                                            .then(literal("DARK_BLUE").executes(context -> setColor(context, colorType, "DARK_BLUE")))
                                            .then(literal("DARK_GREEN").executes(context -> setColor(context, colorType, "DARK_GREEN")))
                                            .then(literal("DARK_AQUA").executes(context -> setColor(context, colorType, "DARK_AQUA")))
                                            .then(literal("DARK_RED").executes(context -> setColor(context, colorType, "DARK_RED")))
                                            .then(literal("DARK_PURPLE").executes(context -> setColor(context, colorType, "DARK_PURPLE")))
                                            .then(literal("GOLD").executes(context -> setColor(context, colorType, "GOLD")))
                                            .then(literal("GRAY").executes(context -> setColor(context, colorType, "GRAY")))
                                            .then(literal("DARK_GRAY").executes(context -> setColor(context, colorType, "DARK_GRAY")))
                                            .then(literal("BLUE").executes(context -> setColor(context, colorType, "BLUE")))
                                            .then(literal("GREEN").executes(context -> setColor(context, colorType, "GREEN")))
                                            .then(literal("AQUA").executes(context -> setColor(context, colorType, "AQUA")))
                                            .then(literal("RED").executes(context -> setColor(context, colorType, "RED")))
                                            .then(literal("LIGHT_PURPLE").executes(context -> setColor(context, colorType, "LIGHT_PURPLE")))
                                            .then(literal("YELLOW").executes(context -> setColor(context, colorType, "YELLOW")))
                                            .then(literal("WHITE").executes(context -> setColor(context, colorType, "WHITE")))
                                    )
                            ));
        }
    }

    private static int setColor(CommandContext<ServerCommandSource> context, String colorType, String color) {
        ServerCommandSource source = context.getSource();
        if (!(source.getEntity() instanceof ServerPlayerEntity)) {
            source.sendError(Text.literal("This command can only be executed by a player."));
            return 0;
        }

        ServerPlayerEntity player = (ServerPlayerEntity) source.getEntity();
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
            playerData.addProperty(propertyName, color);

            try (FileWriter writer = new FileWriter(playerFile)) {
                new GsonBuilder().setPrettyPrinting().create().toJson(playerData, writer);
            }

            ToggleRenderer.clearCache(playerId);

            String message = color.equals("NONE")
                    ? String.format("§aReset %s color to default", colorType)
                    : String.format("§aSet %s color to §%s%s", colorType, getColorCode(color), color);
            context.getSource().sendFeedback(() -> Text.literal(message), false);
            return 1;
        } catch (IOException | JsonParseException e) {
            System.err.println("Error handling player color settings: " + e.getMessage());
            context.getSource().sendError(Text.literal("Error saving your color preference."));
            return 0;
        }
    }

    public static String getColorCode(String colorName) {
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
            default: return "r";
        }
    }

    private static int setToggleWithStats(CommandContext<ServerCommandSource> context, String toggle, String location, String statCategory) {
        ServerCommandSource source = context.getSource();
        if (!(source.getEntity() instanceof ServerPlayerEntity)) {
            source.sendError(Text.literal("This command can only be executed by a player."));
            return 0;
        }

        ServerPlayerEntity player = (ServerPlayerEntity) source.getEntity();
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
            playerData.addProperty("toggle", toggle);
            playerData.addProperty("toggle_location", location);
            playerData.addProperty("stat_category", statCategory);

            try (FileWriter writer = new FileWriter(playerFile)) {
                new GsonBuilder().setPrettyPrinting().create().toJson(playerData, writer);
            }

            ToggleRenderer.clearCache(playerId);

            context.getSource().sendFeedback(() -> Text.literal("§aSet toggle §e" + toggle +
                    " §ato display in §e" + location + " §awith §e" + statCategory + " §astatistics"), false);
            return 1;
        } catch (IOException | JsonParseException e) {
            System.err.println("Error handling player toggle settings: " + e.getMessage());
            context.getSource().sendError(Text.literal("Error saving your toggle preference."));
            return 0;
        }
    }

    private static int setStatCategory(CommandContext<ServerCommandSource> context, String category) {
        ServerCommandSource source = context.getSource();
        if (!(source.getEntity() instanceof ServerPlayerEntity)) {
            source.sendError(Text.literal("This command can only be executed by a player."));
            return 0;
        }

        ServerPlayerEntity player = (ServerPlayerEntity) source.getEntity();
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

            if (playerData.has("stat_category") && playerData.get("stat_category").getAsString().equals(category)) {
                context.getSource().sendFeedback(() -> Text.literal("§cYou already have statistic category set to " + category), false);
                return 0;
            }

            playerData.addProperty("uuid", playerId.toString());
            playerData.addProperty("playername", player.getName().getString());
            playerData.addProperty("stat_category", category);

            try (FileWriter writer = new FileWriter(playerFile)) {
                new GsonBuilder().setPrettyPrinting().create().toJson(playerData, writer);
            }

            context.getSource().sendFeedback(() -> Text.literal("§aSet statistic category to §e" + category), false);
            return 1;
        } catch (IOException | JsonParseException e) {
            System.err.println("Error handling player statistic category: " + e.getMessage());
            context.getSource().sendError(Text.literal("Error saving your statistic preference."));
            return 0;
        }
    }

    private static int clearPlayerConfig(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = (ServerPlayerEntity) context.getSource().getEntity();
        UUID playerId = player.getUuid();
        File playerFile = new File(PLAYERDATA_DIR, playerId.toString() + ".json");

        ToggleRenderer.clearCache(playerId);

        if (playerFile.exists() && playerFile.delete()) {
            player.sendMessage(Text.empty(), true);
            context.getSource().sendFeedback(() ->
                    Text.literal("§aYour toggle preferences have been reset."), false);
            return 1;
        }

        if (playerFile.exists()) {
            if (playerFile.delete()) {
                context.getSource().sendFeedback(() -> Text.literal("§aYour toggle preferences have been reset."), false);
                return 1;
            } else {
                context.getSource().sendError(Text.literal("§cFailed to reset your toggle preferences."));
                return 0;
            }
        } else {
            context.getSource().sendFeedback(() -> Text.literal("§aYou didn't have any toggle preferences to reset."), false);
            return 1;
        }
    }
}