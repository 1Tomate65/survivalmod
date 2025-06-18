package de.tomate65.survivalmod.updating;

import de.tomate65.survivalmod.manager.ConfigBackupManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class UpdateRegister {
    public static void register() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            ConfigBackupManager.getInstance().checkAndUpdateConfigs();
        });
    }
}