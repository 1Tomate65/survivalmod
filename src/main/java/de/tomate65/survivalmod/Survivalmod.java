package de.tomate65.survivalmod;

import com.mojang.brigadier.CommandDispatcher;
import de.tomate65.survivalmod.commands.RecipeCommand;
import de.tomate65.survivalmod.commands.SurvivalCommand;
import de.tomate65.survivalmod.commands.ToggleCommand;
import de.tomate65.survivalmod.config.ConfigGenerator;
import de.tomate65.survivalmod.config.ConfigReader;
import de.tomate65.survivalmod.datapackgen.DatapackGen;
import de.tomate65.survivalmod.manager.ConfigBackupManager;
import de.tomate65.survivalmod.manager.MagnetManager;
import de.tomate65.survivalmod.manager.RecipeHandler;
import de.tomate65.survivalmod.manager.UpdateHelper;
import de.tomate65.survivalmod.togglerenderer.ToggleRenderer;
import de.tomate65.survivalmod.togglerenderer.ToggleRenderer.PlayerToggleData;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.ItemEntity;
import net.minecraft.network.packet.s2c.play.TitleFadeS2CPacket;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Survivalmod implements ModInitializer {
	public static final String MOD_ID = "survivalmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final String ModVersion = "0.2.5-S1";

	@Override
	public void onInitialize() {
		LOGGER.info("Survival Mod started to initialize!");

		ConfigBackupManager.getInstance().checkAndUpdateConfigs();

		String oldVersion = ConfigReader.getModVersion();
		if (!oldVersion.equals(ModVersion)) {
			try {
				UpdateHelper.migrateThroughVersions(oldVersion);
				LOGGER.info("[SurvivalMod] Migration von {} nach {} abgeschlossen.", oldVersion, ModVersion);
			} catch (IOException e) {
				LOGGER.error("[SurvivalMod] Migration fehlgeschlagen: {}", e.getMessage());
			}
		}

		ConfigGenerator.generateConfigs();
		ConfigReader.loadConfig();
		RecipeHandler.initialize();

		File datapackFolder = new File("world/datapacks/runtime_recipes");
		Set<String> enabledRecipes = new HashSet<>();
		RecipeHandler.getRecipeStates().forEach((recipe, enabled) -> {
			if (enabled) {
				enabledRecipes.add(recipe + ".json");
			}
		});

		DatapackGen.registerRecipesToDatapack(datapackFolder, enabledRecipes, RecipeHandler.RECIPE_DIR);

		SurvivalCommand.register();
		ToggleCommand.register();


		ServerTickEvents.START_SERVER_TICK.register(server -> {
			if (server.getTicks() == 1) {
				server.getPlayerManager().getPlayerList().forEach(player -> {
					player.networkHandler.sendPacket(new TitleFadeS2CPacket(0, 40, 0));
				});
			}
		});

		ServerLifecycleEvents.SERVER_STARTING.register(server -> {
			ConfigBackupManager.getInstance().checkAndUpdateConfigs();
			CommandDispatcher<ServerCommandSource> dispatcher = server.getCommandManager().getDispatcher();
			RecipeCommand.register(dispatcher);
		});

		ServerTickEvents.END_SERVER_TICK.register(server -> {
			if (server.getTicks() % 5 != 0) return;

			server.getPlayerManager().getPlayerList().forEach(player -> {
				if (MagnetManager.getMagnetState(player.getUuid())) {
					Box attractionBox = MagnetManager.getMagnetBox(player);
					player.getWorld().getEntitiesByClass(ItemEntity.class, attractionBox, item -> true)
							.forEach(item -> {
								if (item.isAlive() && !item.cannotPickup()) {
									Vec3d playerPos = player.getPos();
									Vec3d itemPos = item.getPos();
									Vec3d direction = playerPos.subtract(itemPos).normalize();
									double distance = playerPos.distanceTo(itemPos);
									double speed = Math.min(0.5, 0.1 + (1.0 / distance));
									item.setVelocity(direction.multiply(speed));
								}
							});
				}

				PlayerToggleData data = ToggleRenderer.getPlayerData(player);
				if (data != null && ToggleRenderer.hasActiveToggle(player)) {
					LOGGER.debug("Rendering toggle for {}: {} {} {}",
							player.getName().getString(),
							data.toggleItem,
							data.location,
							data.statCategory);
				}

				ToggleRenderer.renderForPlayer(player);
			});
		});

		ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
			ToggleRenderer.clearCache(handler.getPlayer().getUuid());
		});

		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			ServerPlayerEntity player = handler.player;
			player.networkHandler.sendPacket(new TitleFadeS2CPacket(0, 40, 0));

			ToggleRenderer.clearCache(player.getUuid());
			if (ToggleRenderer.hasActiveToggle(player)) {
				ToggleRenderer.renderForPlayer(player);
			}
		});

		ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, resourceManager, success) -> {
			if (success) {
				CommandDispatcher<ServerCommandSource> dispatcher = server.getCommandManager().getDispatcher();
				RecipeCommand.register(dispatcher);

				RecipeHandler.getRecipeStates().forEach((recipe, enabled) -> {
					if (enabled) enabledRecipes.add(recipe + ".json");
				});
				DatapackGen.registerRecipesToDatapack(datapackFolder, enabledRecipes, RecipeHandler.RECIPE_DIR);

				LOGGER.info("Datapack and commands successfully reloaded");
			} else {
				LOGGER.error("Datapack reload failed - recipes not updated");
			}
		});

		LOGGER.info("Survival Mod Loaded");
	}
}