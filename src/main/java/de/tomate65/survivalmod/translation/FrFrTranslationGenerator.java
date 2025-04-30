package de.tomate65.survivalmod.translation;

import com.google.gson.JsonObject;

public class FrFrTranslationGenerator extends TranslationGenerator {
    @Override
    public String getLanguageCode() {
        return "fr_fr";
    }

    @Override
    public JsonObject generateTranslations() {
        JsonObject translations = new JsonObject();

        // Unités de temps
        translations.addProperty("time.years", "an");
        translations.addProperty("time.days", "j");
        translations.addProperty("time.hours", "h");
        translations.addProperty("time.minutes", "min");
        translations.addProperty("time.seconds", "s");

        // Retour des commandes
        translations.addProperty("command.disabled", "Commande désactivée");
        translations.addProperty("command.player_only", "La commande doit être exécutée par un joueur");
        translations.addProperty("command.invalid_language", "Code de langue invalide");
        translations.addProperty("command.invalid_toggle", "Cette option n'est pas autorisée dans le mode actuel (%s)");
        translations.addProperty("command.invalid_hex_color", "Format de couleur hexadécimal invalide. Utilisez RRGGBB ou #RRGGBB");
        translations.addProperty("command.error_saving", "Erreur lors de l'enregistrement de votre préférence %s.");
        translations.addProperty("command.reset_success", "Vos préférences ont été réinitialisées.");
        translations.addProperty("command.reset_failed", "Échec de la réinitialisation de vos préférences.");
        translations.addProperty("command.reset_none", "Vous n'aviez aucune préférence à réinitialiser.");
        translations.addProperty("command.reloaded", "Configuration rechargée.");
        translations.addProperty("command.no_content", "Aucun contenu disponible pour cette sous-commande.");
        translations.addProperty("command.no_subcommands", "Aucune sous-commande disponible. Vérifiez votre configuration.");
        translations.addProperty("command.available_commands", "Sous-commandes disponibles :");
        translations.addProperty("command.usage", "Utilisation : %s");
        translations.addProperty("command.available_colors", "Couleurs disponibles : %s");
        translations.addProperty("command.available_languages", "Langues disponibles : %s");
        translations.addProperty("command.hex_format", "Le format hexadécimal peut être RRGGBB ou #RRGGBB");
        translations.addProperty("command.reset_partial", "Vos paramètres %s ont été effacés");

        // Messages de succès des commandes
        translations.addProperty("timeplayed.set", "Temps de jeu affiché en %s");
        translations.addProperty("language.set", "Langue définie sur %s");
        translations.addProperty("color.set", "Couleur %s définie sur %s");
        translations.addProperty("color.reset", "Couleur %s réinitialisée par défaut");
        translations.addProperty("toggle.set", "Option %s affichée en %s avec les statistiques %s");

        // Parties des commandes de couleur
        translations.addProperty("color.set.part1", "Couleur ");
        translations.addProperty("color.set.part2", " définie sur ");
        translations.addProperty("color.reset.part1", "Couleur ");
        translations.addProperty("color.reset.part2", " réinitialisée par défaut");

        // Noms des items
        translations.addProperty("item.stone.name", "Pierre");
        translations.addProperty("item.dirt.name", "Terre");
        translations.addProperty("item.oak_log.name", "Bûche de chêne");

        // Catégories de statistiques
        translations.addProperty("stat.timeplayed", "Temps de jeu");
        translations.addProperty("stat.mined", "Miné");
        translations.addProperty("stat.crafted", "Fabriqué");
        translations.addProperty("stat.used", "Utilisé");
        translations.addProperty("stat.broken", "Cassé");
        translations.addProperty("stat.picked_up", "Ramassé");
        translations.addProperty("stat.dropped", "Jeté");
        translations.addProperty("stat.killed", "Tué");
        translations.addProperty("stat.killed_by", "Tué par");
        translations.addProperty("stat.custom", "Personnalisé");

        // Système d'aide
        addHelpTranslations(translations);

        // Système d'aimant
        addMagnetTranslations(translations);

        // Commande clear
        addClearCommandTranslations(translations);

        // Commande set
        addSetCommandTranslations(translations);

        return translations;
    }

