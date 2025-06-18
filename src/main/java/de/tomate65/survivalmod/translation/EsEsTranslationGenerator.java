package de.tomate65.survivalmod.translation;

import com.google.gson.JsonObject;

public class EsEsTranslationGenerator extends TranslationGenerator {
    @Override
    public String getLanguageCode() {
        return "es_es";
    }

    @Override
    public JsonObject generateTranslations() {
        JsonObject translations = new JsonObject();

        // Time units
        translations.addProperty("time.years", "a");
        translations.addProperty("time.days", "d");
        translations.addProperty("time.hours", "h");
        translations.addProperty("time.minutes", "m");
        translations.addProperty("time.seconds", "s");

        // Command feedback
        translations.addProperty("command.disabled", "Comando desactivado");
        translations.addProperty("command.player_only", "El comando debe ser ejecutado por un jugador");
        translations.addProperty("command.language_disabled", "El idioma %s está desactivado en la configuración");
        translations.addProperty("command.invalid_language", "Código de idioma inválido");
        translations.addProperty("command.invalid_toggle", "Este toggle no está permitido en el modo actual (%s)");
        translations.addProperty("command.invalid_hex_color", "Formato de color hexadecimal inválido. Usa RRGGBB o #RRGGBB");
        translations.addProperty("command.error_saving", "Error al guardar tu preferencia %s.");
        translations.addProperty("command.reset_success", "Tus preferencias de toggle han sido reiniciadas.");
        translations.addProperty("command.reset_failed", "Error al reiniciar tus preferencias de toggle.");
        translations.addProperty("command.reset_none", "No tenías preferencias de toggle para reiniciar.");
        translations.addProperty("command.reloaded", "Configuración recargada.");
        translations.addProperty("command.no_content", "No hay contenido disponible para este subcomando.");
        translations.addProperty("command.no_subcommands", "No hay subcomandos disponibles. Revisa tu configuración.");
        translations.addProperty("command.available_commands", "Subcomandos disponibles:");
        translations.addProperty("command.usage", "Uso: %s");
        translations.addProperty("command.available_colors", "Colores disponibles: %s");
        translations.addProperty("command.available_languages", "Idiomas disponibles: %s");
        translations.addProperty("command.hex_format", "El formato hexadecimal puede ser RRGGBB o #RRGGBB");
        translations.addProperty("command.reset_partial", "Borradas tus configuraciones %s");
        translations.addProperty("command.set_usage.title", "=== Uso del Comando Set ===");
        translations.addProperty("command.set_usage.object", "/toggle set object <id_objeto>");
        translations.addProperty("command.set_usage.location", "/toggle set location <actionbar|chat|title>");
        translations.addProperty("command.set_usage.category", "/toggle set category <categoría_estadística>");

        translations.addProperty("message.action.mined", "minado");
        translations.addProperty("message.action.crafted", "fabricado");
        translations.addProperty("message.action.used", "usado");
        translations.addProperty("message.action.broken", "roto");
        translations.addProperty("message.action.picked_up", "recogido");
        translations.addProperty("message.action.dropped", "soltado");
        translations.addProperty("message.action.killed", "matado a");
        translations.addProperty("message.action.killed_by", "sido matado por");
        translations.addProperty("message.action.custom", "alcanzado un hito en");
        translations.addProperty("message.you", "Has");
        translations.addProperty("message.have", "");
        translations.addProperty("message.plural", "s");
        translations.addProperty("message.exclamation", "!");

        // Command success messages
        translations.addProperty("timeplayed.set", "Establecer tiempo jugado para mostrar en %s");
        translations.addProperty("language.set", "Establecer idioma a %s");
        translations.addProperty("color.set", "Establecer color %s a %s");
        translations.addProperty("color.reset", "Reiniciar color %s a predeterminado");
        translations.addProperty("toggle.set", "Establecer toggle %s para mostrar en %s con estadísticas %s");

        // Color command parts
        translations.addProperty("color.set.part1", "Establecer ");
        translations.addProperty("color.set.part2", " color a ");
        translations.addProperty("color.reset.part1", "Reiniciar ");
        translations.addProperty("color.reset.part2", " color a predeterminado");

        // Item names
        translations.addProperty("item.stone.name", "Piedra");
        translations.addProperty("item.dirt.name", "Tierra");
        translations.addProperty("item.oak_log.name", "Tronco de roble");

        // Stat categories
        translations.addProperty("stat.timeplayed", "Tiempo jugado");
        translations.addProperty("stat.mined", "Minado");
        translations.addProperty("stat.crafted", "Fabricado");
        translations.addProperty("stat.used", "Usado");
        translations.addProperty("stat.broken", "Roto");
        translations.addProperty("stat.picked_up", "Recogido");
        translations.addProperty("stat.dropped", "Tirado");
        translations.addProperty("stat.killed", "Matado");
        translations.addProperty("stat.killed_by", "Matado por");
        translations.addProperty("stat.custom", "Personalizado");

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
        translations.addProperty("help.general.title", "=== Ayuda del comando Toggle ===");
        translations.addProperty("help.general.materials", "/toggle help materials - Mostrar categorías de materiales disponibles");
        translations.addProperty("help.general.color", "/toggle help color - Mostrar ayuda para personalización de colores");
        translations.addProperty("help.general.clear", "/toggle help clear - Mostrar cómo reiniciar tus configuraciones");
        translations.addProperty("help.general.language", "/toggle help language - Mostrar opciones de idioma");
        translations.addProperty("help.general.usage", "Uso: /toggle <material> <ubicación> [categoría_estadística]");

        translations.addProperty("help.materials.title", "=== Categorías de materiales ===");
        translations.addProperty("help.materials.header", "Tipos de materiales disponibles:");
        translations.addProperty("help.materials.mobs", "Mobs - Todas las entidades vivas (zombies, creepers, etc.)");
        translations.addProperty("help.materials.blocks", "Bloques - Todos los bloques colocables (piedra, tierra, etc.)");
        translations.addProperty("help.materials.items", "Objetos - Todos los items (espadas, comida, herramientas, etc.)");
        translations.addProperty("help.materials.all", "Todo - Todo lo disponible");
        translations.addProperty("help.materials.usage", "Uso: /toggle <id_material> <actionbar|chat|title> [categoría_estadística]");

        translations.addProperty("help.color.title", "=== Personalización de colores ===");
        translations.addProperty("help.color.header", "Cambia colores para diferentes elementos de texto:");
        translations.addProperty("help.color.text", "/toggle color text <color> - Color del texto principal");
        translations.addProperty("help.color.category", "/toggle color category <color> - Color de la categoría de estadísticas");
        translations.addProperty("help.color.material", "/toggle color material <color> - Color del nombre del material");
        translations.addProperty("help.color.number", "/toggle color number <color> - Color de los números");
        translations.addProperty("help.color.time", "/toggle color time <color> - Color del tiempo de juego");
        translations.addProperty("help.color.colors", "Colores pueden ser: ROJO, VERDE, AZUL, AMARILLO, etc.");
        translations.addProperty("help.color.hex", "O usa valores hexadecimales: '#FF0000' (rojo), '#00FF00' (verde)");
        translations.addProperty("help.color.none", "Usa 'NONE' para reiniciar al predeterminado");

        translations.addProperty("help.language.title", "=== Opciones de idioma ===");
        translations.addProperty("help.language.available", "Idiomas disponibles:");
        translations.addProperty("help.language.change", "Cambia tu idioma de visualización:");
        translations.addProperty("help.language.command", "/toggle language <código_idioma>");
        translations.addProperty("help.language.example", "Ejemplo: /toggle language es_es");

        translations.addProperty("survivalmod.info.running", "Estás usando %s del Survival Mod");
        translations.addProperty("survivalmod.info.update_available", "¡Hay una actualización disponible!");
        translations.addProperty("survivalmod.info.no_update", "No hay actualizaciones todavía.");
        translations.addProperty("survivalmod.info.modrinth_link", "Haz clic aquí para ir a la página de Modrinth");
    }

