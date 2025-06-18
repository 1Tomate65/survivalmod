package de.tomate65.survivalmod.translation;

import com.google.gson.JsonObject;

public class RuRuTranslationGenerator extends TranslationGenerator {
    @Override
    public String getLanguageCode() {
        return "ru_ru";
    }

    @Override
    public JsonObject generateTranslations() {
        JsonObject translations = new JsonObject();

        // Time units
        translations.addProperty("time.years", "г");
        translations.addProperty("time.days", "д");
        translations.addProperty("time.hours", "ч");
        translations.addProperty("time.minutes", "м");
        translations.addProperty("time.seconds", "с");

        // Command feedback
        translations.addProperty("command.disabled", "Команда отключена");
        translations.addProperty("command.player_only", "Команда должна выполняться игроком");
        translations.addProperty("command.invalid_language", "Неверный код языка");
        translations.addProperty("command.language_disabled", "Язык %s в настоящее время отключен в конфигурации");
        translations.addProperty("command.invalid_toggle", "Этот переключатель не разрешен в текущем режиме (%s)");
        translations.addProperty("command.invalid_hex_color", "Неверный формат hex-цвета. Используйте RRGGBB или #RRGGBB");
        translations.addProperty("command.error_saving", "Ошибка сохранения ваших настроек %s");
        translations.addProperty("command.reset_success", "Ваши настройки переключателей сброшены");
        translations.addProperty("command.reset_failed", "Не удалось сбросить настройки переключателей");
        translations.addProperty("command.reset_none", "У вас не было настроек переключателей для сброса");
        translations.addProperty("command.reloaded", "Конфигурация перезагружена");
        translations.addProperty("command.no_content", "Нет содержимого для этой подкоманды");
        translations.addProperty("command.no_subcommands", "Нет доступных подкоманд. Проверьте конфигурацию");
        translations.addProperty("command.available_commands", "Доступные подкоманды:");
        translations.addProperty("command.usage", "Использование: %s");
        translations.addProperty("command.available_colors", "Доступные цвета: %s");
        translations.addProperty("command.available_languages", "Доступные языки: %s");
        translations.addProperty("command.hex_format", "Hex-формат может быть RRGGBB или #RRGGBB");
        translations.addProperty("command.reset_partial", "Ваши настройки %s очищены");
        translations.addProperty("command.set_usage.title", "=== Использование команды Set ===");
        translations.addProperty("command.set_usage.object", "/toggle set object <ID_объекта>");
        translations.addProperty("command.set_usage.location", "/toggle set location <actionbar|chat|title>");
        translations.addProperty("command.set_usage.category", "/toggle set category <категория_статистики>");

        translations.addProperty("message.action.mined", "добыл(а)");
        translations.addProperty("message.action.crafted", "создал(а)");
        translations.addProperty("message.action.used", "использовал(а)");
        translations.addProperty("message.action.broken", "сломал(а)");
        translations.addProperty("message.action.picked_up", "подобрал(а)");
        translations.addProperty("message.action.dropped", "выбросил(а)");
        translations.addProperty("message.action.killed", "убил(а)");
        translations.addProperty("message.action.killed_by", "был(а) убит(а)");
        translations.addProperty("message.action.custom", "достиг(ла) результата в");
        translations.addProperty("message.you", "Ты");
        translations.addProperty("message.have", "");
        translations.addProperty("message.plural", "");
        translations.addProperty("message.exclamation", "!");

        // Command success messages
        translations.addProperty("timeplayed.set", "Время игры установлено для отображения в %s");
        translations.addProperty("language.set", "Язык установлен на %s");
        translations.addProperty("color.set", "Цвет %s установлен на %s");
        translations.addProperty("color.reset", "Цвет %s сброшен на стандартный");
        translations.addProperty("toggle.set", "Переключатель %s установлен для отображения в %s со статистикой %s");

        // Color command parts
        translations.addProperty("color.set.part1", "Установлен ");
        translations.addProperty("color.set.part2", " цвет на ");
        translations.addProperty("color.reset.part1", "Сброшен ");
        translations.addProperty("color.reset.part2", " цвет на стандартный");

        // Item names
        translations.addProperty("item.stone.name", "Камень");
        translations.addProperty("item.dirt.name", "Земля");
        translations.addProperty("item.oak_log.name", "Дубовое бревно");

        // Stat categories
        translations.addProperty("stat.timeplayed", "Время игры");
        translations.addProperty("stat.mined", "Добыто");
        translations.addProperty("stat.crafted", "Создано");
        translations.addProperty("stat.used", "Использовано");
        translations.addProperty("stat.broken", "Сломано");
        translations.addProperty("stat.picked_up", "Подобрано");
        translations.addProperty("stat.dropped", "Выброшено");
        translations.addProperty("stat.killed", "Убито");
        translations.addProperty("stat.killed_by", "Убит");
        translations.addProperty("stat.custom", "Пользовательский");

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
        translations.addProperty("help.general.title", "=== Помощь по команде Toggle ===");
        translations.addProperty("help.general.materials", "/toggle help materials - Показать доступные категории материалов");
        translations.addProperty("help.general.color", "/toggle help color - Показать помощь по настройке цветов");
        translations.addProperty("help.general.clear", "/toggle help clear - Показать как сбросить настройки");
        translations.addProperty("help.general.language", "/toggle help language - Показать языковые опции");
        translations.addProperty("help.general.usage", "Использование: /toggle <материал> <место> [категория_статистики]");

        translations.addProperty("help.materials.title", "=== Категории материалов ===");
        translations.addProperty("help.materials.header", "Доступные типы материалов:");
        translations.addProperty("help.materials.mobs", "Мобы - Все живые существа (зомби, криперы и т.д.)");
        translations.addProperty("help.materials.blocks", "Блоки - Все размещаемые блоки (камень, земля и т.д.)");
        translations.addProperty("help.materials.items", "Предметы - Все предметы (мечи, еда, инструменты и т.д.)");
        translations.addProperty("help.materials.all", "Все - Все доступное");
        translations.addProperty("help.materials.usage", "Использование: /toggle <id_материала> <actionbar|chat|title> [категория_статистики]");

        translations.addProperty("help.color.title", "=== Настройка цветов ===");
        translations.addProperty("help.color.header", "Изменить цвета для разных элементов текста:");
        translations.addProperty("help.color.text", "/toggle color text <цвет> - Основной цвет текста");
        translations.addProperty("help.color.category", "/toggle color category <цвет> - Цвет категории статистики");
        translations.addProperty("help.color.material", "/toggle color material <цвет> - Цвет названия материала");
        translations.addProperty("help.color.number", "/toggle color number <цвет> - Цвет чисел");
        translations.addProperty("help.color.time", "/toggle color time <цвет> - Цвет отображения времени игры");
        translations.addProperty("help.color.colors", "Цвета могут быть: RED, GREEN, BLUE, YELLOW и т.д.");
        translations.addProperty("help.color.hex", "Или используйте hex-значения: '#FF0000' (красный), '#00FF00' (зеленый)");
        translations.addProperty("help.color.none", "Используйте 'NONE' для сброса на стандартный");

        translations.addProperty("help.language.title", "=== Языковые опции ===");
        translations.addProperty("help.language.available", "Доступные языки:");
        translations.addProperty("help.language.change", "Изменить язык отображения:");
        translations.addProperty("help.language.command", "/toggle language <код_языка>");
        translations.addProperty("help.language.example", "Пример: /toggle language ru_ru");

        translations.addProperty("survivalmod.info.running", "Вы используете %s мода Survival Mod");
        translations.addProperty("survivalmod.info.update_available", "Доступно обновление!");
        translations.addProperty("survivalmod.info.no_update", "Обновлений пока нет.");
        translations.addProperty("survivalmod.info.modrinth_link", "Нажмите здесь, чтобы перейти на страницу Modrinth");
    }

