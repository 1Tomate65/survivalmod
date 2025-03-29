package de.tomate65.survivalmod.commands;

import com.google.gson.*;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import de.tomate65.survivalmod.config.ConfigGenerator;
import de.tomate65.survivalmod.togglerenderer.ToggleRenderer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static net.minecraft.server.command.CommandManager.literal;

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
                        .then(literal("reload").executes(context -> {
                            if (!isCommandEnabled) {
                                context.getSource().sendError(Text.literal("This Command is Disabled, please contact an admin if you think this is a mistake"));
                                return 0;
                            }
                            loadConfig();
                            loadMainConfig();
                            context.getSource().sendFeedback(() -> Text.literal("Toggle configuration reloaded."), false);
                            return 1;
                        }))
                        .then(literal("clear").executes(context -> {
                            if (!isCommandEnabled) {
                                context.getSource().sendError(Text.literal("This Command is Disabled, please contact an admin if you think this is a mistake"));
                                return 0;
                            }
                            return clearPlayerConfig(context);
                        }))
        );

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
                                    /*.then(literal("title")
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
                                    )*/ //title not implemented right
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

            )));
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