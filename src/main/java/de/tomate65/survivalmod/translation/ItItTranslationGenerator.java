package de.tomate65.survivalmod.translation;

import com.google.gson.JsonObject;

public class ItItTranslationGenerator extends TranslationGenerator {
    @Override
    public String getLanguageCode() {
        return "it_it";
    }

    @Override
    public JsonObject generateTranslations() {
        JsonObject translations = new JsonObject();

        // Unità di tempo
        translations.addProperty("time.years", "anni");
        translations.addProperty("time.days", "gg");
        translations.addProperty("time.hours", "h");
        translations.addProperty("time.minutes", "min");
        translations.addProperty("time.seconds", "sec");

        // Nomi oggetti
        translations.addProperty("item.stone.name", "Pietra");
        translations.addProperty("item.dirt.name", "Terra");
        translations.addProperty("item.oak_log.name", "Tronco di quercia");

        // Categorie statistiche
        translations.addProperty("stat.timeplayed", "Tempo di gioco");
        translations.addProperty("stat.mined", "Minato");
        translations.addProperty("stat.crafted", "Creato");
        translations.addProperty("stat.used", "Usato");
        translations.addProperty("stat.broken", "Rotto");
        translations.addProperty("stat.picked_up", "Raccolto");
        translations.addProperty("stat.dropped", "Lasciato");
        translations.addProperty("stat.killed", "Ucciso");
        translations.addProperty("stat.killed_by", "Ucciso da");
        translations.addProperty("stat.custom", "Personalizzato");

        // Sistema di aiuto
        addHelpTranslations(translations);

        // Sistema magnete
        addMagnetTranslations(translations);

        // Comando clear
        addClearCommandTranslations(translations);

        // Comando set
        addSetCommandTranslations(translations);

        return translations;
    }

    private void addHelpTranslations(JsonObject translations) {
        translations.addProperty("help.general.title", "=== Aiuto Comando Toggle ===");
        translations.addProperty("help.general.materials", "/toggle help materials - Mostra categorie materiali disponibili");
        translations.addProperty("help.general.color", "/toggle help color - Mostra aiuto personalizzazione colori");
        translations.addProperty("help.general.clear", "/toggle help clear - Mostra come resettare le impostazioni");
        translations.addProperty("help.general.language", "/toggle help language - Mostra opzioni lingua");
        translations.addProperty("help.general.usage", "Uso: /toggle <materiale> <posizione> [categoria_stat]");

        translations.addProperty("help.materials.title", "=== Categorie Materiali ===");
        translations.addProperty("help.materials.header", "Tipi di materiali disponibili:");
        translations.addProperty("help.materials.mobs", "Mob - Tutte le entità viventi (zombie, creeper, ecc.)");
        translations.addProperty("help.materials.blocks", "Blocchi - Tutti i blocchi piazzabili (pietra, terra, ecc.)");
        translations.addProperty("help.materials.items", "Oggetti - Tutti gli oggetti (spade, cibo, strumenti, ecc.)");
        translations.addProperty("help.materials.all", "Tutto - Tutto ciò disponibile");
        translations.addProperty("help.materials.usage", "Uso: /toggle <id_materiale> <actionbar|chat|titolo> [categoria_stat]");

        translations.addProperty("help.color.title", "=== Personalizzazione Colori ===");
        translations.addProperty("help.color.header", "Cambia colori per diversi elementi di testo:");
        translations.addProperty("help.color.text", "/toggle color text <colore> - Colore testo principale");
        translations.addProperty("help.color.category", "/toggle color category <colore> - Colore categoria statistiche");
        translations.addProperty("help.color.material", "/toggle color material <colore> - Colore nome materiale");
        translations.addProperty("help.color.number", "/toggle color number <colore> - Colore numeri");
        translations.addProperty("help.color.time", "/toggle color time <colore> - Colore visualizzazione tempo");
        translations.addProperty("help.color.colors", "Colori disponibili: ROSSO, VERDE, BLU, GIALLO, ecc.");
        translations.addProperty("help.color.hex", "O usa valori esadecimali: '#FF0000' (rosso), '#00FF00' (verde)");
        translations.addProperty("help.color.none", "Usa 'NONE' per resettare al default");

        translations.addProperty("help.language.title", "=== Opzioni Lingua ===");
        translations.addProperty("help.language.available", "Lingue disponibili:");
        translations.addProperty("help.language.change", "Cambia lingua di visualizzazione:");
        translations.addProperty("help.language.command", "/toggle language <codice_lingua>");
        translations.addProperty("help.language.example", "Esempio: /toggle language it_it");
    }