    private void addMagnetTranslations(JsonObject translations) {
        translations.addProperty("magnet.activated", "Магнит активирован - Притягивает предметы в радиусе %d блоков");
        translations.addProperty("magnet.deactivated", "Магнит деактивирован");
        translations.addProperty("magnet.already_active", "Магнит уже активен");
        translations.addProperty("magnet.already_inactive", "Магнит уже неактивен");
    }

    private void addClearCommandTranslations(JsonObject translations) {
        translations.addProperty("help.clear.title", "=== Сброс настроек ===");
        translations.addProperty("help.clear.header", "Сбросить ваши настройки переключателей:");
        translations.addProperty("help.clear.command", "/toggle clear - Сбросить ВСЕ настройки");
        translations.addProperty("help.clear.partial_command", "/toggle clear <тип> - Сбросить конкретные настройки");
        translations.addProperty("help.clear.removes", "Полный сброс удаляет:");
        translations.addProperty("help.clear.toggles", "- Ваши текущие настройки переключателей");
        translations.addProperty("help.clear.colors", "- Все цветовые настройки");
        translations.addProperty("help.clear.language", "- Выбор языка");
        translations.addProperty("help.clear.partial_removes", "Опции частичного сброса:");
        translations.addProperty("help.clear.partial_colors", "- /toggle clear color - Все цвета");
        translations.addProperty("help.clear.partial_color_types", "- /toggle clear color <тип> - Конкретный цвет");
        translations.addProperty("help.clear.partial_toggle", "- /toggle clear toggle - Только настройки переключателей");
        translations.addProperty("help.clear.partial_language", "- /toggle clear language - Только язык");
        translations.addProperty("help.clear.note", "Вам нужно будет перенастроить все после сброса");

        translations.addProperty("command.clear.color.text", "Цвет текста сброшен");
        translations.addProperty("command.clear.color.category", "Цвет категории сброшен");
        translations.addProperty("command.clear.color.material", "Цвет материала сброшен");
        translations.addProperty("command.clear.color.number", "Цвет чисел сброшен");
        translations.addProperty("command.clear.color.time", "Цвет времени сброшен");
        translations.addProperty("command.clear.toggle", "Настройки переключателей сброшены");
        translations.addProperty("command.clear.language", "Языковая настройка сброшена");
        translations.addProperty("help.clear.partial", "/toggle clear <тип> - Сбросить конкретные настройки");
        translations.addProperty("help.clear.types", "Доступные типы: color, toggle, language");
        translations.addProperty("help.clear.color_types", "Доступные типы цветов: text, category, material, number, time");
    }

    private void addSetCommandTranslations(JsonObject translations) {
        translations.addProperty("command.set.success", "%s установлен на %s");
        translations.addProperty("command.set.usage", "Использование: /toggle set <тип> <значение>");
        translations.addProperty("command.set.types", "Доступные типы: object, location, category");
        translations.addProperty("command.set.invalid_stat_category", "Неверная категория статистики");
        translations.addProperty("command.set.invalid_location", "Неверное местоположение");
        translations.addProperty("command.set.object", "Объект установлен на %s");
        translations.addProperty("command.set.location", "Местоположение установлено на %s");
        translations.addProperty("command.set.category", "Категория статистики установлена на %s");
    }
}