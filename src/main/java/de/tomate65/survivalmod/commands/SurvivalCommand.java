package de.tomate65.survivalmod.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.minecraft.server.command.CommandManager.literal;

public class SurvivalCommand {
    private static final File CONFIG_FILE = new File("config/survival/survival.json");
    private static Map<String, List<String>> subcommands = new HashMap<>();

    public static void register() {
        loadConfig();
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            registerCommands(dispatcher);
        });
    }

    public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        // Main command with reload subcommand
        dispatcher.register(
                literal("survival")
                        .then(literal("reload")
                                .requires(source -> source.hasPermissionLevel(2)) // OP level 2 required
                                .executes(context -> {
                                    loadConfig();
                                    context.getSource().sendFeedback(() -> Text.literal("§7Configuration reloaded."), false);
                                    return 1;
                                })
                        )
                        .executes(SurvivalCommand::listAvailableCommands)
        );

        // Register all subcommands from config
        for (String subcommand : subcommands.keySet()) {
            dispatcher.register(
                    literal("survival")
                            .then(literal(subcommand)
                                    .executes(context -> executeSubcommand(context, subcommand))
                            ));
        }
    }

    private static void loadConfig() {
        if (!CONFIG_FILE.exists()) {
            System.err.println("§cConfiguration file does not exist: " + CONFIG_FILE.getPath());
            return;
        }

        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            JsonObject config = JsonParser.parseReader(reader).getAsJsonObject();
            JsonObject commands = config.getAsJsonObject("commands");
            JsonObject survival = commands.getAsJsonObject("survival");

            subcommands.clear();
            for (String key : survival.keySet()) {
                JsonArray values = survival.getAsJsonArray(key);
                List<String> messages = new ArrayList<>();
                for (int i = 0; i < values.size(); i++) {
                    messages.add(values.get(i).getAsString());
                }
                subcommands.put(key, messages);
            }
        } catch (IOException | JsonParseException e) {
            System.err.println("Error reading configuration file: " + e.getMessage());
        }
    }

    private static int executeSubcommand(CommandContext<ServerCommandSource> context, String subcommand) {
        ServerCommandSource source = context.getSource();
        List<String> messages = subcommands.get(subcommand);

        if (messages == null || messages.isEmpty()) {
            source.sendError(Text.literal("No content available for this subcommand."));
            return 1;
        }

        context.getSource().sendFeedback(() -> Text.literal("§aSurvival §b" + subcommand + "§7:§r"), false);
        for (String message : messages) {
            context.getSource().sendFeedback(() -> Text.literal(message), false);
        }
        return 1;
    }

    private static int listAvailableCommands(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();

        if (subcommands.isEmpty()) {
            source.sendError(Text.literal("No subcommands available. Check your configuration."));
            return 1;
        }

        context.getSource().sendFeedback(() -> Text.literal("Available subcommands:"), false);
        for (String subcommand : subcommands.keySet()) {
            context.getSource().sendFeedback(() -> Text.literal("§7/§asurvival §b" + subcommand), false);
        }
        return 1;
    }
}