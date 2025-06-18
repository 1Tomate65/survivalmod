package de.tomate65.survivalmod.translation;

import com.google.gson.JsonObject;

public class HiInTranslationGenerator extends TranslationGenerator {
    @Override
    public String getLanguageCode() {
        return "hi_in";
    }

    @Override
    public JsonObject generateTranslations() {
        JsonObject translations = new JsonObject();

        // Time units
        translations.addProperty("time.years", "वर्ष");
        translations.addProperty("time.days", "दिन");
        translations.addProperty("time.hours", "घंटे");
        translations.addProperty("time.minutes", "मिनट");
        translations.addProperty("time.seconds", "सेकंड");

        // Command feedback
        translations.addProperty("command.disabled", "कमांड अक्षम");
        translations.addProperty("command.player_only", "केवल खिलाड़ी ही इस कमांड को चला सकते हैं");
        translations.addProperty("command.invalid_language", "अमान्य भाषा कोड");
        translations.addProperty("command.language_disabled", "भाषा %s वर्तमान में कॉन्फ़िग में अक्षम है");
        translations.addProperty("command.invalid_toggle", "यह टॉगल वर्तमान मोड (%s) में अनुमति नहीं है");
        translations.addProperty("command.invalid_hex_color", "अमान्य हेक्स रंग प्रारूप। RRGGBB या #RRGGBB का उपयोग करें");
        translations.addProperty("command.error_saving", "आपकी %s प्राथमिकता सहेजने में त्रुटि");
        translations.addProperty("command.reset_success", "आपकी टॉगल प्राथमिकताएँ रीसेट हो गई हैं");
        translations.addProperty("command.reset_failed", "आपकी टॉगल प्राथमिकताएँ रीसेट करने में विफल");
        translations.addProperty("command.reset_none", "रीसेट करने के लिए आपकी कोई टॉगल प्राथमिकताएँ नहीं थीं");
        translations.addProperty("command.reloaded", "कॉन्फ़िगरेशन पुनः लोड हो गया");
        translations.addProperty("command.no_content", "इस सबकमांड के लिए कोई सामग्री उपलब्ध नहीं है");
        translations.addProperty("command.no_subcommands", "कोई सबकमांड उपलब्ध नहीं है। अपनी कॉन्फ़िगरेशन जांचें");
        translations.addProperty("command.available_commands", "उपलब्ध सबकमांड:");
        translations.addProperty("command.usage", "उपयोग: %s");
        translations.addProperty("command.available_colors", "उपलब्ध रंग: %s");
        translations.addProperty("command.available_languages", "उपलब्ध भाषाएँ: %s");
        translations.addProperty("command.hex_format", "हेक्स प्रारूप RRGGBB या #RRGGBB हो सकता है");
        translations.addProperty("command.reset_partial", "आपकी %s सेटिंग्स साफ़ हो गईं");
        translations.addProperty("command.set_usage.title", "=== सेट कमांड उपयोग ===");
        translations.addProperty("command.set_usage.object", "/toggle set object <वस्तु_आईडी>");
        translations.addProperty("command.set_usage.location", "/toggle set location <actionbar|chat|title>");
        translations.addProperty("command.set_usage.category", "/toggle set category <सांख्यिकी_श्रेणी>");

        translations.addProperty("message.action.mined", "खोदा");
        translations.addProperty("message.action.crafted", "बनाया");
        translations.addProperty("message.action.used", "इस्तेमाल किया");
        translations.addProperty("message.action.broken", "तोड़ा");
        translations.addProperty("message.action.picked_up", "उठाया");
        translations.addProperty("message.action.dropped", "गिराया");
        translations.addProperty("message.action.killed", "को मारा");
        translations.addProperty("message.action.killed_by", "द्वारा मारे गए");
        translations.addProperty("message.action.custom", "में एक मील का पत्थर हासिल किया");
        translations.addProperty("message.you", "आपने");
        translations.addProperty("message.have", "");
        translations.addProperty("message.plural", "");
        translations.addProperty("message.exclamation", "!");

        // Command success messages
        translations.addProperty("timeplayed.set", "टाइमप्लेयड %s में प्रदर्शित होगा");
        translations.addProperty("language.set", "भाषा %s पर सेट की गई");
        translations.addProperty("color.set", "%s का रंग %s पर सेट किया गया");
        translations.addProperty("color.reset", "%s का रंग डिफ़ॉल्ट पर रीसेट किया गया");
        translations.addProperty("toggle.set", "%s टॉगल %s में %s सांख्यिकी के साथ प्रदर्शित होगा");

        // Color command parts
        translations.addProperty("color.set.part1", "सेट ");
        translations.addProperty("color.set.part2", " रंग ");
        translations.addProperty("color.reset.part1", "रीसेट ");
        translations.addProperty("color.reset.part2", " रंग डिफ़ॉल्ट पर");

        // Item names
        translations.addProperty("item.stone.name", "पत्थर");
        translations.addProperty("item.dirt.name", "मिट्टी");
        translations.addProperty("item.oak_log.name", "ओक लकड़ी");

        // Stat categories
        translations.addProperty("stat.timeplayed", "खेला गया समय");
        translations.addProperty("stat.mined", "खोदा गया");
        translations.addProperty("stat.crafted", "बनाया गया");
        translations.addProperty("stat.used", "उपयोग किया गया");
        translations.addProperty("stat.broken", "तोड़ा गया");
        translations.addProperty("stat.picked_up", "उठाया गया");
        translations.addProperty("stat.dropped", "गिराया गया");
        translations.addProperty("stat.killed", "मारा गया");
        translations.addProperty("stat.killed_by", "द्वारा मारा गया");
        translations.addProperty("stat.custom", "कस्टम");

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
        translations.addProperty("help.general.title", "=== टॉगल कमांड सहायता ===");
        translations.addProperty("help.general.materials", "/toggle help materials - उपलब्ध सामग्री श्रेणियाँ दिखाएँ");
        translations.addProperty("help.general.color", "/toggle help color - रंग अनुकूलन सहायता दिखाएँ");
        translations.addProperty("help.general.clear", "/toggle help clear - अपनी सेटिंग्स रीसेट करने का तरीका दिखाएँ");
        translations.addProperty("help.general.language", "/toggle help language - भाषा विकल्प दिखाएँ");
        translations.addProperty("help.general.usage", "उपयोग: /toggle <सामग्री> <स्थान> [सांख्यिकी_श्रेणी]");

        translations.addProperty("help.materials.title", "=== सामग्री श्रेणियाँ ===");
        translations.addProperty("help.materials.header", "उपलब्ध सामग्री प्रकार:");
        translations.addProperty("help.materials.mobs", "मॉब्स - सभी जीवित इकाइयाँ (ज़ोंबी, क्रीपर, आदि)");
        translations.addProperty("help.materials.blocks", "ब्लॉक्स - सभी रखने योग्य ब्लॉक (पत्थर, मिट्टी, आदि)");
        translations.addProperty("help.materials.items", "आइटम्स - सभी आइटम (तलवार, भोजन, औजार, आदि)");
        translations.addProperty("help.materials.all", "सभी - सब कुछ उपलब्ध");
        translations.addProperty("help.materials.usage", "उपयोग: /toggle <सामग्री_आईडी> <actionbar|chat|title> [सांख्यिकी_श्रेणी]");

        translations.addProperty("help.color.title", "=== रंग अनुकूलन ===");
        translations.addProperty("help.color.header", "विभिन्न पाठ तत्वों के लिए रंग बदलें:");
        translations.addProperty("help.color.text", "/toggle color text <रंग> - मुख्य पाठ रंग");
        translations.addProperty("help.color.category", "/toggle color category <रंग> - सांख्यिकी श्रेणी रंग");
        translations.addProperty("help.color.material", "/toggle color material <रंग> - सामग्री नाम रंग");
        translations.addProperty("help.color.number", "/toggle color number <रंग> - संख्या रंग");
        translations.addProperty("help.color.time", "/toggle color time <रंग> - खेलने का समय प्रदर्शन रंग");
        translations.addProperty("help.color.colors", "रंग हो सकते हैं: RED, GREEN, BLUE, YELLOW, आदि");
        translations.addProperty("help.color.hex", "या हेक्स मानों का उपयोग करें: '#FF0000' (लाल), '#00FF00' (हरा)");
        translations.addProperty("help.color.none", "डिफ़ॉल्ट पर रीसेट करने के लिए 'NONE' का उपयोग करें");

        translations.addProperty("help.language.title", "=== भाषा विकल्प ===");
        translations.addProperty("help.language.available", "उपलब्ध भाषाएँ:");
        translations.addProperty("help.language.change", "अपनी प्रदर्शन भाषा बदलें:");
        translations.addProperty("help.language.command", "/toggle language <भाषा_कोड>");
        translations.addProperty("help.language.example", "उदाहरण: /toggle language hi_in");

        translations.addProperty("survivalmod.info.running", "आप सर्वाइवल मोड का %s संस्करण चला रहे हैं");
        translations.addProperty("survivalmod.info.update_available", "एक अपडेट उपलब्ध है!");
        translations.addProperty("survivalmod.info.no_update", "अभी तक कोई अपडेट नहीं आया है।");
        translations.addProperty("survivalmod.info.modrinth_link", "मॉडरिंथ पेज पर जाने के लिए यहां क्लिक करें");
    }

