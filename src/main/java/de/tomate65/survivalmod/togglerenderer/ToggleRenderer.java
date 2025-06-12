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
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import de.tomate65.survivalmod.config.ConfigReader;
import de.tomate65.survivalmod.commands.ToggleCommand;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ToggleRenderer {
    private static final File PLAYERDATA_DIR = new File("config/survival/" + ConfigReader.getModVersion() + "/playerdata");
    private static final Map<UUID, PlayerToggleData> playerDataCache = new HashMap<>();
    private static final Map<UUID, Integer> lastMilestoneReached = new HashMap<>();

    public static class PlayerToggleData {
        public String toggleItem;
        public String location;
        public String statCategory;
        String textColor;
        String categoryColor;
        String materialColor;
        String numberColor;
        String timeColor;
        String language;
    }

    public static void renderForPlayer(ServerPlayerEntity player) {
        PlayerToggleData data = getPlayerData(player);
        if (data == null || !hasActiveToggle(player)) {
            return;
        }

        UUID playerId = player.getUuid();
        int currentStatCount = data.toggleItem.equals("timeplayed")
                ? player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.PLAY_TIME))
                : getStatCount(player, data.toggleItem, data.statCategory);

        if (data.location.equals("actionbar")) {
            Text message = data.toggleItem.equals("timeplayed")
                    ? Text.literal(formatPlaytime(currentStatCount, player)) // Updated call
                    .styled(style -> style.withColor(parseColor(getPlayerColor(player, "time"))))
                    : createActionbarMessage(player, data.toggleItem, data.statCategory, currentStatCount);
            player.sendMessage(message, true);

        } else if (data.location.equals("chat")) {
            handleChatMessage(player, data, currentStatCount);

        } else if (data.location.equals("title")) {
            Text message = createTitleMessage(player, data.toggleItem, data.statCategory, currentStatCount);
            player.networkHandler.sendPacket(new TitleS2CPacket(message));
        }
    }

    private static void handleChatMessage(ServerPlayerEntity player, PlayerToggleData data, int currentStatCount) {
        int frequency = ConfigReader.getChatMsgFrequency();
        if (frequency > 0) {
            UUID playerId = player.getUuid();
            lastMilestoneReached.putIfAbsent(playerId, -1);

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

    private static Text createActionbarMessage(ServerPlayerEntity player, String itemId, String statCategory, int count) {
        String langCode = getPlayerLanguage(player);
        Text itemName = getTranslatedItemName(player, itemId, statCategory);
        String statName = ConfigReader.translate("stat." + statCategory, langCode);

        // Get all colors
        TextColor materialColor = parseColor(getPlayerColor(player, "material"));
        TextColor categoryColor = parseColor(getPlayerColor(player, "category"));
        TextColor numberColor = parseColor(getPlayerColor(player, "number"));

        return Text.empty()
                .append(itemName.copy().styled(style -> style.withColor(materialColor)))
                .append(Text.literal(" ").styled(style -> style.withColor(Formatting.RESET)))
                .append(Text.literal(statName).styled(style -> style.withColor(categoryColor)))
                .append(Text.literal(": ").styled(style -> style.withColor(Formatting.RESET)))
                .append(Text.literal(String.valueOf(count)).styled(style -> style.withColor(numberColor)));
    }

    private static Text createChatMessage(ServerPlayerEntity player, String itemId, String statCategory, int count) {
        String langCode = getPlayerLanguage(player);
        Text itemName = getTranslatedItemName(player, itemId, statCategory);
        String actionVerb = ConfigReader.translate("message.action." + statCategory, langCode);
        String you = ConfigReader.translate("message.you", langCode);
        String have = ConfigReader.translate("message.have", langCode);
        String pluralSuffix = count == 1 ? "" : ConfigReader.translate("message.plural", langCode);
        String exclamation = ConfigReader.translate("message.exclamation", langCode);

        // Get all colors
        TextColor textColor = parseColor(getPlayerColor(player, "text"));
        TextColor categoryColor = parseColor(getPlayerColor(player, "category"));
        TextColor numberColor = parseColor(getPlayerColor(player, "number"));
        TextColor materialColor = parseColor(getPlayerColor(player, "material"));

        // Build the message with proper translations and colors
        return Text.empty()
                .append(Text.literal(you + " ").styled(style -> style.withColor(textColor)))
                .append(Text.literal(have + " ").styled(style -> style.withColor(textColor)))
                .append(Text.literal(actionVerb + " ").styled(style -> style.withColor(categoryColor)))
                .append(Text.literal(String.valueOf(count) + " ").styled(style -> style.withColor(numberColor)))
                .append(itemName.copy().styled(style -> style.withColor(materialColor)))
                .append(Text.literal(pluralSuffix + exclamation).styled(style -> style.withColor(textColor)));
    }

    private static Text createTitleMessage(ServerPlayerEntity player, String itemId, String statCategory, int count) {
        return createActionbarMessage(player, itemId, statCategory, count);
    }

    private static Text getTranslatedItemName(ServerPlayerEntity player, String itemId, String statCategory) {
        try {
            if (statCategory.equals("killed") || statCategory.equals("killed_by")) {
                EntityType<?> entityType = Registries.ENTITY_TYPE.get(Identifier.tryParse(itemId));
                if (entityType != null) {
                    return Text.translatable(entityType.getTranslationKey());
                }
            }

            Item item = Registries.ITEM.get(Identifier.tryParse(itemId));
            if (item != null) {
                return Text.translatable(item.getTranslationKey());
            }

            Block block = Registries.BLOCK.get(Identifier.tryParse(itemId));
            if (block != null) {
                return Text.translatable(block.getTranslationKey());
            }

            return Text.literal(itemId);
        } catch (Exception e) {
            return Text.literal(itemId);
        }
    }

    private static String formatPlaytime(int ticks, ServerPlayerEntity player) {
        int seconds = ticks / 20;
        int minutes = seconds / 60;
        int hours = minutes / 60;
        int days = hours / 24;
        int years = days / 365;

        seconds %= 60;
        minutes %= 60;
        hours %= 24;
        days %= 365;

        StringBuilder sb = new StringBuilder();
        String lang = ToggleRenderer.getPlayerLanguage(player);

        if (years > 0) sb.append(years).append(ConfigReader.translate("time.years", lang)).append(" ");
        if (days > 0) sb.append(days).append(ConfigReader.translate("time.days", lang)).append(" ");
        if (hours > 0) sb.append(hours).append(ConfigReader.translate("time.hours", lang)).append(" ");
        if (minutes > 0) sb.append(minutes).append(ConfigReader.translate("time.minutes", lang)).append(" ");
        if (seconds > 0 || sb.length() == 0) {
            sb.append(seconds).append(ConfigReader.translate("time.seconds", lang));
        }

        return sb.toString().trim();
    }

    public static TextColor parseColor(String colorCode) {
        if (colorCode == null || colorCode.equalsIgnoreCase("NONE")) {
            return TextColor.fromFormatting(Formatting.WHITE);
        }

        // Handle hex codes (with or without # prefix)
        if (colorCode.matches("^[0-9a-fA-F]{6}$")) {
            try {
                return TextColor.fromRgb(Integer.parseInt(colorCode, 16));
            } catch (NumberFormatException e) {
                return TextColor.fromFormatting(Formatting.WHITE);
            }
        } else if (colorCode.startsWith("#") && colorCode.matches("^#[0-9a-fA-F]{6}$")) {
            try {
                return TextColor.fromRgb(Integer.parseInt(colorCode.substring(1), 16));
            } catch (NumberFormatException e) {
                return TextColor.fromFormatting(Formatting.WHITE);
            }
        }

        // Handle Minecraft formatting codes
        if (colorCode.startsWith("§")) {
            Formatting formatting = Formatting.byCode(colorCode.charAt(1));
            if (formatting != null && formatting.isColor()) {
                return TextColor.fromFormatting(formatting);
            }
        } else if (colorCode.startsWith("&")) {
            Formatting formatting = Formatting.byCode(colorCode.charAt(1));
            if (formatting != null && formatting.isColor()) {
                return TextColor.fromFormatting(formatting);
            }
        }

        // Handle color names by converting to format code
        Formatting formatting = Formatting.byName(colorCode.toUpperCase());
        if (formatting != null && formatting.isColor()) {
            return TextColor.fromFormatting(formatting);
        }

        return TextColor.fromFormatting(Formatting.WHITE);
    }

    public static String getPlayerColor(ServerPlayerEntity player, String colorType) {
        PlayerToggleData data = getPlayerData(player);
        if (data == null) {
            return getDefaultColor(colorType);
        }

        String color = switch (colorType) {
            case "text" -> data.textColor;
            case "category" -> data.categoryColor;
            case "material" -> data.materialColor;
            case "number" -> data.numberColor;
            case "time" -> data.timeColor;
            default -> null;
        };

        if (color == null || color.equals("NONE")) {
            return getDefaultColor(colorType);
        }

        if (color.startsWith("#") || color.equals("&")) {
            return color;
        }
        return "§" + ToggleCommand.getColorCode(color);
    }

    private static String getDefaultColor(String colorType) {
        return switch (colorType) {
            case "text" -> "§" + ToggleCommand.getColorCode(ConfigReader.getDefaultTextColor());
            case "category" -> "§" + ToggleCommand.getColorCode(ConfigReader.getDefaultCategoryColor());
            case "material" -> "§" + ToggleCommand.getColorCode(ConfigReader.getDefaultMaterialColor());
            case "number" -> "§" + ToggleCommand.getColorCode(ConfigReader.getDefaultNumberColor());
            case "time" -> "§" + ToggleCommand.getColorCode(ConfigReader.getDefaultTimeColor());
            default -> "§r";
        };
    }

    public static String getPlayerLanguage(ServerPlayerEntity player) {
        PlayerToggleData data = getPlayerData(player);
        return data != null && data.language != null ? data.language : ConfigReader.getDefaultLanguage();
    }

    private static int getStatCount(ServerPlayerEntity player, String itemName, String statCategory) {
        try {
            Identifier id = Identifier.tryParse(itemName);
            if (id == null) {
                // Try with minecraft: prefix if not present
                id = Identifier.tryParse("minecraft:" + itemName);
                if (id == null) return 0;
            }

            if (statCategory.equals("killed") || statCategory.equals("killed_by")) {
                EntityType<?> entityType = Registries.ENTITY_TYPE.get(id);
                if (entityType != null) {
                    Stat<?> stat = statCategory.equals("killed")
                            ? Stats.KILLED.getOrCreateStat(entityType)
                            : Stats.KILLED_BY.getOrCreateStat(entityType);
                    return player.getStatHandler().getStat(stat);
                }
                return 0;
            }

            Item item = Registries.ITEM.get(id);
            if (item != null) {
                Stat<?> stat = switch (statCategory) {
                    case "mined" -> {
                        Block block = Block.getBlockFromItem(item);
                        yield block != null ? Stats.MINED.getOrCreateStat(block) : null;
                    }
                    case "crafted" -> Stats.CRAFTED.getOrCreateStat(item);
                    case "used" -> Stats.USED.getOrCreateStat(item);
                    case "broken" -> Stats.BROKEN.getOrCreateStat(item);
                    case "picked_up" -> Stats.PICKED_UP.getOrCreateStat(item);
                    case "dropped" -> Stats.DROPPED.getOrCreateStat(item);
                    default -> null;
                };
                return stat != null ? player.getStatHandler().getStat(stat) : 0;
            }

            Block block = Registries.BLOCK.get(id);
            if (block != null && statCategory.equals("mined")) {
                return player.getStatHandler().getStat(Stats.MINED.getOrCreateStat(block));
            }

            return 0;
        } catch (Exception e) {
            System.err.println("Error getting stat count for " + itemName + ": " + e.getMessage());
            return 0;
        }
    }

    public static boolean hasActiveToggle(ServerPlayerEntity player) {
        PlayerToggleData data = getPlayerData(player);
        if (data == null || data.toggleItem == null || data.location == null) {
            return false;
        }

        if (!ToggleCommand.isToggleAllowed(data.toggleItem)) {
            return false;
        }

        // Special case for timeplayed
        if (data.toggleItem.equals("timeplayed")) {
            return data.location.equals("actionbar");
        }

        // For other toggles, ensure we have a valid stat category
        if (data.statCategory == null || !Arrays.asList(ToggleCommand.STAT_CATEGORIES).contains(data.statCategory)) {
            return false;
        }

        // Check if the item/block/entity exists
        try {
            Identifier id = Identifier.tryParse(data.toggleItem);
            if (id == null) return false;

            if (data.statCategory.equals("killed") || data.statCategory.equals("killed_by")) {
                return Registries.ENTITY_TYPE.containsId(id);
            }

            return Registries.ITEM.containsId(id) || Registries.BLOCK.containsId(id);
        } catch (Exception e) {
            return false;
        }
    }

    public static void clearCache(UUID playerId) {
        playerDataCache.remove(playerId);
        lastMilestoneReached.remove(playerId);
    }

    public static PlayerToggleData getPlayerData(ServerPlayerEntity player) {
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
            if (json.has("time_color")) data.timeColor = json.get("time_color").getAsString();
            if (json.has("language")) data.language = json.get("language").getAsString();

            playerDataCache.put(playerId, data);
            return data;
        } catch (IOException | JsonParseException e) {
            System.err.println("Error reading player toggle data: " + e.getMessage());
            return null;
        }
    }
}