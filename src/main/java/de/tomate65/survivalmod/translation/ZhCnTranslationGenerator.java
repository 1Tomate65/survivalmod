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

        // 时间单位
        translations.addProperty("time.years", "年");
        translations.addProperty("time.days", "天");
        translations.addProperty("time.hours", "小时");
        translations.addProperty("time.minutes", "分钟");
        translations.addProperty("time.seconds", "秒");

        // 物品名称
        translations.addProperty("item.stone.name", "石头");
        translations.addProperty("item.dirt.name", "泥土");
        translations.addProperty("item.oak_log.name", "橡木原木");

        // 统计类别
        translations.addProperty("stat.timeplayed", "游戏时间");
        translations.addProperty("stat.mined", "挖掘");
        translations.addProperty("stat.crafted", "合成");
        translations.addProperty("stat.used", "使用");
        translations.addProperty("stat.broken", "破坏");
        translations.addProperty("stat.picked_up", "拾取");
        translations.addProperty("stat.dropped", "丢弃");
        translations.addProperty("stat.killed", "击杀");
        translations.addProperty("stat.killed_by", "被击杀");
        translations.addProperty("stat.custom", "自定义");

        // 帮助系统
        addHelpTranslations(translations);

        // 磁铁系统
        addMagnetTranslations(translations);

        // 清除命令
        addClearCommandTranslations(translations);

        // 设置命令
        addSetCommandTranslations(translations);

        return translations;
    }

    private void addHelpTranslations(JsonObject translations) {
        translations.addProperty("help.general.title", "=== 切换命令帮助 ===");
        translations.addProperty("help.general.materials", "/toggle help materials - 显示可用材料类别");
        translations.addProperty("help.general.color", "/toggle help color - 显示颜色自定义帮助");
        translations.addProperty("help.general.clear", "/toggle help clear - 显示如何重置设置");
        translations.addProperty("help.general.language", "/toggle help language - 显示语言选项");
        translations.addProperty("help.general.usage", "用法: /toggle <材料> <位置> [统计类别]");

        translations.addProperty("help.materials.title", "=== 材料类别 ===");
        translations.addProperty("help.materials.header", "可用材料类型:");
        translations.addProperty("help.materials.mobs", "生物 - 所有活体实体(僵尸、苦力怕等)");
        translations.addProperty("help.materials.blocks", "方块 - 所有可放置方块(石头、泥土等)");
        translations.addProperty("help.materials.items", "物品 - 所有物品(剑、食物、工具等)");
        translations.addProperty("help.materials.all", "全部 - 所有可用内容");
        translations.addProperty("help.materials.usage", "用法: /toggle <材料ID> <动作栏|聊天|标题> [统计类别]");

        translations.addProperty("help.color.title", "=== 颜色自定义 ===");
        translations.addProperty("help.color.header", "更改不同文本元素的颜色:");
        translations.addProperty("help.color.text", "/toggle color text <颜色> - 主文本颜色");
        translations.addProperty("help.color.category", "/toggle color category <颜色> - 统计类别颜色");
        translations.addProperty("help.color.material", "/toggle color material <颜色> - 材料名称颜色");
        translations.addProperty("help.color.number", "/toggle color number <颜色> - 数字颜色");
        translations.addProperty("help.color.time", "/toggle color time <颜色> - 游戏时间显示颜色");
        translations.addProperty("help.color.colors", "颜色可以是: 红色、绿色、蓝色、黄色等");
        translations.addProperty("help.color.hex", "或使用十六进制值: '#FF0000'(红)、'#00FF00'(绿)");
        translations.addProperty("help.color.none", "使用'NONE'重置为默认");

        translations.addProperty("help.language.title", "=== 语言选项 ===");
        translations.addProperty("help.language.available", "可用语言:");
        translations.addProperty("help.language.change", "更改显示语言:");
        translations.addProperty("help.language.command", "/toggle language <语言代码>");
        translations.addProperty("help.language.example", "示例: /toggle language zh_cn");
    }

    private void addMagnetTranslations(JsonObject translations) {
        translations.addProperty("magnet.activated", "磁铁已激活 - 吸引%d格范围内的物品");
        translations.addProperty("magnet.deactivated", "磁铁已停用");
        translations.addProperty("magnet.already_active", "磁铁已经处于激活状态");
        translations.addProperty("magnet.already_inactive", "磁铁已经处于停用状态");
    }

    private void addClearCommandTranslations(JsonObject translations) {
        translations.addProperty("help.clear.title", "=== 重置设置 ===");
        translations.addProperty("help.clear.header", "重置您的偏好设置:");
        translations.addProperty("help.clear.command", "/toggle clear - 重置所有设置");
        translations.addProperty("help.clear.partial_command", "/toggle clear <类型> - 重置特定设置");
        translations.addProperty("help.clear.removes", "完全重置将删除:");
        translations.addProperty("help.clear.toggles", "- 您当前的切换设置");
        translations.addProperty("help.clear.colors", "- 所有颜色偏好");
        translations.addProperty("help.clear.language", "- 语言选择");
        translations.addProperty("help.clear.partial_removes", "部分重置选项:");
        translations.addProperty("help.clear.partial_colors", "- /toggle clear color - 所有颜色");
        translations.addProperty("help.clear.partial_color_types", "- /toggle clear color <类型> - 特定颜色");
        translations.addProperty("help.clear.partial_toggle", "- /toggle clear toggle - 仅切换设置");
        translations.addProperty("help.clear.partial_language", "- /toggle clear language - 仅语言");
        translations.addProperty("help.clear.note", "重置后您需要重新配置所有内容。");

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
        translations.addProperty("command.set.object", "已将对象设置为%s");
        translations.addProperty("command.set.location", "已将位置设置为%s");
        translations.addProperty("command.set.category", "已将统计类别设置为%s");
    }
}