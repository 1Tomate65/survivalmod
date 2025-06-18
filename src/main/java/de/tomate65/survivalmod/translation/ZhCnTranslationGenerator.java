package de.tomate65.survivalmod.translation;

import com.google.gson.JsonObject;

public class ZhCnTranslationGenerator extends TranslationGenerator {
    @Override
    public String getLanguageCode() {
        return "zh_cn";
    }

    @Override
    public JsonObject generateTranslations() {
        JsonObject translations = new JsonObject();

        // Time units
        translations.addProperty("time.years", "年");
        translations.addProperty("time.days", "天");
        translations.addProperty("time.hours", "小时");
        translations.addProperty("time.minutes", "分钟");
        translations.addProperty("time.seconds", "秒");

        // Command feedback
        translations.addProperty("command.disabled", "命令已禁用");
        translations.addProperty("command.player_only", "必须由玩家执行该命令");
        translations.addProperty("command.invalid_language", "无效的语言代码");
        translations.addProperty("command.language_disabled", "语言 %s 目前在配置中已禁用");
        translations.addProperty("command.invalid_toggle", "当前模式(%s)不允许此切换");
        translations.addProperty("command.invalid_hex_color", "无效的十六进制颜色格式。使用RRGGBB或#RRGGBB");
        translations.addProperty("command.error_saving", "保存您的%s首选项时出错");
        translations.addProperty("command.reset_success", "您的切换首选项已重置");
        translations.addProperty("command.reset_failed", "重置切换首选项失败");
        translations.addProperty("command.reset_none", "您没有要重置的切换首选项");
        translations.addProperty("command.reloaded", "配置已重新加载");
        translations.addProperty("command.no_content", "此子命令没有可用内容");
        translations.addProperty("command.no_subcommands", "没有可用的子命令。检查您的配置");
        translations.addProperty("command.available_commands", "可用的子命令:");
        translations.addProperty("command.usage", "用法: %s");
        translations.addProperty("command.available_colors", "可用颜色: %s");
        translations.addProperty("command.available_languages", "可用语言: %s");
        translations.addProperty("command.hex_format", "十六进制格式可以是RRGGBB或#RRGGBB");
        translations.addProperty("command.reset_partial", "已清除您的%s设置");
        translations.addProperty("command.set_usage.title", "=== Set 命令用法 ===");
        translations.addProperty("command.set_usage.object", "/toggle set object <物品ID>");
        translations.addProperty("command.set_usage.location", "/toggle set location <actionbar|chat|title>");
        translations.addProperty("command.set_usage.category", "/toggle set category <统计类别>");

        translations.addProperty("message.action.mined", "挖掘了");
        translations.addProperty("message.action.crafted", "制作了");
        translations.addProperty("message.action.used", "使用了");
        translations.addProperty("message.action.broken", "破坏了");
        translations.addProperty("message.action.picked_up", "拾取了");
        translations.addProperty("message.action.dropped", "丢弃了");
        translations.addProperty("message.action.killed", "杀死了");
        translations.addProperty("message.action.killed_by", "被杀死");
        translations.addProperty("message.action.custom", "达成了里程碑");
        translations.addProperty("message.you", "你");
        translations.addProperty("message.have", "");
        translations.addProperty("message.plural", "");
        translations.addProperty("message.exclamation", "！");

        // Command success messages
        translations.addProperty("timeplayed.set", "已设置游戏时间显示在%s");
        translations.addProperty("language.set", "语言已设置为%s");
        translations.addProperty("color.set", "已将%s颜色设置为%s");
        translations.addProperty("color.reset", "已将%s颜色重置为默认");
        translations.addProperty("toggle.set", "已设置切换%s在%s显示%s统计信息");

        // Color command parts
        translations.addProperty("color.set.part1", "已设置");
        translations.addProperty("color.set.part2", "颜色为");
        translations.addProperty("color.reset.part1", "已重置");
        translations.addProperty("color.reset.part2", "颜色为默认");

        // Item names
        translations.addProperty("item.stone.name", "石头");
        translations.addProperty("item.dirt.name", "泥土");
        translations.addProperty("item.oak_log.name", "橡木原木");

        // Stat categories
        translations.addProperty("stat.timeplayed", "游戏时间");
        translations.addProperty("stat.mined", "挖掘");
        translations.addProperty("stat.crafted", "合成");
        translations.addProperty("stat.used", "使用");
        translations.addProperty("stat.broken", "破坏");
        translations.addProperty("stat.picked_up", "拾取");
        translations.addProperty("stat.dropped", "丢弃");
        translations.addProperty("stat.killed", "击杀");
        translations.addProperty("stat.killed_by", "死于");
        translations.addProperty("stat.custom", "自定义");

        // Help system
        addHelpTranslations(translations);

        // Magnet system
        addMagnetTranslations(translations);

        // Clear command
        addClearCommandTranslations(translations);

        // Set command
        addSetCommandTranslations(translations);

        return translations;
    }

