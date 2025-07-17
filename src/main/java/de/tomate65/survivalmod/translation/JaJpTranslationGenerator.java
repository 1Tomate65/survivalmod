package de.tomate65.survivalmod.translation;

import com.google.gson.JsonObject;

public class JaJpTranslationGenerator extends TranslationGenerator {
    @Override
    public String getLanguageCode() {
        return "ja_jp";
    }

    @Override
    public JsonObject generateTranslations() {
        JsonObject translations = new JsonObject();

        // Time units (Zeiteinheiten)
        translations.addProperty("time.years", "年"); // y
        translations.addProperty("time.days", "日");  // d
        translations.addProperty("time.hours", "時間"); // h
        translations.addProperty("time.minutes", "分"); // m
        translations.addProperty("time.seconds", "秒"); // s

        // Command feedback (Befehls-Feedback)
        translations.addProperty("command.disabled", "コマンドが無効です");
        translations.addProperty("command.player_only", "このコマンドはプレイヤーのみが実行できます");
        translations.addProperty("command.language_disabled", "言語 %s は現在設定で無効になっています");
        translations.addProperty("command.invalid_language", "無効な言語コードです");
        translations.addProperty("command.invalid_toggle", "この切り替えは現在のモードでは許可されていません (%s)");
        translations.addProperty("command.invalid_hex_color", "無効な16進カラー形式です。RRGGBBまたは#RRGGBBを使用してください");
        translations.addProperty("command.error_saving", "%s の設定を保存中にエラーが発生しました。");
        translations.addProperty("command.reset_success", "切り替え設定がリセットされました。");
        translations.addProperty("command.reset_failed", "切り替え設定のリセットに失敗しました。");
        translations.addProperty("command.reload_success", "設定が再読み込みされました。");
        translations.addProperty("command.reload_failed", "設定の再読み込みに失敗しました。");
        translations.addProperty("command.backup_created", "手動バックアップが正常に作成されました。");
        translations.addProperty("command.backup_failed", "バックアップに失敗しました: %s");

        // Help command (Hilfe-Befehl)
        translations.addProperty("help.general", "利用可能なコマンド:");
        translations.addProperty("help.toggle", "/toggle - HUD表示を切り替えます");
        translations.addProperty("help.toggle.info", "/toggle info - 現在の切り替え設定を表示します");
        translations.addProperty("help.toggle.set", "/toggle set <タイプ> <値> - 特定の設定を設定します");
        translations.addProperty("help.toggle.clear", "/toggle clear <タイプ> - 特定の設定をクリアします");
        translations.addProperty("help.toggle.color", "/toggle color <タイプ> <色> - HUDテキストの色を設定します");
        translations.addProperty("help.toggle.language", "/toggle language <言語コード> - HUDの言語を設定します");
        translations.addProperty("help.survival", "/survival - サバイバル情報コマンド");
        translations.addProperty("help.survival.subcommand", "/survival <サブコマンド> - 特定のサバイバル情報を表示します");
        translations.addProperty("help.recipetoggle", "/recipetoggle - レシピを有効/無効にします");
        translations.addProperty("help.recipetoggle.enable", "/recipetoggle enable <レシピID> - 特定のレシピを有効にします");
        translations.addProperty("help.recipetoggle.disable", "/recipetoggle disable <レシピID> - 特定のレシピを無効にします");
        translations.addProperty("help.recipetoggle.list", "/recipetoggle list - 利用可能なレシピをリスト表示します");
        translations.addProperty("help.recipetoggle.enable_all", "/recipetoggle enable_all - すべてのカスタムレシピを有効にします");
        translations.addProperty("help.recipetoggle.disable_all", "/recipetoggle disable_all - すべてのカスタムレシピを無効にします");
        translations.addProperty("help.backup", "/survival backup - 手動で設定をバックアップします");

        // Toggle command feedback (切り替えコマンドのフィードバック)
        translations.addProperty("command.clear.color.text", "テキストの色をクリアしました");
        translations.addProperty("command.clear.color.category", "カテゴリの色をクリアしました");
        translations.addProperty("command.clear.color.material", "素材の色をクリアしました");
        translations.addProperty("command.clear.color.number", "数字の色をクリアしました");
        translations.addProperty("command.clear.color.time", "時間の表示色をクリアしました");
        translations.addProperty("command.clear.toggle", "切り替え設定をクリアしました");
        translations.addProperty("command.clear.language", "言語設定をクリアしました");
        translations.addProperty("help.clear.partial", "/toggle clear <タイプ> - 特定の設定をクリアします");
        translations.addProperty("help.clear.types", "利用可能なタイプ: color, toggle, language");
        translations.addProperty("help.clear.color_types", "利用可能なカラータイプ: text, category, material, number, time");

        translations.addProperty("command.set.success", "%s を %s に設定しました");
        translations.addProperty("command.set.usage", "使用法: /toggle set <タイプ> <値>");
        translations.addProperty("command.set.types", "利用可能なタイプ: object, location, category");
        translations.addProperty("command.set.invalid_stat_category", "無効な統計カテゴリです");
        translations.addProperty("command.set.invalid_location", "無効な場所です");
        translations.addProperty("command.set.object", "オブジェクトを %s に設定しました");
        translations.addProperty("command.set.location", "場所を %s に設定しました");
        translations.addProperty("command.set.category", "統計カテゴリを %s に設定しました");

        // Toggle info (切り替え情報)
        translations.addProperty("toggle.info.current_object", "現在のオブジェクト: %s");
        translations.addProperty("toggle.info.current_location", "現在の場所: %s");
        translations.addProperty("toggle.info.current_category", "現在のカテゴリ: %s");
        translations.addProperty("toggle.info.current_language", "現在の言語: %s");
        translations.addProperty("toggle.info.colors", "色:");
        translations.addProperty("toggle.info.text_color", "テキスト: %s");
        translations.addProperty("toggle.info.category_color", "カテゴリ: %s");
        translations.addProperty("toggle.info.material_color", "素材: %s");
        translations.addProperty("toggle.info.number_color", "数字: %s");
        translations.addProperty("toggle.info.time_color", "時間: %s");
        translations.addProperty("toggle.info.toggle_enabled", "切り替えが有効です");
        translations.addProperty("toggle.info.toggle_disabled", "切り替えが無効です");

        // Toggle categories (切り替えカテゴリ)
        translations.addProperty("toggle.category.mined", "採掘済み");
        translations.addProperty("toggle.category.broken", "破壊済み");
        translations.addProperty("toggle.category.crafted", "作成済み");
        translations.addProperty("toggle.category.used", "使用済み");
        translations.addProperty("toggle.category.picked_up", "拾った");
        translations.addProperty("toggle.category.dropped", "落とした");
        translations.addProperty("toggle.category.killed", "倒した");
        translations.addProperty("toggle.category.killed_by", "倒された");
        translations.addProperty("toggle.category.custom", "カスタム");
        translations.addProperty("toggle.category.time", "時間");
        translations.addProperty("toggle.category.magnet", "マグネット");

        // Toggle locations (切り替え場所)
        translations.addProperty("toggle.location.actionbar", "アクションバー");
        translations.addProperty("toggle.location.title", "タイトル");
        translations.addProperty("toggle.location.subtitle", "サブタイトル");
        translations.addProperty("toggle.location.chat", "チャット");

        // Recipe command feedback (レシピコマンドのフィードバック)
        translations.addProperty("recipe.toggle.enabled", "レシピ %s が有効になりました。変更を適用するには /reload を実行してください。");
        translations.addProperty("recipe.toggle.disabled", "レシピ %s が無効になりました。変更を適用するには /reload を実行してください。");
        translations.addProperty("recipe.file_not_found", "レシピファイルが見つかりません: %s");
        translations.addProperty("recipe.no_custom_recipes", "カスタムレシピが見つかりません。");
        translations.addProperty("recipe.available_recipes", "利用可能なレシピ:");
        translations.addProperty("recipe.status_enabled", "有効");
        translations.addProperty("recipe.status_disabled", "無効");
        translations.addProperty("recipe.toggle_all_enabled", "すべてのカスタムレシピ (%d) が有効になりました。変更を適用するには /reload を実行してください。");
        translations.addProperty("recipe.toggle_all_disabled", "すべてのカスタムレシピ (%d) が無効になりました。変更を適用するには /reload を実行してください。");

        // Survival Command (サバイバルコマンド)
        translations.addProperty("survival.no_content", "このサブコマンドにはコンテンツがありません。");
        translations.addProperty("survival.available_subcommands", "利用可能なサブコマンド:");
        translations.addProperty("survival.no_subcommands", "サブコマンドがありません。設定を確認してください。");
        translations.addProperty("survival.update_available", "新しいバージョンが利用可能です！現在: %s, 最新: %s");
        translations.addProperty("survival.no_update", "新しいアップデートはありません。");
        translations.addProperty("survival.update_check_failed", "アップデートの確認に失敗しました。");

        return translations;
    }
}