    private void addMagnetTranslations(JsonObject translations) {
        translations.addProperty("magnet.activated", "चुंबक सक्रिय - %d ब्लॉक त्रिज्या में आइटम आकर्षित कर रहा है");
        translations.addProperty("magnet.deactivated", "चुंबक निष्क्रिय");
        translations.addProperty("magnet.already_active", "चुंबक पहले से ही सक्रिय है");
        translations.addProperty("magnet.already_inactive", "चुंबक पहले से ही निष्क्रिय है");
    }

    private void addClearCommandTranslations(JsonObject translations) {
        translations.addProperty("help.clear.title", "=== सेटिंग्स रीसेट करें ===");
        translations.addProperty("help.clear.header", "अपनी टॉगल प्राथमिकताएँ रीसेट करें:");
        translations.addProperty("help.clear.command", "/toggle clear - सभी सेटिंग्स रीसेट करें");
        translations.addProperty("help.clear.partial_command", "/toggle clear <प्रकार> - विशिष्ट सेटिंग्स रीसेट करें");
        translations.addProperty("help.clear.removes", "पूर्ण साफ़ करने से हटाए जाते हैं:");
        translations.addProperty("help.clear.toggles", "- आपकी वर्तमान टॉगल सेटिंग्स");
        translations.addProperty("help.clear.colors", "- सभी रंग प्राथमिकताएँ");
        translations.addProperty("help.clear.language", "- भाषा चयन");
        translations.addProperty("help.clear.partial_removes", "आंशिक साफ़ करने के विकल्प:");
        translations.addProperty("help.clear.partial_colors", "- /toggle clear color - सभी रंग");
        translations.addProperty("help.clear.partial_color_types", "- /toggle clear color <प्रकार> - विशिष्ट रंग");
        translations.addProperty("help.clear.partial_toggle", "- /toggle clear toggle - केवल टॉगल सेटिंग्स");
        translations.addProperty("help.clear.partial_language", "- /toggle clear language - केवल भाषा");
        translations.addProperty("help.clear.note", "साफ़ करने के बाद आपको चीजों को फिर से सेट करना होगा");

        translations.addProperty("command.clear.color.text", "पाठ रंग साफ़ किया गया");
        translations.addProperty("command.clear.color.category", "श्रेणी रंग साफ़ किया गया");
        translations.addProperty("command.clear.color.material", "सामग्री रंग साफ़ किया गया");
        translations.addProperty("command.clear.color.number", "संख्या रंग साफ़ किया गया");
        translations.addProperty("command.clear.color.time", "समय रंग साफ़ किया गया");
        translations.addProperty("command.clear.toggle", "टॉगल सेटिंग्स साफ़ की गईं");
        translations.addProperty("command.clear.language", "भाषा सेटिंग साफ़ की गई");
        translations.addProperty("help.clear.partial", "/toggle clear <प्रकार> - विशिष्ट सेटिंग्स साफ़ करें");
        translations.addProperty("help.clear.types", "उपलब्ध प्रकार: color, toggle, language");
        translations.addProperty("help.clear.color_types", "उपलब्ध रंग प्रकार: text, category, material, number, time");
    }

    private void addSetCommandTranslations(JsonObject translations) {
        translations.addProperty("command.set.success", "%s को %s पर सेट किया");
        translations.addProperty("command.set.usage", "उपयोग: /toggle set <प्रकार> <मान>");
        translations.addProperty("command.set.types", "उपलब्ध प्रकार: object, location, category");
        translations.addProperty("command.set.invalid_stat_category", "अमान्य सांख्यिकी श्रेणी");
        translations.addProperty("command.set.invalid_location", "अमान्य स्थान");
        translations.addProperty("command.set.object", "ऑब्जेक्ट %s पर सेट किया");
        translations.addProperty("command.set.location", "स्थान %s पर सेट किया");
        translations.addProperty("command.set.category", "सांख्यिकी श्रेणी %s पर सेट की गई");
    }
}