    private void addMagnetTranslations(JsonObject translations) {
        translations.addProperty("magnet.activated", "Imán activado - Atrayendo items en un radio de %d bloques");
        translations.addProperty("magnet.deactivated", "Imán desactivado");
        translations.addProperty("magnet.already_active", "El imán ya está activo");
        translations.addProperty("magnet.already_inactive", "El imán ya está inactivo");
    }

    private void addClearCommandTranslations(JsonObject translations) {
        translations.addProperty("help.clear.title", "=== Reiniciar configuraciones ===");
        translations.addProperty("help.clear.header", "Reinicia todas tus preferencias de toggle:");
        translations.addProperty("help.clear.command", "/toggle clear");
        translations.addProperty("help.clear.removes", "Esto eliminará:");
        translations.addProperty("help.clear.toggles", "- Tus configuraciones actuales de toggle");
        translations.addProperty("help.clear.colors", "- Todas las preferencias de color");
        translations.addProperty("help.clear.language", "- Selección de idioma");
        translations.addProperty("help.clear.note", "Necesitarás configurar todo de nuevo después de reiniciar.");
        translations.addProperty("help.clear.partial_command", "/toggle clear <tipo> - Reinicia configuraciones específicas");
        translations.addProperty("help.clear.partial_removes", "Opciones de reinicio parcial:");
        translations.addProperty("help.clear.partial_colors", "- /toggle clear color - Todos los colores");
        translations.addProperty("help.clear.partial_color_types", "- /toggle clear color <tipo> - Un color específico");
        translations.addProperty("help.clear.partial_toggle", "- /toggle clear toggle - Solo las configuraciones de toggle");
        translations.addProperty("help.clear.partial_language", "- /toggle clear language - Solo el idioma");
        translations.addProperty("help.clear.partial", "/toggle clear <tipo> - Borrar configuraciones específicas");
        translations.addProperty("command.clear.color.text", "Color de texto borrado");
        translations.addProperty("command.clear.color.category", "Color de categoría borrado");
        translations.addProperty("command.clear.color.material", "Color de material borrado");
        translations.addProperty("command.clear.color.number", "Color de números borrado");
        translations.addProperty("command.clear.color.time", "Color de tiempo borrado");
        translations.addProperty("command.clear.toggle", "Configuraciones de toggle borradas");
        translations.addProperty("command.clear.language", "Configuración de idioma borrada");
        translations.addProperty("help.clear.types", "Tipos disponibles: color, toggle, language");
        translations.addProperty("help.clear.color_types", "Tipos de color disponibles: text, category, material, number, time");
    }

    private void addSetCommandTranslations(JsonObject translations) {
        translations.addProperty("command.set.success", "Establecer %s a %s");
        translations.addProperty("command.set.usage", "Uso: /toggle set <tipo> <valor>");
        translations.addProperty("command.set.types", "Tipos disponibles: object, location, category");
        translations.addProperty("command.set.invalid_stat_category", "Categoría de estadística inválida");
        translations.addProperty("command.set.invalid_location", "Ubicación inválida");
        translations.addProperty("command.set.object", "Establecer objeto a %s");
        translations.addProperty("command.set.location", "Establecer ubicación a %s");
        translations.addProperty("command.set.category", "Establecer categoría de estadística a %s");
    }
}