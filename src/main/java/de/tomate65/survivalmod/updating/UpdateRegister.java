package de.tomate65.survivalmod.updating;

import de.tomate65.survivalmod.manager.UpdateHelper;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class UpdateRegister {
    public static void register() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            UpdateHelper.checkAndUpdateConfigs();
        });
    }
}