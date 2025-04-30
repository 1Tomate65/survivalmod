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

        // Единицы времени
        translations.addProperty("time.years", "г");
        translations.addProperty("time.days", "д");
        translations.addProperty("time.hours", "ч");
        translations.addProperty("time.minutes", "мин");
        translations.addProperty("time.seconds", "сек");

        // Названия предметов
        translations.addProperty("item.stone.name", "Камень");
        translations.addProperty("item.dirt.name", "Земля");
        translations.addProperty("item.oak_log.name", "Дубовое бревно");

        // Категории статистики
        translations.addProperty("stat.timeplayed", "Время игры");
        translations.addProperty("stat.mined", "Добыто");
        translations.addProperty("stat.crafted", "Создано");
        translations.addProperty("stat.used", "Использовано");
        translations.addProperty("stat.broken", "Сломано");
        translations.addProperty("stat.picked_up", "Подобрано");
        translations.addProperty("stat.dropped", "Выброшено");
        translations.addProperty("stat.killed", "Убито");
        translations.addProperty("stat.killed_by", "Убит");
        translations.addProperty("stat.custom", "Своё");

        // Система помощи
        addHelpTranslations(translations);

        // Система магнита
        addMagnetTranslations(translations);

        // Команда clear
        addClearCommandTranslations(translations);

        // Команда set
        addSetCommandTranslations(translations);

        return translations;
    }

    private void addHelpTranslations(JsonObject translations) {
        translations.addProperty("help.general.title", "=== Помощь по команде Toggle ===");
        translations.addProperty("help.general.materials", "/toggle help materials - Показать доступные категории материалов");
        translations.addProperty("help.general.color", "/toggle help color - Помощь по настройке цветов");
        translations.addProperty("help.general.clear", "/toggle help clear - Как сбросить настройки");
        translations.addProperty("help.general.language", "/toggle help language - Языковые настройки");
        translations.addProperty("help.general.usage", "Использование: /toggle <материал> <место> [категория_стат]");

        translations.addProperty("help.materials.title", "=== Категории материалов ===");
        translations.addProperty("help.materials.header", "Доступные типы материалов:");
        translations.addProperty("help.materials.mobs", "Мобы - Все живые существа (зомби, криперы и т.д.)");
        translations.addProperty("help.materials.blocks", "Блоки - Все размещаемые блоки (камень, земля и т.д.)");
        translations.addProperty("help.materials.items", "Предметы - Все предметы (мечи, еда, инструменты и т.д.)");
        translations.addProperty("help.materials.all", "Все - Всё доступное");
        translations.addProperty("help.materials.usage", "Использование: /toggle <ид_материала> <панель|чат|заголовок> [категория_стат]");

        translations.addProperty("help.color.title", "=== Настройка цветов ===");
        translations.addProperty("help.color.header", "Изменение цветов для элементов текста:");
        translations.addProperty("help.color.text", "/toggle color text <цвет> - Цвет основного текста");
        translations.addProperty("help.color.category", "/toggle color category <цвет> - Цвет категории статистики");
        translations.addProperty("help.color.material", "/toggle color material <цвет> - Цвет названия материала");
        translations.addProperty("help.color.number", "/toggle color number <цвет> - Цвет чисел");
        translations.addProperty("help.color.time", "/toggle color time <цвет> - Цвет отображения времени");
        translations.addProperty("help.color.colors", "Цвета: КРАСНЫЙ, ЗЕЛЁНЫЙ, СИНИЙ, ЖЁЛТЫЙ и т.д.");
        translations.addProperty("help.color.hex", "Или HEX-значения: '#FF0000' (красный), '#00FF00' (зелёный)");
        translations.addProperty("help.color.none", "Используйте 'NONE' для сброса");

        translations.addProperty("help.language.title", "=== Языковые настройки ===");
        translations.addProperty("help.language.available", "Доступные языки:");
        translations.addProperty("help.language.change", "Изменить язык интерфейса:");
        translations.addProperty("help.language.command", "/toggle language <код_языка>");
        translations.addProperty("help.language.example", "Пример: /toggle language ru_ru");
    }

    private void addMagnetTranslations(JsonObject translations) {
        translations.addProperty("magnet.activated", "Магнит активирован - Притягивает предметы в радиусе %d блоков");
        translations.addProperty("magnet.deactivated", "Магнит деактивирован");
        translations.addProperty("magnet.already_active", "Магнит уже активен");
        translations.addProperty("magnet.already_inactive", "Магнит уже неактивен");
    }

    private void addClearCommandTranslations(JsonObject translations) {
        translations.addProperty("help.clear.title", "=== Сброс настроек ===");
        translations.addProperty("help.clear.header", "Сбросить ваши настройки:");
        translations.addProperty("help.clear.command", "/toggle clear - Сбросить ВСЕ настройки");
        translations.addProperty("help.clear.partial_command", "/toggle clear <тип> - Сбросить определённые настройки");
        translations.addProperty("help.clear.removes", "Полный сброс удаляет:");
        translations.addProperty("help.clear.toggles", "- Ваши текущие настройки");
        translations.addProperty("help.clear.colors", "- Все цветовые настройки");
        translations.addProperty("help.clear.language", "- Выбор языка");
        translations.addProperty("help.clear.partial_removes", "Частичный сброс:");
        translations.addProperty("help.clear.partial_colors", "- /toggle clear color - Все цвета");
        translations.addProperty("help.clear.partial_color_types", "- /toggle clear color <тип> - Конкретный цвет");
        translations.addProperty("help.clear.partial_toggle", "- /toggle clear toggle - Только настройки");
        translations.addProperty("help.clear.partial_language", "- /toggle clear language - Только язык");
        translations.addProperty("help.clear.note", "После сброса нужно будет настроить всё заново.");

        translations.addProperty("command.clear.color.text", "Цвет текста сброшен");
        translations.addProperty("command.clear.color.category", "Цвет категории сброшен");
        translations.addProperty("command.clear.color.material", "Цвет материала сброшен");
        translations.addProperty("command.clear.color.number", "Цвет чисел сброшен");
        translations.addProperty("command.clear.color.time", "Цвет времени сброшен");
        translations.addProperty("command.clear.toggle", "Настройки сброшены");
        translations.addProperty("command.clear.language", "Язык сброшен");
        translations.addProperty("help.clear.partial", "/toggle clear <тип> - Очистить определённые настройки");
        translations.addProperty("help.clear.types", "Доступные типы: color, toggle, language");
        translations.addProperty("help.clear.color_types", "Доступные цветовые типы: text, category, material, number, time");
    }

    private void addSetCommandTranslations(JsonObject translations) {
        translations.addProperty("command.set.success", "%s установлено на %s");
        translations.addProperty("command.set.usage", "Использование: /toggle set <тип> <значение>");
        translations.addProperty("command.set.types", "Доступные типы: object, location, category");
        translations.addProperty("command.set.invalid_stat_category", "Неверная категория статистики");
        translations.addProperty("command.set.invalid_location", "Неверное местоположение");
        translations.addProperty("command.set.object", "Объект установлен на %s");
        translations.addProperty("command.set.location", "Местоположение установлено на %s");
        translations.addProperty("command.set.category", "Категория статистики установлена на %s");
    }
}