    private void addMagnetTranslations(JsonObject translations) {
        translations.addProperty("magnet.activated", "Magnete attivato - Attira oggetti in un raggio di %d blocchi");
        translations.addProperty("magnet.deactivated", "Magnete disattivato");
        translations.addProperty("magnet.already_active", "Il magnete è già attivo");
        translations.addProperty("magnet.already_inactive", "Il magnete è già disattivato");
    }

    private void addClearCommandTranslations(JsonObject translations) {
        translations.addProperty("help.clear.title", "=== Resetta Impostazioni ===");
        translations.addProperty("help.clear.header", "Resetta le tue preferenze:");
        translations.addProperty("help.clear.command", "/toggle clear - Resetta TUTTE le impostazioni");
        translations.addProperty("help.clear.partial_command", "/toggle clear <tipo> - Resetta impostazioni specifiche");
        translations.addProperty("help.clear.removes", "Reset completo rimuove:");
        translations.addProperty("help.clear.toggles", "- Le tue impostazioni attuali");
        translations.addProperty("help.clear.colors", "- Tutte le preferenze colore");
        translations.addProperty("help.clear.language", "- Selezione lingua");
        translations.addProperty("help.clear.partial_removes", "Opzioni reset parziale:");
        translations.addProperty("help.clear.partial_colors", "- /toggle clear color - Tutti i colori");
        translations.addProperty("help.clear.partial_color_types", "- /toggle clear color <tipo> - Colore specifico");
        translations.addProperty("help.clear.partial_toggle", "- /toggle clear toggle - Solo impostazioni");
        translations.addProperty("help.clear.partial_language", "- /toggle clear language - Solo lingua");
        translations.addProperty("help.clear.note", "Dovrai riconfigurare tutto dopo il reset.");

        translations.addProperty("command.clear.color.text", "Colore testo resettato");
        translations.addProperty("command.clear.color.category", "Colore categoria resettato");
        translations.addProperty("command.clear.color.material", "Colore materiale resettato");
        translations.addProperty("command.clear.color.number", "Colore numeri resettato");
        translations.addProperty("command.clear.color.time", "Colore tempo resettato");
        translations.addProperty("command.clear.toggle", "Impostazioni resettate");
        translations.addProperty("command.clear.language", "Impostazione lingua resettata");
        translations.addProperty("help.clear.partial", "/toggle clear <tipo> - Resetta impostazioni specifiche");
        translations.addProperty("help.clear.types", "Tipi disponibili: color, toggle, language");
        translations.addProperty("help.clear.color_types", "Tipi colore disponibili: text, category, material, number, time");
    }

    private void addSetCommandTranslations(JsonObject translations) {
        translations.addProperty("command.set.success", "%s impostato su %s");
        translations.addProperty("command.set.usage", "Uso: /toggle set <tipo> <valore>");
        translations.addProperty("command.set.types", "Tipi disponibili: object, location, category");
        translations.addProperty("command.set.invalid_stat_category", "Categoria statistica non valida");
        translations.addProperty("command.set.invalid_location", "Posizione non valida");
        translations.addProperty("command.set.object", "Oggetto impostato su %s");
        translations.addProperty("command.set.location", "Posizione impostata su %s");
        translations.addProperty("command.set.category", "Categoria statistica impostata su %s");
    }
}