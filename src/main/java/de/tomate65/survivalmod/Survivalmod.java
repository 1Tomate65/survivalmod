package de.tomate65.survivalmod;

import de.tomate65.survivalmod.commands.SurvivalCommand;
//import de.tomate65.survivalmod.commands.ToggleCommand;
import de.tomate65.survivalmod.config.ConfigGenerator;
import de.tomate65.survivalmod.config.ConfigReader;
import net.fabricmc.api.ModInitializer;

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
		//ToggleCommand.register();


		LOGGER.info("Survival Mod Loaded");
	}
}