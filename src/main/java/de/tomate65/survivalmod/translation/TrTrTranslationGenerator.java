package de.tomate65.survivalmod.translation;

import com.google.gson.JsonObject;

public class TrTrTranslationGenerator extends TranslationGenerator {
    @Override
    public String getLanguageCode() {
        return "tr_tr";
    }

    @Override
    public JsonObject generateTranslations() {
        JsonObject translations = new JsonObject();

        // Time units
        translations.addProperty("time.years", "y");
        translations.addProperty("time.days", "g");
        translations.addProperty("time.hours", "s");
        translations.addProperty("time.minutes", "d");
        translations.addProperty("time.seconds", "sn");

        // Command feedback
        translations.addProperty("command.disabled", "Komut devre dışı");
        translations.addProperty("command.player_only", "Komut yalnızca bir oyuncu tarafından çalıştırılabilir");
        translations.addProperty("command.invalid_language", "Geçersiz dil kodu");
        translations.addProperty("command.language_disabled", "%s dili şu anda yapılandırmada devre dışı");
        translations.addProperty("command.invalid_toggle", "Bu geçiş mevcut modda (%s) izin verilmiyor");
        translations.addProperty("command.invalid_hex_color", "Geçersiz hex renk formatı. RRGGBB veya #RRGGBB kullanın");
        translations.addProperty("command.error_saving", "%s tercihiniz kaydedilirken hata oluştu");
        translations.addProperty("command.reset_success", "Geçiş tercihleriniz sıfırlandı");
        translations.addProperty("command.reset_failed", "Geçiş tercihleriniz sıfırlanamadı");
        translations.addProperty("command.reset_none", "Sıfırlanacak geçiş tercihiniz yoktu");
        translations.addProperty("command.reloaded", "Yapılandırma yeniden yüklendi");
        translations.addProperty("command.no_content", "Bu alt komut için içerik yok");
        translations.addProperty("command.no_subcommands", "Kullanılabilir alt komut yok. Yapılandırmanızı kontrol edin");
        translations.addProperty("command.available_commands", "Kullanılabilir alt komutlar:");
        translations.addProperty("command.usage", "Kullanım: %s");
        translations.addProperty("command.available_colors", "Kullanılabilir renkler: %s");
        translations.addProperty("command.available_languages", "Kullanılabilir diller: %s");
        translations.addProperty("command.hex_format", "Hex formatı RRGGBB veya #RRGGBB olabilir");
        translations.addProperty("command.reset_partial", "%s ayarlarınız temizlendi");
        translations.addProperty("command.set_usage.title", "=== Set Komutu Kullanımı ===");
        translations.addProperty("command.set_usage.object", "/toggle set object <nesne_id>");
        translations.addProperty("command.set_usage.location", "/toggle set location <actionbar|chat|title>");
        translations.addProperty("command.set_usage.category", "/toggle set category <istatistik_kategorisi>");

        translations.addProperty("message.action.mined", "kazdı");
        translations.addProperty("message.action.crafted", "üretti");
        translations.addProperty("message.action.used", "kullandı");
        translations.addProperty("message.action.broken", "kırdı");
        translations.addProperty("message.action.picked_up", "aldı");
        translations.addProperty("message.action.dropped", "bıraktı");
        translations.addProperty("message.action.killed", "öldürdü");
        translations.addProperty("message.action.killed_by", "tarafından öldürüldü");
        translations.addProperty("message.action.custom", "bir hedefe ulaştı");
        translations.addProperty("message.you", "Sen");
        translations.addProperty("message.have", "");
        translations.addProperty("message.plural", "");
        translations.addProperty("message.exclamation", "!");

        // Command success messages
        translations.addProperty("timeplayed.set", "Oynama süresi %s'de görüntülenecek şekilde ayarlandı");
        translations.addProperty("language.set", "Dil %s olarak ayarlandı");
        translations.addProperty("color.set", "%s rengi %s olarak ayarlandı");
        translations.addProperty("color.reset", "%s rengi varsayılana sıfırlandı");
        translations.addProperty("toggle.set", "%s geçişi %s istatistikleri ile %s'de görüntülenecek");

        // Color command parts
        translations.addProperty("color.set.part1", "Ayarlandı ");
        translations.addProperty("color.set.part2", " rengi ");
        translations.addProperty("color.reset.part1", "Sıfırlandı ");
        translations.addProperty("color.reset.part2", " rengi varsayılana");

        // Item names
        translations.addProperty("item.stone.name", "Taş");
        translations.addProperty("item.dirt.name", "Toprak");
        translations.addProperty("item.oak_log.name", "Meşe Kütüğü");

        // Stat categories
        translations.addProperty("stat.timeplayed", "Oynama Süresi");
        translations.addProperty("stat.mined", "Kazıldı");
        translations.addProperty("stat.crafted", "Üretildi");
        translations.addProperty("stat.used", "Kullanıldı");
        translations.addProperty("stat.broken", "Kırıldı");
        translations.addProperty("stat.picked_up", "Alındı");
        translations.addProperty("stat.dropped", "Bırakıldı");
        translations.addProperty("stat.killed", "Öldürüldü");
        translations.addProperty("stat.killed_by", "Tarafından Öldürüldü");
        translations.addProperty("stat.custom", "Özel");

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
        translations.addProperty("help.general.title", "=== Toggle Komutu Yardım ===");
        translations.addProperty("help.general.materials", "/toggle help materials - Kullanılabilir malzeme kategorilerini göster");
        translations.addProperty("help.general.color", "/toggle help color - Renk özelleştirme yardımını göster");
        translations.addProperty("help.general.clear", "/toggle help clear - Ayarlarınızı sıfırlama yöntemini göster");
        translations.addProperty("help.general.language", "/toggle help language - Dil seçeneklerini göster");
        translations.addProperty("help.general.usage", "Kullanım: /toggle <malzeme> <konum> [istatistik_kategorisi]");

        translations.addProperty("help.materials.title", "=== Malzeme Kategorileri ===");
        translations.addProperty("help.materials.header", "Kullanılabilir malzeme türleri:");
        translations.addProperty("help.materials.mobs", "Canlılar - Tüm yaşayan varlıklar (zombi, creeper, vb.)");
        translations.addProperty("help.materials.blocks", "Bloklar - Tüm yerleştirilebilir bloklar (taş, toprak, vb.)");
        translations.addProperty("help.materials.items", "Eşyalar - Tüm eşyalar (kılıçlar, yiyecek, aletler, vb.)");
        translations.addProperty("help.materials.all", "Tümü - Mevcut her şey");
        translations.addProperty("help.materials.usage", "Kullanım: /toggle <malzeme_id> <actionbar|chat|title> [istatistik_kategorisi]");

        translations.addProperty("help.color.title", "=== Renk Özelleştirme ===");
        translations.addProperty("help.color.header", "Farklı metin öğeleri için renkleri değiştirin:");
        translations.addProperty("help.color.text", "/toggle color text <renk> - Ana metin rengi");
        translations.addProperty("help.color.category", "/toggle color category <renk> - İstatistik kategorisi rengi");
        translations.addProperty("help.color.material", "/toggle color material <renk> - Malzeme adı rengi");
        translations.addProperty("help.color.number", "/toggle color number <renk> - Sayı rengi");
        translations.addProperty("help.color.time", "/toggle color time <renk> - Oynama süresi görüntüleme rengi");
        translations.addProperty("help.color.colors", "Renkler: RED, GREEN, BLUE, YELLOW, vb. olabilir");
        translations.addProperty("help.color.hex", "Veya hex değerlerini kullanın: '#FF0000' (kırmızı), '#00FF00' (yeşil)");
        translations.addProperty("help.color.none", "Varsayılana sıfırlamak için 'NONE' kullanın");

        translations.addProperty("help.language.title", "=== Dil Seçenekleri ===");
        translations.addProperty("help.language.available", "Kullanılabilir diller:");
        translations.addProperty("help.language.change", "Görüntüleme dilinizi değiştirin:");
        translations.addProperty("help.language.command", "/toggle language <dil_kodu>");
        translations.addProperty("help.language.example", "Örnek: /toggle language tr_tr");

        translations.addProperty("survivalmod.info.running", "Survival Mod'un %s sürümünü kullanıyorsunuz");
        translations.addProperty("survivalmod.info.update_available", "Bir güncelleme var!");
        translations.addProperty("survivalmod.info.no_update", "Henüz bir güncelleme yok.");
        translations.addProperty("survivalmod.info.modrinth_link", "Modrinth sayfasına gitmek için tıklayın");
    }

