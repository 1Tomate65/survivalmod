package de.tomate65.survivalmod.manager;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Box;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MagnetManager {
    private static final Map<UUID, Boolean> playerMagnetStates = new HashMap<>();
    private static int magnetHungerStrength = 5;
    private static int baseMagnetRadius = 3;
    private static boolean allowMagnetToggle = false;

    public static void setMagnetState(ServerPlayerEntity player, boolean state) {
        UUID playerId = player.getUuid();
        playerMagnetStates.put(playerId, state);

        // Save to player file
        File playerFile = new File("config/survival/playerdata", playerId.toString() + ".json");
        try {
            JsonObject playerData = new JsonObject();
            if (playerFile.exists()) {
                try (FileReader reader = new FileReader(playerFile)) {
                    playerData = JsonParser.parseReader(reader).getAsJsonObject();
                }
            }

            playerData.addProperty("magnet_enabled", state);

            try (FileWriter writer = new FileWriter(playerFile)) {
                new GsonBuilder().setPrettyPrinting().create().toJson(playerData, writer);
            }
        } catch (IOException e) {
            System.err.println("Error saving magnet state: " + e.getMessage());
        }

        if (state) {
            player.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.HUNGER,
                    Integer.MAX_VALUE,
                    magnetHungerStrength - 1,
                    false,
                    false
            ));
        } else {
            player.removeStatusEffect(StatusEffects.HUNGER);
        }
    }

    public static boolean getMagnetState(UUID playerId) {
        // First check memory
        if (playerMagnetStates.containsKey(playerId)) {
            return playerMagnetStates.get(playerId);
        }

        // Fallback to file
        File playerFile = new File("config/survival/playerdata", playerId.toString() + ".json");
        if (playerFile.exists()) {
            try (FileReader reader = new FileReader(playerFile)) {
                JsonObject playerData = JsonParser.parseReader(reader).getAsJsonObject();
                if (playerData.has("magnet_enabled")) {
                    boolean state = playerData.get("magnet_enabled").getAsBoolean();
                    playerMagnetStates.put(playerId, state); // Cache it
                    return state;
                }
            } catch (IOException e) {
                System.err.println("Error reading magnet state: " + e.getMessage());
            }
        }
        return false;
    }

    public static Box getMagnetBox(ServerPlayerEntity player) {
        double scale = getPlayerScale(player);
        double effectiveRadius = baseMagnetRadius * scale;

        return new Box(
                player.getX() - effectiveRadius,
                player.getY() - effectiveRadius,
                player.getZ() - effectiveRadius,
                player.getX() + effectiveRadius,
                player.getY() + effectiveRadius,
                player.getZ() + effectiveRadius
        );
    }

    public static double getPlayerScale(ServerPlayerEntity player) {
        EntityAttributeInstance scaleAttribute = player.getAttributeInstance(EntityAttributes.SCALE);
        return scaleAttribute != null ? scaleAttribute.getValue() : 1.0;
    }

    public static void onPlayerDisconnect(UUID playerId) {
        playerMagnetStates.remove(playerId);
    }

    public static void setConfigValues(boolean allowToggle, int hungerStrength, int radius) {
        allowMagnetToggle = allowToggle;
        magnetHungerStrength = hungerStrength;
        baseMagnetRadius = radius;
    }

    public static boolean isMagnetAllowed() {
        return allowMagnetToggle;
    }

    public static int getBaseRadius() {
        return baseMagnetRadius;
    }
}