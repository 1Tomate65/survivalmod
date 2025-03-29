package de.tomate65.survivalmod.togglerenderer;

import com.google.gson.*;
import net.minecraft.block.Block;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import de.tomate65.survivalmod.config.ConfigReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ToggleRenderer {
    private static final File PLAYERDATA_DIR = new File("config/survival/playerdata");
    private static final Map<UUID, PlayerToggleData> playerDataCache = new HashMap<>();
    private static final Map<UUID, Integer> lastMilestoneReached = new HashMap<>();

    private static class PlayerToggleData {
        String toggleItem;
        String location;
        String statCategory;
    }

    public static void renderForPlayer(ServerPlayerEntity player) {
        PlayerToggleData data = getPlayerData(player);
        if (data == null || !hasActiveToggle(player)) {
            return;
        }

        UUID playerId = player.getUuid();
        int currentStatCount = getStatCount(player, data.toggleItem, data.statCategory);
        int frequency = ConfigReader.getChatMsgFrequency();

        switch (data.location) {
            case "actionbar" -> {
                Text message = createActionbarMessage(data.toggleItem, data.statCategory, currentStatCount);
                player.sendMessage(message, true);
            }

            case "chat" -> {
                if (frequency > 0) {
                    if (!lastMilestoneReached.containsKey(playerId)) {
                        lastMilestoneReached.put(playerId, -1); // Initialize to -1 to handle first milestone
                    }

                    int lastMilestone = lastMilestoneReached.get(playerId);
                    int currentMilestone = (currentStatCount / frequency) * frequency;

                    if (currentMilestone > 0 && currentMilestone > lastMilestone) {
                        player.sendMessage(
                                createChatMessage(data.toggleItem, data.statCategory, currentMilestone),
                                false
                        );
                        lastMilestoneReached.put(playerId, currentMilestone);
                    }
                }
            }

            case "title" -> {
                Text message = createTitleMessage(data.toggleItem, data.statCategory, currentStatCount);
                player.networkHandler.sendPacket(new TitleS2CPacket(message));
                player.networkHandler.sendPacket(new TitleS2CPacket(Text.empty()));
            }

            case "scoreboard" -> {
                // Scoreboard implementation is missing
            }
        }
    }

    public static boolean hasActiveToggle(ServerPlayerEntity player) {
        PlayerToggleData data = getPlayerData(player);
        return data != null && data.toggleItem != null && data.location != null && data.statCategory != null;
    }

    private static Text createActionbarMessage(String itemId, String statCategory, int count) {
        return Text.literal(String.format("%s %s§8:§r %d",
                getItemDisplayName(itemId),
                getStatDisplayName(statCategory),
                count));
    }

    private static Text createChatMessage(String itemId, String statCategory, int count) {
        String itemName = getItemDisplayName(itemId);
        String actionVerb = getActionVerb(statCategory);
        String formattedItem = count == 1 ? itemName : itemName + "'s.";
        return Text.literal(String.format("You %s %d %s", actionVerb, count, formattedItem));
    }

    private static Text createTitleMessage(String itemId, String statCategory, int count) {
        return createActionbarMessage(itemId, statCategory, count);
    }

    private static String getItemDisplayName(String itemId) {
        try {
            Item item = Registries.ITEM.get(Identifier.of(itemId));
            return item.getName().getString();
        } catch (Exception e) {
            return itemId;
        }
    }

    private static String getStatDisplayName(String statKey) {
        return Arrays.stream(statKey.split("_"))
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                .collect(Collectors.joining(" "));
    }

    private static String getActionVerb(String statCategory) {
        return switch (statCategory) {
            case "mined" -> "mined";
            case "crafted" -> "crafted";
            case "used" -> "used";
            case "broken" -> "broke";
            case "picked_up" -> "picked up";
            case "dropped" -> "dropped";
            default -> statCategory.replace("_", " ");
        };
    }

    private static int getStatCount(ServerPlayerEntity player, String itemName, String statCategory) {
        try {
            Identifier id = Identifier.of(itemName);
            Item item = Registries.ITEM.get(id);
            if (item == null) return 0;

            Stat<?> stat = switch (statCategory) {
                case "mined" -> Stats.MINED.getOrCreateStat(Block.getBlockFromItem(item));
                case "crafted" -> Stats.CRAFTED.getOrCreateStat(item);
                case "used" -> Stats.USED.getOrCreateStat(item);
                case "broken" -> Stats.BROKEN.getOrCreateStat(item);
                case "picked_up" -> Stats.PICKED_UP.getOrCreateStat(item);
                case "dropped" -> Stats.DROPPED.getOrCreateStat(item);
                default -> null;
            };

            return stat != null ? player.getStatHandler().getStat(stat) : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    private static PlayerToggleData getPlayerData(ServerPlayerEntity player) {
        UUID playerId = player.getUuid();

        if (playerDataCache.containsKey(playerId)) {
            return playerDataCache.get(playerId);
        }

        File playerFile = new File(PLAYERDATA_DIR, playerId.toString() + ".json");
        if (!playerFile.exists()) return null;

        try (FileReader reader = new FileReader(playerFile)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            PlayerToggleData data = new PlayerToggleData();

            if (json.has("toggle")) data.toggleItem = json.get("toggle").getAsString();
            if (json.has("toggle_location")) data.location = json.get("toggle_location").getAsString();
            if (json.has("stat_category")) data.statCategory = json.get("stat_category").getAsString();

            playerDataCache.put(playerId, data);
            return data;
        } catch (IOException | JsonParseException e) {
            System.err.println("Error reading player toggle data: " + e.getMessage());
            return null;
        }
    }

    public static void clearCache(UUID playerId) {
        playerDataCache.remove(playerId);
        lastMilestoneReached.remove(playerId);
    }
}