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
import de.tomate65.survivalmod.commands.ToggleCommand;

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
        String textColor;
        String categoryColor;
        String materialColor;
        String numberColor;
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
                Text message = createActionbarMessage(player, data.toggleItem, data.statCategory, currentStatCount);
                player.sendMessage(message, true);
            }
            case "chat" -> {
                if (frequency > 0) {
                    if (!lastMilestoneReached.containsKey(playerId)) {
                        lastMilestoneReached.put(playerId, -1);
                    }

                    int lastMilestone = lastMilestoneReached.get(playerId);
                    int currentMilestone = (currentStatCount / frequency) * frequency;

                    if (currentMilestone > 0 && currentMilestone > lastMilestone) {
                        player.sendMessage(
                                createChatMessage(player, data.toggleItem, data.statCategory, currentMilestone),
                                false
                        );
                        lastMilestoneReached.put(playerId, currentMilestone);
                    }
                }
            }
            case "title" -> {
                Text message = createTitleMessage(player, data.toggleItem, data.statCategory, currentStatCount);
                player.networkHandler.sendPacket(new TitleS2CPacket(message));
                //player.networkHandler.sendPacket(new TitleS2CPacket(Text.empty()));
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

    private static Text createActionbarMessage(ServerPlayerEntity player, String itemId, String statCategory, int count) {
        String materialColor = getPlayerColor(player, "material");
        String categoryColor = getPlayerColor(player, "category");
        String numberColor = getPlayerColor(player, "number");

        return Text.literal(String.format("%s%s %s%s§8:§r %s%d",
                materialColor, getItemDisplayName(itemId),
                categoryColor, getStatDisplayName(statCategory),
                numberColor, count));
    }

    private static Text createChatMessage(ServerPlayerEntity player, String itemId, String statCategory, int count) {
        String textColor = getPlayerColor(player, "text");
        String categoryColor = getPlayerColor(player, "category");
        String numberColor = getPlayerColor(player, "number");
        String materialColor = getPlayerColor(player, "material");

        String itemName = getItemDisplayName(itemId);
        String actionName = getActionVerb(statCategory);
        String pluralSuffix = count == 1 ? "" : "s";

        return Text.literal(String.format("%sYou %s%s %s%d§8: %s%s§7'%ss§8!",
                textColor,
                categoryColor, actionName,
                numberColor, count,
                materialColor, itemName,
                textColor, pluralSuffix));
    }


    private static Text createTitleMessage(ServerPlayerEntity player, String itemId, String statCategory, int count) {
        return createActionbarMessage(player, itemId, statCategory, count);
    }

    private static String getPlayerColor(ServerPlayerEntity player, String colorType) {
        PlayerToggleData data = getPlayerData(player);
        if (data == null) {
            return getDefaultColor(colorType);
        }

        String color = null;
        switch (colorType) {
            case "text": color = data.textColor; break;
            case "category": color = data.categoryColor; break;
            case "material": color = data.materialColor; break;
            case "number": color = data.numberColor; break;
        }

        if (color == null || color.equals("NONE")) {
            return getDefaultColor(colorType);
        }

        return "§" + ToggleCommand.getColorCode(color);
    }

    private static String getDefaultColor(String colorType) {
        switch (colorType) {
            case "text": return "§" + ToggleCommand.getColorCode(ConfigReader.getDefaultTextColor());
            case "category": return "§" + ToggleCommand.getColorCode(ConfigReader.getDefaultCategoryColor());
            case "material": return "§" + ToggleCommand.getColorCode(ConfigReader.getDefaultMaterialColor());
            case "number": return "§" + ToggleCommand.getColorCode(ConfigReader.getDefaultNumberColor());
            default: return "§r";
        }
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
            if (json.has("text_color")) data.textColor = json.get("text_color").getAsString();
            if (json.has("category_color")) data.categoryColor = json.get("category_color").getAsString();
            if (json.has("material_color")) data.materialColor = json.get("material_color").getAsString();
            if (json.has("number_color")) data.numberColor = json.get("number_color").getAsString();

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