    private void addHelpTranslations(JsonObject translations) {
        translations.addProperty("help.general.title", "=== 切换命令帮助 ===");
        translations.addProperty("help.general.materials", "/toggle help materials - 显示可用材料类别");
        translations.addProperty("help.general.color", "/toggle help color - 显示颜色自定义帮助");
        translations.addProperty("help.general.clear", "/toggle help clear - 显示如何重置您的设置");
        translations.addProperty("help.general.language", "/toggle help language - 显示语言选项");
        translations.addProperty("help.general.usage", "用法: /toggle <材料> <位置> [统计类别]");

        translations.addProperty("help.materials.title", "=== 材料类别 ===");
        translations.addProperty("help.materials.header", "可用材料类型:");
        translations.addProperty("help.materials.mobs", "生物 - 所有活体实体(僵尸、爬行者等)");
        translations.addProperty("help.materials.blocks", "方块 - 所有可放置方块(石头、泥土等)");
        translations.addProperty("help.materials.items", "物品 - 所有物品(剑、食物、工具等)");
        translations.addProperty("help.materials.all", "全部 - 所有可用内容");
        translations.addProperty("help.materials.usage", "用法: /toggle <材料ID> <actionbar|chat|title> [统计类别]");

        translations.addProperty("help.color.title", "=== 颜色自定义 ===");
        translations.addProperty("help.color.header", "更改不同文本元素的颜色:");
        translations.addProperty("help.color.text", "/toggle color text <颜色> - 主要文本颜色");
        translations.addProperty("help.color.category", "/toggle color category <颜色> - 统计类别颜色");
        translations.addProperty("help.color.material", "/toggle color material <颜色> - 材料名称颜色");
        translations.addProperty("help.color.number", "/toggle color number <颜色> - 数字颜色");
        translations.addProperty("help.color.time", "/toggle color time <颜色> - 游戏时间显示颜色");
        translations.addProperty("help.color.colors", "颜色可以是: RED, GREEN, BLUE, YELLOW等");
        translations.addProperty("help.color.hex", "或使用十六进制值: '#FF0000' (红色), '#00FF00' (绿色)");
        translations.addProperty("help.color.none", "使用'NONE'重置为默认");

        translations.addProperty("help.language.title", "=== 语言选项 ===");
        translations.addProperty("help.language.available", "可用语言:");
        translations.addProperty("help.language.change", "更改您的显示语言:");
        translations.addProperty("help.language.command", "/toggle language <语言代码>");
        translations.addProperty("help.language.example", "示例: /toggle language zh_cn");

        translations.addProperty("survivalmod.info.running", "你正在运行生存模组 %s 版本");
        translations.addProperty("survivalmod.info.update_available", "有可用的更新！");
        translations.addProperty("survivalmod.info.no_update", "目前还没有更新。");
        translations.addProperty("survivalmod.info.modrinth_link", "点击此处前往Modrinth页面");
    }

    private void addMagnetTranslations(JsonObject translations) {
        translations.addProperty("magnet.activated", "磁铁已激活 - 在%d方块半径内吸引物品");
        translations.addProperty("magnet.deactivated", "磁铁已停用");
        translations.addProperty("magnet.already_active", "磁铁已经激活");
        translations.addProperty("magnet.already_inactive", "磁铁已经停用");
    }

    private void addClearCommandTranslations(JsonObject translations) {
        translations.addProperty("help.clear.title", "=== 重置设置 ===");
        translations.addProperty("help.clear.header", "重置您的切换首选项:");
        translations.addProperty("help.clear.command", "/toggle clear - 重置所有设置");
        translations.addProperty("help.clear.partial_command", "/toggle clear <类型> - 重置特定设置");
        translations.addProperty("help.clear.removes", "完全清除将移除:");
        translations.addProperty("help.clear.toggles", "- 您当前的切换设置");
        translations.addProperty("help.clear.colors", "- 所有颜色首选项");
        translations.addProperty("help.clear.language", "- 语言选择");
        translations.addProperty("help.clear.partial_removes", "部分清除选项:");
        translations.addProperty("help.clear.partial_colors", "- /toggle clear color - 所有颜色");
        translations.addProperty("help.clear.partial_color_types", "- /toggle clear color <类型> - 特定颜色");
        translations.addProperty("help.clear.partial_toggle", "- /toggle clear toggle - 仅切换设置");
        translations.addProperty("help.clear.partial_language", "- /toggle clear language - 仅语言");
        translations.addProperty("help.clear.note", "清除后您需要重新设置所有内容");

        translations.addProperty("command.clear.color.text", "已清除文本颜色");
        translations.addProperty("command.clear.color.category", "已清除类别颜色");
        translations.addProperty("command.clear.color.material", "已清除材料颜色");
        translations.addProperty("command.clear.color.number", "已清除数字颜色");
        translations.addProperty("command.clear.color.time", "已清除时间颜色");
        translations.addProperty("command.clear.toggle", "已清除切换设置");
        translations.addProperty("command.clear.language", "已清除语言设置");
        translations.addProperty("help.clear.partial", "/toggle clear <类型> - 清除特定设置");
        translations.addProperty("help.clear.types", "可用类型: color, toggle, language");
        translations.addProperty("help.clear.color_types", "可用颜色类型: text, category, material, number, time");
    }

    private void addSetCommandTranslations(JsonObject translations) {
        translations.addProperty("command.set.success", "已将%s设置为%s");
        translations.addProperty("command.set.usage", "用法: /toggle set <类型> <值>");
        translations.addProperty("command.set.types", "可用类型: object, location, category");
        translations.addProperty("command.set.invalid_stat_category", "无效的统计类别");
        translations.addProperty("command.set.invalid_location", "无效的位置");
        translations.addProperty("command.set.object", "对象已设置为%s");
        translations.addProperty("command.set.location", "位置已设置为%s");
        translations.addProperty("command.set.category", "统计类别已设置为%s");
    }
}