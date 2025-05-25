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

        // Time units
        translations.addProperty("time.years", "anni");
        translations.addProperty("time.days", "giorni");
        translations.addProperty("time.hours", "ore");
        translations.addProperty("time.minutes", "minuti");
        translations.addProperty("time.seconds", "secondi");

        // Command feedback
        translations.addProperty("command.disabled", "Comando disabilitato");
        translations.addProperty("command.player_only", "Il comando deve essere eseguito da un giocatore");
        translations.addProperty("command.invalid_language", "Codice lingua non valido");
        translations.addProperty("command.language_disabled", "La lingua %s è attualmente disabilitata nella configurazione");
        translations.addProperty("command.invalid_toggle", "Questo toggle non è consentito nella modalità corrente (%s)");
        translations.addProperty("command.invalid_hex_color", "Formato colore esadecimale non valido. Usa RRGGBB o #RRGGBB");
        translations.addProperty("command.error_saving", "Errore nel salvare la tua preferenza %s");
        translations.addProperty("command.reset_success", "Le tue preferenze toggle sono state resettate");
        translations.addProperty("command.reset_failed", "Impossibile resettare le tue preferenze toggle");
        translations.addProperty("command.reset_none", "Non avevi preferenze toggle da resettare");
        translations.addProperty("command.reloaded", "Configurazione ricaricata");
        translations.addProperty("command.no_content", "Nessun contenuto disponibile per questo sottocomando");
        translations.addProperty("command.no_subcommands", "Nessun sottocomando disponibile. Controlla la tua configurazione");
        translations.addProperty("command.available_commands", "Sottocomandi disponibili:");
        translations.addProperty("command.usage", "Uso: %s");
        translations.addProperty("command.available_colors", "Colori disponibili: %s");
        translations.addProperty("command.available_languages", "Lingue disponibili: %s");
        translations.addProperty("command.hex_format", "Il formato esadecimale può essere RRGGBB o #RRGGBB");
        translations.addProperty("command.reset_partial", "Cancellate le tue impostazioni %s");

        // Command success messages
        translations.addProperty("timeplayed.set", "Tempo di gioco impostato per visualizzare in %s");
        translations.addProperty("language.set", "Lingua impostata su %s");
        translations.addProperty("color.set", "Colore %s impostato su %s");
        translations.addProperty("color.reset", "Colore %s reimpostato su default");
        translations.addProperty("toggle.set", "Toggle %s impostato per visualizzare in %s con statistiche %s");

        // Color command parts
        translations.addProperty("color.set.part1", "Impostato ");
        translations.addProperty("color.set.part2", " colore su ");
        translations.addProperty("color.reset.part1", "Reimpostato ");
        translations.addProperty("color.reset.part2", " colore su default");

        // Item names
        translations.addProperty("item.stone.name", "Pietra");
        translations.addProperty("item.dirt.name", "Terra");
        translations.addProperty("item.oak_log.name", "Tronco di quercia");

        // Stat categories
        translations.addProperty("stat.timeplayed", "Tempo di gioco");
        translations.addProperty("stat.mined", "Minato");
        translations.addProperty("stat.crafted", "Creato");
        translations.addProperty("stat.used", "Usato");
        translations.addProperty("stat.broken", "Rotto");
        translations.addProperty("stat.picked_up", "Raccolto");
        translations.addProperty("stat.dropped", "Lasciato cadere");
        translations.addProperty("stat.killed", "Ucciso");
        translations.addProperty("stat.killed_by", "Ucciso da");
        translations.addProperty("stat.custom", "Personalizzato");

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
        translations.addProperty("help.general.title", "=== Aiuto Comando Toggle ===");
        translations.addProperty("help.general.materials", "/toggle help materials - Mostra categorie materiali disponibili");
        translations.addProperty("help.general.color", "/toggle help color - Mostra aiuto personalizzazione colori");
        translations.addProperty("help.general.clear", "/toggle help clear - Mostra come resettare le tue impostazioni");
        translations.addProperty("help.general.language", "/toggle help language - Mostra opzioni lingua");
        translations.addProperty("help.general.usage", "Uso: /toggle <materiale> <posizione> [categoria_statistiche]");

        translations.addProperty("help.materials.title", "=== Categorie Materiali ===");
        translations.addProperty("help.materials.header", "Tipi di materiali disponibili:");
        translations.addProperty("help.materials.mobs", "Mob - Tutte le entità viventi (zombie, creeper, ecc.)");
        translations.addProperty("help.materials.blocks", "Blocchi - Tutti i blocchi posizionabili (pietra, terra, ecc.)");
        translations.addProperty("help.materials.items", "Oggetti - Tutti gli oggetti (spade, cibo, strumenti, ecc.)");
        translations.addProperty("help.materials.all", "Tutto - Tutto ciò che è disponibile");
        translations.addProperty("help.materials.usage", "Uso: /toggle <id_materiale> <actionbar|chat|title> [categoria_statistiche]");

        translations.addProperty("help.color.title", "=== Personalizzazione Colori ===");
        translations.addProperty("help.color.header", "Cambia colori per diversi elementi di testo:");
        translations.addProperty("help.color.text", "/toggle color text <colore> - Colore testo principale");
        translations.addProperty("help.color.category", "/toggle color category <colore> - Colore categoria statistiche");
        translations.addProperty("help.color.material", "/toggle color material <colore> - Colore nome materiale");
        translations.addProperty("help.color.number", "/toggle color number <colore> - Colore numeri");
        translations.addProperty("help.color.time", "/toggle color time <colore> - Colore visualizzazione tempo di gioco");
        translations.addProperty("help.color.colors", "Colori possono essere: RED, GREEN, BLUE, YELLOW, ecc.");
        translations.addProperty("help.color.hex", "Oppure usa valori esadecimali: '#FF0000' (rosso), '#00FF00' (verde)");
        translations.addProperty("help.color.none", "Usa 'NONE' per resettare al default");

        translations.addProperty("help.language.title", "=== Opzioni Lingua ===");
        translations.addProperty("help.language.available", "Lingue disponibili:");
        translations.addProperty("help.language.change", "Cambia la tua lingua di visualizzazione:");
        translations.addProperty("help.language.command", "/toggle language <codice_lingua>");
        translations.addProperty("help.language.example", "Esempio: /toggle language it_it");

        translations.addProperty("survivalmod.info.running", "Stai utilizzando %s del Survival Mod");
        translations.addProperty("survivalmod.info.update_available", "È disponibile un aggiornamento!");
        translations.addProperty("survivalmod.info.no_update", "Non ci sono ancora aggiornamenti.");
        translations.addProperty("survivalmod.info.modrinth_link", "Clicca qui per andare alla pagina Modrinth");
    }

    private void addMagnetTranslations(JsonObject translations) {
        translations.addProperty("magnet.activated", "Magnete attivato - Attrae oggetti in un raggio di %d blocchi");
        translations.addProperty("magnet.deactivated", "Magnete disattivato");
        translations.addProperty("magnet.already_active", "Il magnete è già attivo");
        translations.addProperty("magnet.already_inactive", "Il magnete è già disattivato");
    }

    private void addClearCommandTranslations(JsonObject translations) {
        translations.addProperty("help.clear.title", "=== Resetta Impostazioni ===");
        translations.addProperty("help.clear.header", "Resetta le tue preferenze toggle:");
        translations.addProperty("help.clear.command", "/toggle clear - Resetta TUTTE le impostazioni");
        translations.addProperty("help.clear.partial_command", "/toggle clear <tipo> - Resetta impostazioni specifiche");
        translations.addProperty("help.clear.removes", "Cancellazione completa rimuove:");
        translations.addProperty("help.clear.toggles", "- Le tue impostazioni toggle attuali");
        translations.addProperty("help.clear.colors", "- Tutte le preferenze colore");
        translations.addProperty("help.clear.language", "- Selezione lingua");
        translations.addProperty("help.clear.partial_removes", "Opzioni cancellazione parziale:");
        translations.addProperty("help.clear.partial_colors", "- /toggle clear color - Tutti i colori");
        translations.addProperty("help.clear.partial_color_types", "- /toggle clear color <tipo> - Colore specifico");
        translations.addProperty("help.clear.partial_toggle", "- /toggle clear toggle - Solo impostazioni toggle");
        translations.addProperty("help.clear.partial_language", "- /toggle clear language - Solo lingua");
        translations.addProperty("help.clear.note", "Dovrai riconfigurare tutto dopo la cancellazione");

        translations.addProperty("command.clear.color.text", "Colore testo cancellato");
        translations.addProperty("command.clear.color.category", "Colore categoria cancellato");
        translations.addProperty("command.clear.color.material", "Colore materiale cancellato");
        translations.addProperty("command.clear.color.number", "Colore numeri cancellato");
        translations.addProperty("command.clear.color.time", "Colore tempo cancellato");
        translations.addProperty("command.clear.toggle", "Impostazioni toggle cancellate");
        translations.addProperty("command.clear.language", "Impostazione lingua cancellata");
        translations.addProperty("help.clear.partial", "/toggle clear <tipo> - Cancella impostazioni specifiche");
        translations.addProperty("help.clear.types", "Tipi disponibili: color, toggle, language");
        translations.addProperty("help.clear.color_types", "Tipi colore disponibili: text, category, material, number, time");
    }

    private void addSetCommandTranslations(JsonObject translations) {
        translations.addProperty("command.set.success", "Impostato %s su %s");
        translations.addProperty("command.set.usage", "Uso: /toggle set <tipo> <valore>");
        translations.addProperty("command.set.types", "Tipi disponibili: object, location, category");
        translations.addProperty("command.set.invalid_stat_category", "Categoria statistiche non valida");
        translations.addProperty("command.set.invalid_location", "Posizione non valida");
        translations.addProperty("command.set.object", "Oggetto impostato su %s");
        translations.addProperty("command.set.location", "Posizione impostata su %s");
        translations.addProperty("command.set.category", "Categoria statistiche impostata su %s");
    }
}