    private void addHelpTranslations(JsonObject translations) {
        translations.addProperty("help.general.title", "=== Aide de la commande Toggle ===");
        translations.addProperty("help.general.materials", "/toggle help materials - Afficher les catégories de matériaux disponibles");
        translations.addProperty("help.general.color", "/toggle help color - Afficher l'aide pour la personnalisation des couleurs");
        translations.addProperty("help.general.clear", "/toggle help clear - Afficher comment réinitialiser vos paramètres");
        translations.addProperty("help.general.language", "/toggle help language - Afficher les options de langue");
        translations.addProperty("help.general.usage", "Utilisation : /toggle <matériau> <emplacement> [catégorie_stat]");

        translations.addProperty("help.materials.title", "=== Catégories de matériaux ===");
        translations.addProperty("help.materials.header", "Types de matériaux disponibles :");
        translations.addProperty("help.materials.mobs", "Créatures - Toutes les entités vivantes (zombies, creepers, etc.)");
        translations.addProperty("help.materials.blocks", "Blocs - Tous les blocs placables (pierre, terre, etc.)");
        translations.addProperty("help.materials.items", "Objets - Tous les items (épées, nourriture, outils, etc.)");
        translations.addProperty("help.materials.all", "Tout - Tout ce qui est disponible");
        translations.addProperty("help.materials.usage", "Utilisation : /toggle <ID_matériau> <barre_action|chat|titre> [catégorie_stat]");

        translations.addProperty("help.color.title", "=== Personnalisation des couleurs ===");
        translations.addProperty("help.color.header", "Changer les couleurs des différents éléments textuels :");
        translations.addProperty("help.color.text", "/toggle color text <couleur> - Couleur du texte principal");
        translations.addProperty("help.color.category", "/toggle color category <couleur> - Couleur de la catégorie de statistiques");
        translations.addProperty("help.color.material", "/toggle color material <couleur> - Couleur du nom du matériau");
        translations.addProperty("help.color.number", "/toggle color number <couleur> - Couleur des nombres");
        translations.addProperty("help.color.time", "/toggle color time <couleur> - Couleur de l'affichage du temps de jeu");
        translations.addProperty("help.color.colors", "Couleurs possibles : ROUGE, VERT, BLEU, JAUNE, etc.");
        translations.addProperty("help.color.hex", "Ou utiliser des valeurs hexadécimales : '#FF0000' (rouge), '#00FF00' (vert)");
        translations.addProperty("help.color.none", "Utiliser 'NONE' pour réinitialiser par défaut");

        translations.addProperty("help.language.title", "=== Options de langue ===");
        translations.addProperty("help.language.available", "Langues disponibles :");
        translations.addProperty("help.language.change", "Changer votre langue d'affichage :");
        translations.addProperty("help.language.command", "/toggle language <code_langue>");
        translations.addProperty("help.language.example", "Exemple : /toggle language fr_fr");
    }

    private void addMagnetTranslations(JsonObject translations) {
        translations.addProperty("magnet.activated", "Aimant activé - Attire les objets dans un rayon de %d blocs");
        translations.addProperty("magnet.deactivated", "Aimant désactivé");
        translations.addProperty("magnet.already_active", "L'aimant est déjà actif");
        translations.addProperty("magnet.already_inactive", "L'aimant est déjà inactif");
    }

    private void addClearCommandTranslations(JsonObject translations) {
        translations.addProperty("help.clear.title", "=== Réinitialisation des paramètres ===");
        translations.addProperty("help.clear.header", "Réinitialiser vos préférences :");
        translations.addProperty("help.clear.command", "/toggle clear - Réinitialiser TOUS les paramètres");
        translations.addProperty("help.clear.partial_command", "/toggle clear <type> - Réinitialiser des paramètres spécifiques");
        translations.addProperty("help.clear.removes", "La réinitialisation complète supprime :");
        translations.addProperty("help.clear.toggles", "- Vos paramètres actuels");
        translations.addProperty("help.clear.colors", "- Toutes les préférences de couleur");
        translations.addProperty("help.clear.language", "- La sélection de langue");
        translations.addProperty("help.clear.partial_removes", "Options de réinitialisation partielle :");
        translations.addProperty("help.clear.partial_colors", "- /toggle clear color - Toutes les couleurs");
        translations.addProperty("help.clear.partial_color_types", "- /toggle clear color <type> - Couleur spécifique");
        translations.addProperty("help.clear.partial_toggle", "- /toggle clear toggle - Seulement les paramètres");
        translations.addProperty("help.clear.partial_language", "- /toggle clear language - Seulement la langue");
        translations.addProperty("help.clear.note", "Vous devrez tout reconfigurer après la réinitialisation.");

        translations.addProperty("command.clear.color.text", "Couleur du texte réinitialisée");
        translations.addProperty("command.clear.color.category", "Couleur de catégorie réinitialisée");
        translations.addProperty("command.clear.color.material", "Couleur de matériau réinitialisée");
        translations.addProperty("command.clear.color.number", "Couleur des nombres réinitialisée");
        translations.addProperty("command.clear.color.time", "Couleur du temps réinitialisée");
        translations.addProperty("command.clear.toggle", "Paramètres réinitialisés");
        translations.addProperty("command.clear.language", "Paramètre de langue réinitialisé");
        translations.addProperty("help.clear.partial", "/toggle clear <type> - Effacer des paramètres spécifiques");
        translations.addProperty("help.clear.types", "Types disponibles : color, toggle, language");
        translations.addProperty("help.clear.color_types", "Types de couleur disponibles : text, category, material, number, time");
    }

    private void addSetCommandTranslations(JsonObject translations) {
        translations.addProperty("command.set.success", "%s défini sur %s");
        translations.addProperty("command.set.usage", "Utilisation : /toggle set <type> <valeur>");
        translations.addProperty("command.set.types", "Types disponibles : object, location, category");
        translations.addProperty("command.set.invalid_stat_category", "Catégorie de statistique invalide");
        translations.addProperty("command.set.invalid_location", "Emplacement invalide");
        translations.addProperty("command.set.object", "Objet défini sur %s");
        translations.addProperty("command.set.location", "Emplacement défini sur %s");
        translations.addProperty("command.set.category", "Catégorie de statistique définie sur %s");
    }
}