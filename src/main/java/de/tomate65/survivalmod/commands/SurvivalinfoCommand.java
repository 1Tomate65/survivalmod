package de.tomate65.survivalmod.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import de.tomate65.survivalmod.manager.SurvivalInfoManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import java.net.URI;

import static net.minecraft.server.command.CommandManager.literal;

public class SurvivalinfoCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                literal("survivalinfo")
                        .requires(source -> source.hasPermissionLevel(1))
                        .executes(SurvivalinfoCommand::execute)
        );
    }

    private static int execute(CommandContext<ServerCommandSource> context) {
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
}