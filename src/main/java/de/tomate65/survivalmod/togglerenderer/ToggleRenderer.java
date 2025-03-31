package de.tomate65.survivalmod.togglerenderer;

import com.google.gson.*;
import net.minecraft.block.Block;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
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
        String language;
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
            }
            case "scoreboard" -> {
                // Scoreboard implementation is missing
            }
        }
    }

    private static TextColor parseColor(String colorCode) {
        if (colorCode.startsWith("§")) {
            Formatting formatting = Formatting.byCode(colorCode.charAt(1));
            if (formatting != null && formatting.isColor()) {
                return TextColor.fromFormatting(formatting);
            }
        }
        else if (colorCode.startsWith("#")) {
            try {
                return TextColor.parse(colorCode).result().orElse(TextColor.fromFormatting(Formatting.WHITE));
            } catch (Exception e) {
                return TextColor.fromFormatting(Formatting.WHITE);
            }
        }
        return TextColor.fromFormatting(Formatting.WHITE);
    }

    private static Text createActionbarMessage(ServerPlayerEntity player, String itemId, String statCategory, int count) {
        PlayerToggleData data = getPlayerData(player);
        String langCode = data != null && data.language != null ? data.language : ConfigReader.getDefaultLanguage();

        Text itemName = getTranslatedItemName(player, itemId);
        String statName = ConfigReader.translate("stat." + statCategory, langCode);

        return Text.literal("")
                .append(itemName.copy().styled(style -> style.withColor(parseColor(getPlayerColor(player, "material")))))
                .append(" ")
                .append(Text.literal(statName).styled(style -> style.withColor(parseColor(getPlayerColor(player, "category")))))
                .append(": ")
                .append(Text.literal(String.valueOf(count)).styled(style -> style.withColor(parseColor(getPlayerColor(player, "number")))));
    }

    private static Text createChatMessage(ServerPlayerEntity player, String itemId, String statCategory, int count) {
        PlayerToggleData data = getPlayerData(player);
        String langCode = data != null && data.language != null ? data.language : ConfigReader.getDefaultLanguage();

        Text itemName = getTranslatedItemName(player, itemId);
        String actionVerb = ConfigReader.translate("message.action." + statCategory, langCode);
        String you = ConfigReader.translate("message.you", langCode);
        String have = ConfigReader.translate("message.have", langCode);
        String pluralSuffix = count == 1 ? "" : ConfigReader.translate("message.plural", langCode);
        String exclamation = ConfigReader.translate("message.exclamation", langCode);

        return Text.literal("")
                .append(Text.literal(you).styled(style -> style.withColor(parseColor(getPlayerColor(player, "text")))))
                .append(" ")
                .append(Text.literal(have).styled(style -> style.withColor(parseColor(getPlayerColor(player, "text")))))
                .append(" ")
                .append(Text.literal(actionVerb).styled(style -> style.withColor(parseColor(getPlayerColor(player, "category")))))
                .append(" ")
                .append(Text.literal(String.valueOf(count)).styled(style -> style.withColor(parseColor(getPlayerColor(player, "number")))))
                .append(" ")
                .append(itemName.copy().styled(style -> style.withColor(parseColor(getPlayerColor(player, "material")))))
                .append(pluralSuffix)
                .append(Text.literal(exclamation).styled(style -> style.withColor(parseColor(getPlayerColor(player, "text")))));
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

    private static Text getTranslatedItemName(ServerPlayerEntity player, String itemId) {
        try {
            Item item = Registries.ITEM.get(Identifier.of(itemId));
            return Text.translatable(item.getTranslationKey());
        } catch (Exception e) {
            return Text.literal(itemId);
        }
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

    public static boolean hasActiveToggle(ServerPlayerEntity player) {
        PlayerToggleData data = getPlayerData(player);
        return data != null && data.toggleItem != null && data.location != null && data.statCategory != null;
    }

    public static void clearCache(UUID playerId) {
        playerDataCache.remove(playerId);
        lastMilestoneReached.remove(playerId);
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
            if (json.has("language")) data.language = json.get("language").getAsString();

            playerDataCache.put(playerId, data);
            return data;
        } catch (IOException | JsonParseException e) {
            System.err.println("Error reading player toggle data: " + e.getMessage());
            return null;
        }
    }
}