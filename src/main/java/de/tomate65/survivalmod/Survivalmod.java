package de.tomate65.survivalmod;

//import de.tomate65.survivalmod.commands.RecipeToggleCommand;
import de.tomate65.survivalmod.commands.SurvivalCommand;
import de.tomate65.survivalmod.commands.ToggleCommand;
//import de.tomate65.survivalmod.config.AnabeldDatapack;
import de.tomate65.survivalmod.config.ConfigGenerator;
import de.tomate65.survivalmod.config.ConfigReader;
import de.tomate65.survivalmod.togglerenderer.ToggleRenderer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.network.packet.s2c.play.TitleFadeS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Survivalmod implements ModInitializer {
	public static final String MOD_ID = "survivalmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ConfigGenerator.generateConfigs();
		ConfigReader.loadConfig();

		SurvivalCommand.register();
		ToggleCommand.register();

		// Set default title timings for all players
		ServerTickEvents.START_SERVER_TICK.register(server -> {
			if (server.getTicks() == 1) {
				server.getPlayerManager().getPlayerList().forEach(player -> {
					player.networkHandler.sendPacket(new TitleFadeS2CPacket(0, 40, 0));
				});
			}
		});

		ServerTickEvents.END_SERVER_TICK.register(server -> {
			if (server.getTicks() % 20 != 0) return;
			server.getPlayerManager().getPlayerList().forEach(player -> {
				if (ToggleRenderer.hasActiveToggle(player)) {
					ToggleRenderer.renderForPlayer(player);
				}
			});
		});

		ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
			ToggleRenderer.clearCache(handler.getPlayer().getUuid());
		});

		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			handler.player.networkHandler.sendPacket(new TitleFadeS2CPacket(0, 40, 0));
		});

		LOGGER.info("Survival Mod Loaded");
	}
}