package de.tomate65.survivalmod.translation;

import com.google.gson.JsonObject;

public class PlPlTranslationGenerator extends TranslationGenerator {
    @Override
    public String getLanguageCode() {
        return "pl_pl";
    }

    @Override
    public JsonObject generateTranslations() {
        JsonObject translations = new JsonObject();

        // Time units (Jednostki czasu)
        translations.addProperty("time.years", "l"); // y
        translations.addProperty("time.days", "d");  // d
        translations.addProperty("time.hours", "godz"); // h
        translations.addProperty("time.minutes", "min"); // m
        translations.addProperty("time.seconds", "s"); // s

        // Command feedback (Informacje zwrotne z komend)
        translations.addProperty("command.disabled", "Komenda wyłączona");
        translations.addProperty("command.player_only", "Komenda musi być wykonana przez gracza");
        translations.addProperty("command.language_disabled", "Język %s jest obecnie wyłączony w konfiguracji");
        translations.addProperty("command.invalid_language", "Nieprawidłowy kod języka");
        translations.addProperty("command.invalid_toggle", "Ten przełącznik jest niedozwolony w bieżącym trybie (%s)");
        translations.addProperty("command.invalid_hex_color", "Nieprawidłowy format koloru HEX. Użyj RRGGBB lub #RRGGBB");
        translations.addProperty("command.error_saving", "Błąd podczas zapisywania preferencji %s.");
        translations.addProperty("command.reset_success", "Twoje preferencje przełączania zostały zresetowane.");
        translations.addProperty("command.reset_failed", "Nie udało się zresetować preferencji przełączania.");
        translations.addProperty("command.reload_success", "Konfiguracja została pomyślnie przeładowana.");
        translations.addProperty("command.reload_failed", "Nie udało się przeładować konfiguracji.");
        translations.addProperty("command.backup_created", "Ręczna kopia zapasowa została utworzona pomyślnie.");
        translations.addProperty("command.backup_failed", "Kopia zapasowa nie powiodła się: %s");

        // Help command (Komenda pomocy)
        translations.addProperty("help.general", "Dostępne komendy:");
        translations.addProperty("help.toggle", "/toggle - Przełącza wyświetlanie HUD");
        translations.addProperty("help.toggle.info", "/toggle info - Wyświetla bieżące ustawienia przełącznika");
        translations.addProperty("help.toggle.set", "/toggle set <typ> <wartość> - Ustawia konkretne ustawienie");
        translations.addProperty("help.toggle.clear", "/toggle clear <typ> - Czyści konkretne ustawienia");
        translations.addProperty("help.toggle.color", "/toggle color <typ> <kolor> - Ustawia kolor tekstu HUD");
        translations.addProperty("help.toggle.language", "/toggle language <kod_języka> - Ustawia język HUD");
        translations.addProperty("help.survival", "/survival - Komenda informacji o przetrwaniu");
        translations.addProperty("help.survival.subcommand", "/survival <podkomenda> - Wyświetla konkretne informacje o przetrwaniu");
        translations.addProperty("help.recipetoggle", "/recipetoggle - Włącza/wyłącza przepisy");
        translations.addProperty("help.recipetoggle.enable", "/recipetoggle enable <ID_przepisu> - Włącza konkretny przepis");
        translations.addProperty("help.recipetoggle.disable", "/recipetoggle disable <ID_przepisu> - Wyłącza konkretny przepis");
        translations.addProperty("help.recipetoggle.list", "/recipetoggle list - Lista dostępnych przepisów");
        translations.addProperty("help.recipetoggle.enable_all", "/recipetoggle enable_all - Włącza wszystkie niestandardowe przepisy");
        translations.addProperty("help.recipetoggle.disable_all", "/recipetoggle disable_all - Wyłącza wszystkie niestandardowe przepisy");
        translations.addProperty("help.backup", "/survival backup - Ręcznie tworzy kopię zapasową ustawień");

        // Toggle command feedback (Informacje zwrotne z komendy przełączania)
        translations.addProperty("command.clear.color.text", "Wyczyszczono kolor tekstu");
        translations.addProperty("command.clear.color.category", "Wyczyszczono kolor kategorii");
        translations.addProperty("command.clear.color.material", "Wyczyszczono kolor materiału");
        translations.addProperty("command.clear.color.number", "Wyczyszczono kolor liczby");
        translations.addProperty("command.clear.color.time", "Wyczyszczono kolor czasu");
        translations.addProperty("command.clear.toggle", "Wyczyszczono ustawienia przełącznika");
        translations.addProperty("command.clear.language", "Wyczyszczono ustawienie języka");
        translations.addProperty("help.clear.partial", "/toggle clear <typ> - Czyści konkretne ustawienia");
        translations.addProperty("help.clear.types", "Dostępne typy: color, toggle, language");
        translations.addProperty("help.clear.color_types", "Dostępne typy kolorów: text, category, material, number, time");

        translations.addProperty("command.set.success", "Ustawiono %s na %s");
        translations.addProperty("command.set.usage", "Użycie: /toggle set <typ> <wartość>");
        translations.addProperty("command.set.types", "Dostępne typy: object, location, category");
        translations.addProperty("command.set.invalid_stat_category", "Nieprawidłowa kategoria statystyk");
        translations.addProperty("command.set.invalid_location", "Nieprawidłowa lokalizacja");
        translations.addProperty("command.set.object", "Ustawiono obiekt na %s");
        translations.addProperty("command.set.location", "Ustawiono lokalizację na %s");
        translations.addProperty("command.set.category", "Ustawiono kategorię statystyk na %s");

        // Toggle info (Informacje o przełączniku)
        translations.addProperty("toggle.info.current_object", "Bieżący obiekt: %s");
        translations.addProperty("toggle.info.current_location", "Bieżąca lokalizacja: %s");
        translations.addProperty("toggle.info.current_category", "Bieżąca kategoria: %s");
        translations.addProperty("toggle.info.current_language", "Bieżący język: %s");
        translations.addProperty("toggle.info.colors", "Kolory:");
        translations.addProperty("toggle.info.text_color", "Tekst: %s");
        translations.addProperty("toggle.info.category_color", "Kategoria: %s");
        translations.addProperty("toggle.info.material_color", "Materiał: %s");
        translations.addProperty("toggle.info.number_color", "Liczba: %s");
        translations.addProperty("toggle.info.time_color", "Czas: %s");
        translations.addProperty("toggle.info.toggle_enabled", "Przełącznik włączony");
        translations.addProperty("toggle.info.toggle_disabled", "Przełącznik wyłączony");

        // Toggle categories (Kategorie przełącznika)
        translations.addProperty("toggle.category.mined", "Wydobyto");
        translations.addProperty("toggle.category.broken", "Zniszczono");
        translations.addProperty("toggle.category.crafted", "Stworzono");
        translations.addProperty("toggle.category.used", "Użyto");
        translations.addProperty("toggle.category.picked_up", "Podniesiono");
        translations.addProperty("toggle.category.dropped", "Upuszczono");
        translations.addProperty("toggle.category.killed", "Zabito");
        translations.addProperty("toggle.category.killed_by", "Zabity przez");
        translations.addProperty("toggle.category.custom", "Niestandardowe");
        translations.addProperty("toggle.category.time", "Czas");
        translations.addProperty("toggle.category.magnet", "Magnes");

        // Toggle locations (Lokalizacje przełącznika)
        translations.addProperty("toggle.location.actionbar", "Pasek akcji");
        translations.addProperty("toggle.location.title", "Tytuł");
        translations.addProperty("toggle.location.subtitle", "Podtytuł");
        translations.addProperty("toggle.location.chat", "Czat");

        // Recipe command feedback (Informacje zwrotne z komendy przepisów)
        translations.addProperty("recipe.toggle.enabled", "Przepis %s włączony. Uruchom /reload, aby zastosować zmiany.");
        translations.addProperty("recipe.toggle.disabled", "Przepis %s wyłączony. Uruchom /reload, aby zastosować zmiany.");
        translations.addProperty("recipe.file_not_found", "Plik przepisu nie znaleziono: %s");
        translations.addProperty("recipe.no_custom_recipes", "Nie znaleziono niestandardowych przepisów.");
        translations.addProperty("recipe.available_recipes", "Dostępne przepisy:");
        translations.addProperty("recipe.status_enabled", "włączony");
        translations.addProperty("recipe.status_disabled", "wyłączony");
        translations.addProperty("recipe.toggle_all_enabled", "Włączono wszystkie niestandardowe przepisy (%d). Uruchom /reload, aby zastosować zmiany.");
        translations.addProperty("recipe.toggle_all_disabled", "Wyłączono wszystkie niestandardowe przepisy (%d). Uruchom /reload, aby zastosować zmiany.");

        // Survival Command (Komenda przetrwania)
        translations.addProperty("survival.no_content", "Brak zawartości dla tej podkomendy.");
        translations.addProperty("survival.available_subcommands", "Dostępne podkomendy:");
        translations.addProperty("survival.no_subcommands", "Brak dostępnych podkomend. Sprawdź swoją konfigurację.");
        translations.addProperty("survival.update_available", "Dostępna nowa wersja! Bieżąca: %s, Najnowsza: %s");
        translations.addProperty("survival.no_update", "Brak nowych aktualizacji.");
        translations.addProperty("survival.update_check_failed", "Nie udało się sprawdzić aktualizacji.");

        return translations;
    }
}