    private void addMagnetTranslations(JsonObject translations) {
        translations.addProperty("magnet.activated", "Mıknatıs etkin - %d blok yarıçapında eşyaları çekiyor");
        translations.addProperty("magnet.deactivated", "Mıknatıs devre dışı");
        translations.addProperty("magnet.already_active", "Mıknatıs zaten etkin");
        translations.addProperty("magnet.already_inactive", "Mıknatıs zaten devre dışı");
    }

    private void addClearCommandTranslations(JsonObject translations) {
        translations.addProperty("help.clear.title", "=== Ayarları Temizle ===");
        translations.addProperty("help.clear.header", "Geçiş tercihlerinizi sıfırlayın:");
        translations.addProperty("help.clear.command", "/toggle clear - TÜM ayarları sıfırla");
        translations.addProperty("help.clear.partial_command", "/toggle clear <tür> - Belirli ayarları sıfırla");
        translations.addProperty("help.clear.removes", "Tam temizleme şunları kaldırır:");
        translations.addProperty("help.clear.toggles", "- Mevcut geçiş ayarlarınız");
        translations.addProperty("help.clear.colors", "- Tüm renk tercihleri");
        translations.addProperty("help.clear.language", "- Dil seçimi");
        translations.addProperty("help.clear.partial_removes", "Kısmi temizleme seçenekleri:");
        translations.addProperty("help.clear.partial_colors", "- /toggle clear color - Tüm renkler");
        translations.addProperty("help.clear.partial_color_types", "- /toggle clear color <tür> - Belirli renk");
        translations.addProperty("help.clear.partial_toggle", "- /toggle clear toggle - Sadece geçiş ayarları");
        translations.addProperty("help.clear.partial_language", "- /toggle clear language - Sadece dil");
        translations.addProperty("help.clear.note", "Temizledikten sonra her şeyi yeniden ayarlamanız gerekecek");

        translations.addProperty("command.clear.color.text", "Metin rengi temizlendi");
        translations.addProperty("command.clear.color.category", "Kategori rengi temizlendi");
        translations.addProperty("command.clear.color.material", "Malzeme rengi temizlendi");
        translations.addProperty("command.clear.color.number", "Sayı rengi temizlendi");
        translations.addProperty("command.clear.color.time", "Zaman rengi temizlendi");
        translations.addProperty("command.clear.toggle", "Geçiş ayarları temizlendi");
        translations.addProperty("command.clear.language", "Dil ayarı temizlendi");
        translations.addProperty("help.clear.partial", "/toggle clear <tür> - Belirli ayarları temizle");
        translations.addProperty("help.clear.types", "Kullanılabilir türler: color, toggle, language");
        translations.addProperty("help.clear.color_types", "Kullanılabilir renk türleri: text, category, material, number, time");
    }

    private void addSetCommandTranslations(JsonObject translations) {
        translations.addProperty("command.set.success", "%s, %s olarak ayarlandı");
        translations.addProperty("command.set.usage", "Kullanım: /toggle set <tür> <değer>");
        translations.addProperty("command.set.types", "Kullanılabilir türler: object, location, category");
        translations.addProperty("command.set.invalid_stat_category", "Geçersiz istatistik kategorisi");
        translations.addProperty("command.set.invalid_location", "Geçersiz konum");
        translations.addProperty("command.set.object", "Nesne %s olarak ayarlandı");
        translations.addProperty("command.set.location", "Konum %s olarak ayarlandı");
        translations.addProperty("command.set.category", "İstatistik kategorisi %s olarak ayarlandı");
    }
}