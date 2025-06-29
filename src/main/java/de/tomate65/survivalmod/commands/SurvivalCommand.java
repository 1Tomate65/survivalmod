package de.tomate65.survivalmod.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import de.tomate65.survivalmod.config.ConfigReader;
import de.tomate65.survivalmod.manager.ConfigBackupManager;
import de.tomate65.survivalmod.manager.SurvivalInfoManager;
import de.tomate65.survivalmod.manager.UpdateHelper;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static de.tomate65.survivalmod.commands.ToggleCommand.*;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class SurvivalCommand {
    private static final File CONFIG_FILE = new File("config/survival/" + ConfigReader.getModVersion() + "/survival.json");
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
                        .then(literal("version")
                                .requires(source -> source.hasPermissionLevel(1))
                                .executes(SurvivalCommand::execute))
                        .executes(SurvivalCommand::listAvailableCommands)

                        .then(literal("autoupdate")
                                .requires(source -> source.hasPermissionLevel(4)) // High permission level
                                .executes(SurvivalCommand::executeAutoUpdate))

                        .then(literal("language")
                                .executes(context -> showLanguageUsage(context))
                                .then(argument("lang", StringArgumentType.word())
                                        .suggests(LANGUAGE_SUGGESTIONS)
                                        .executes(context -> setPlayerLanguage(context, StringArgumentType.getString(context, "lang"))))));


        for (String subcommand : subcommands.keySet()) {
            dispatcher.register(
                    literal("survival")
                            .then(literal(subcommand)
                                    .executes(context -> executeSubcommand(context, subcommand))
                            ));
        }
    }

    private static int reloadConfigs(ServerCommandSource source) {
        try {
            // Change this line:
            ConfigBackupManager.getInstance().checkAndUpdateConfigs();
            ConfigReader.loadConfig();
            source.sendFeedback((Supplier<Text>) Text.literal("Configs reloaded successfully"), false);
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("Failed to reload configs: " + e.getMessage()));
            return 0;
        }
    }

    private static int executeAutoUpdate(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();

        if (!source.hasPermissionLevel(4)) {
            source.sendError(Text.literal("You don't have permission to use this command.").formatted(Formatting.RED));
            return 0;
        }

        try {
            source.sendFeedback(() -> Text.literal("§7Starting config update process..."), false);

            // Perform the update check and migration
            ConfigBackupManager.getInstance().checkAndUpdateConfigs();

            // Reload the config to ensure we're using the latest version
            loadConfig();

            source.sendFeedback(() -> Text.literal("§aConfig files have been successfully updated to version " + UpdateHelper.getCurrentVersion()), false);
            source.sendFeedback(() -> Text.literal("§7The old config files have been preserved in their version folder."), false);

            return Command.SINGLE_SUCCESS;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError during auto-update: " + e.getMessage()));
            e.printStackTrace();
            return -1;
        }
    }

    static int execute(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();

        if (!source.isExecutedByPlayer() || !source.getPlayer().hasPermissionLevel(1)) {
            source.sendFeedback(() -> Text.literal("You must be an operator to use this command.").formatted(Formatting.RED), false);
            return Command.SINGLE_SUCCESS;
        }

        // Send mod info
        source.sendFeedback(() -> Text.literal("You're running ")
                .append(Text.literal(SurvivalInfoManager.MOD_VERSION).formatted(Formatting.GREEN))
                .append(" of the Survival Mod").formatted(Formatting.GREEN), false);

        // Empty line
        source.sendFeedback(() -> Text.empty(), false);

        // Check for updates
        if (SurvivalInfoManager.isUpdateAvailable()) {
            source.sendFeedback(() -> Text.literal("There has been an update!").formatted(Formatting.GREEN), false);

            Text clickableText = Text.literal("Click here to go to the Modrinth Page")
                    .setStyle(Style.EMPTY
                            .withColor(Formatting.RED)
                            .withUnderline(true)
                            .withClickEvent(new ClickEvent.OpenUrl(URI.create("https://modrinth.com/mod/survivalmod")))
                    );
            source.sendFeedback(() -> clickableText, false);
        } else {
            source.sendFeedback(() -> Text.literal("There has been no update yet.").formatted(Formatting.GOLD), false);
        }

        return Command.SINGLE_SUCCESS;
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
