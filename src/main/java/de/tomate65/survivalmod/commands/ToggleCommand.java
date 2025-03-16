package de.tomate65.survivalmod.commands;

import com.google.gson.*;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

import static net.minecraft.server.command.CommandManager.literal;

public class ToggleCommand {
    private static final File CONFIG_FILE = new File("config/survival/toggle.json");
    private static Set<String> availableToggles = new HashSet<>();

    public static void register() {
        loadConfig();
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated, dedicated2) -> registerCommands(dispatcher));
    }

    private static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                literal("toggle")
                        .then(literal("reload").executes(context -> {
                            loadConfig();
                            context.getSource().sendFeedback(() -> Text.literal("Toggle configuration reloaded."), false);
                            return 1;
                        }))
                        .then(literal("stone").executes(context -> toggleTag(context, "stone")))
        );

        for (String toggle : availableToggles) {
            dispatcher.register(
                    literal("toggle")
                            .then(literal(toggle).executes(context -> toggleTag(context, toggle)))
            );
        }
    }

    static void loadConfig() {
        if (!CONFIG_FILE.exists()) {
            createDefaultConfig();
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

    public static void createDefaultConfig() {
        try {
            CONFIG_FILE.getParentFile().mkdirs();
            JsonObject config = new JsonObject();
            JsonArray defaultToggles = new JsonArray();
            defaultToggles.add("stone");
            config.add("toggles", defaultToggles);

            try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                new GsonBuilder().setPrettyPrinting().create().toJson(config, writer);
            }
            System.out.println("Created default toggle configuration.");
        } catch (IOException e) {
            System.err.println("Error creating default toggle config: " + e.getMessage());
        }
    }

    private static int toggleTag(CommandContext<ServerCommandSource> context, String tag) {
        ServerCommandSource source = context.getSource();
        if (!(source.getEntity() instanceof ServerPlayerEntity)) {
            source.sendError(Text.literal("This command can only be executed by a player."));
            return 0;
        }

        ServerPlayerEntity player = (ServerPlayerEntity) source.getEntity();

        if (player.getCommandTags().contains(tag)) {
            player.removeCommandTag(tag);
            context.getSource().sendFeedback(() -> Text.literal("§2Removed tag§7: §e" + tag), false);
        } else {
            player.addCommandTag(tag);
            context.getSource().sendFeedback(() -> Text.literal("§aAdded tag§7: §e" + tag), false);
        }

        return 1;
    }
}
