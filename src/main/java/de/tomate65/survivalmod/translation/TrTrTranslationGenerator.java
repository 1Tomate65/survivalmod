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

        // Zaman Birimleri
        translations.addProperty("time.years", "yıl");
        translations.addProperty("time.days", "gün");
        translations.addProperty("time.hours", "sa");
        translations.addProperty("time.minutes", "dk");
        translations.addProperty("time.seconds", "sn");

        // Eşya İsimleri
        translations.addProperty("item.stone.name", "Taş");
        translations.addProperty("item.dirt.name", "Toprak");
        translations.addProperty("item.oak_log.name", "Meşe Kütüğü");

        // İstatistik Kategorileri
        translations.addProperty("stat.timeplayed", "Oynama Süresi");
        translations.addProperty("stat.mined", "Kazıldı");
        translations.addProperty("stat.crafted", "Üretildi");
        translations.addProperty("stat.used", "Kullanıldı");
        translations.addProperty("stat.broken", "Kırıldı");
        translations.addProperty("stat.picked_up", "Toplandı");
        translations.addProperty("stat.dropped", "Bırakıldı");
        translations.addProperty("stat.killed", "Öldürüldü");
        translations.addProperty("stat.killed_by", "Tarafından Öldürüldü");
        translations.addProperty("stat.custom", "Özel");

        // Yardım Sistemi
        addHelpTranslations(translations);

        // Mıknatıs Sistemi
        addMagnetTranslations(translations);

        // Temizleme Komutu
        addClearCommandTranslations(translations);

        // Ayarlama Komutu
        addSetCommandTranslations(translations);

        return translations;
    }

    private void addHelpTranslations(JsonObject translations) {
        translations.addProperty("help.general.title", "=== Toggle Komutu Yardım ===");
        translations.addProperty("help.general.materials", "/toggle help materials - Kullanılabilir malzeme kategorilerini göster");
        translations.addProperty("help.general.color", "/toggle help color - Renk özelleştirme yardımı");
        translations.addProperty("help.general.clear", "/toggle help clear - Ayarları sıfırlama yöntemini göster");
        translations.addProperty("help.general.language", "/toggle help language - Dil seçeneklerini göster");
        translations.addProperty("help.general.usage", "Kullanım: /toggle <malzeme> <konum> [istatistik_kategori]");

        translations.addProperty("help.materials.title", "=== Malzeme Kategorileri ===");
        translations.addProperty("help.materials.header", "Kullanılabilir malzeme türleri:");
        translations.addProperty("help.materials.mobs", "Yaratıklar - Tüm canlı varlıklar (zombi, creeper vb.)");
        translations.addProperty("help.materials.blocks", "Bloklar - Yerleştirilebilir tüm bloklar (taş, toprak vb.)");
        translations.addProperty("help.materials.items", "Eşyalar - Tüm nesneler (kılıçlar, yiyecek, aletler vb.)");
        translations.addProperty("help.materials.all", "Tümü - Mevcut her şey");
        translations.addProperty("help.materials.usage", "Kullanım: /toggle <malzeme_id> <actionbar|sohbet|başlık> [istatistik_kategori]");

        translations.addProperty("help.color.title", "=== Renk Özelleştirme ===");
        translations.addProperty("help.color.header", "Farklı metin öğeleri için renkleri değiştirin:");
        translations.addProperty("help.color.text", "/toggle color text <renk> - Ana metin rengi");
        translations.addProperty("help.color.category", "/toggle color category <renk> - İstatistik kategori rengi");
        translations.addProperty("help.color.material", "/toggle color material <renk> - Malzeme adı rengi");
        translations.addProperty("help.color.number", "/toggle color number <renk> - Sayı rengi");
        translations.addProperty("help.color.time", "/toggle color time <renk> - Oyun süresi görüntüleme rengi");
        translations.addProperty("help.color.colors", "Renkler: KIRMIZI, YEŞİL, MAVİ, SARI vb.");
        translations.addProperty("help.color.hex", "Veya hex değerleri kullanın: '#FF0000' (kırmızı), '#00FF00' (yeşil)");
        translations.addProperty("help.color.none", "Varsayılana sıfırlamak için 'NONE' kullanın");

        translations.addProperty("help.language.title", "=== Dil Seçenekleri ===");
        translations.addProperty("help.language.available", "Mevcut diller:");
        translations.addProperty("help.language.change", "Görüntüleme dilini değiştirin:");
        translations.addProperty("help.language.command", "/toggle language <dil_kodu>");
        translations.addProperty("help.language.example", "Örnek: /toggle language tr_tr");
    }

    private void addMagnetTranslations(JsonObject translations) {
        translations.addProperty("magnet.activated", "Mıknatıs aktif - %d blok yarıçapında eşyaları çekiyor");
        translations.addProperty("magnet.deactivated", "Mıknatıs devre dışı");
        translations.addProperty("magnet.already_active", "Mıknatıs zaten aktif");
        translations.addProperty("magnet.already_inactive", "Mıknatıs zaten devre dışı");
    }

    private void addClearCommandTranslations(JsonObject translations) {
        translations.addProperty("help.clear.title", "=== Ayarları Sıfırla ===");
        translations.addProperty("help.clear.header", "Tercihlerinizi sıfırlayın:");
        translations.addProperty("help.clear.command", "/toggle clear - TÜM ayarları sıfırla");
        translations.addProperty("help.clear.partial_command", "/toggle clear <tür> - Belirli ayarları sıfırla");
        translations.addProperty("help.clear.removes", "Tam sıfırlama şunları kaldırır:");
        translations.addProperty("help.clear.toggles", "- Mevcut ayarlarınız");
        translations.addProperty("help.clear.colors", "- Tüm renk tercihleri");
        translations.addProperty("help.clear.language", "- Dil seçimi");
        translations.addProperty("help.clear.partial_removes", "Kısmi sıfırlama seçenekleri:");
        translations.addProperty("help.clear.partial_colors", "- /toggle clear color - Tüm renkler");
        translations.addProperty("help.clear.partial_color_types", "- /toggle clear color <tür> - Belirli renk");
        translations.addProperty("help.clear.partial_toggle", "- /toggle clear toggle - Sadece geçiş ayarları");
        translations.addProperty("help.clear.partial_language", "- /toggle clear language - Sadece dil");
        translations.addProperty("help.clear.note", "Sıfırladıktan sonra her şeyi yeniden yapılandırmanız gerekecek.");

        translations.addProperty("command.clear.color.text", "Metin rengi sıfırlandı");
        translations.addProperty("command.clear.color.category", "Kategori rengi sıfırlandı");
        translations.addProperty("command.clear.color.material", "Malzeme rengi sıfırlandı");
        translations.addProperty("command.clear.color.number", "Sayı rengi sıfırlandı");
        translations.addProperty("command.clear.color.time", "Zaman rengi sıfırlandı");
        translations.addProperty("command.clear.toggle", "Geçiş ayarları sıfırlandı");
        translations.addProperty("command.clear.language", "Dil ayarı sıfırlandı");
        translations.addProperty("help.clear.partial", "/toggle clear <tür> - Belirli ayarları temizle");
        translations.addProperty("help.clear.types", "Mevcut türler: color, toggle, language");
        translations.addProperty("help.clear.color_types", "Mevcut renk türleri: text, category, material, number, time");
    }

    private void addSetCommandTranslations(JsonObject translations) {
        translations.addProperty("command.set.success", "%s, %s olarak ayarlandı");
        translations.addProperty("command.set.usage", "Kullanım: /toggle set <tür> <değer>");
        translations.addProperty("command.set.types", "Mevcut türler: object, location, category");
        translations.addProperty("command.set.invalid_stat_category", "Geçersiz istatistik kategorisi");
        translations.addProperty("command.set.invalid_location", "Geçersiz konum");
        translations.addProperty("command.set.object", "Nesne %s olarak ayarlandı");
        translations.addProperty("command.set.location", "Konum %s olarak ayarlandı");
        translations.addProperty("command.set.category", "İstatistik kategorisi %s olarak ayarlandı");
    }
}