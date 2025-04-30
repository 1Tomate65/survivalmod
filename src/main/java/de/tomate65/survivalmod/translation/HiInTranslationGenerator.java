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

        // समय इकाइयाँ
        translations.addProperty("time.years", "वर्ष");
        translations.addProperty("time.days", "दिन");
        translations.addProperty("time.hours", "घंटे");
        translations.addProperty("time.minutes", "मिनट");
        translations.addProperty("time.seconds", "सेकंड");

        // आइटम नाम
        translations.addProperty("item.stone.name", "पत्थर");
        translations.addProperty("item.dirt.name", "मिट्टी");
        translations.addProperty("item.oak_log.name", "ओक लॉग");

        // स्टैट श्रेणियाँ
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

        // सहायता प्रणाली
        addHelpTranslations(translations);

        // चुंबक प्रणाली
        addMagnetTranslations(translations);

        // क्लियर कमांड
        addClearCommandTranslations(translations);

        // सेट कमांड
        addSetCommandTranslations(translations);

        return translations;
    }

    private void addHelpTranslations(JsonObject translations) {
        translations.addProperty("help.general.title", "=== टॉगल कमांड सहायता ===");
        translations.addProperty("help.general.materials", "/toggle help materials - उपलब्ध सामग्री श्रेणियाँ दिखाएं");
        translations.addProperty("help.general.color", "/toggle help color - रंग अनुकूलन सहायता दिखाएं");
        translations.addProperty("help.general.clear", "/toggle help clear - अपनी सेटिंग्स रीसेट करने का तरीका दिखाएं");
        translations.addProperty("help.general.language", "/toggle help language - भाषा विकल्प दिखाएं");
        translations.addProperty("help.general.usage", "उपयोग: /toggle <सामग्री> <स्थान> [स्टैट_श्रेणी]");

        translations.addProperty("help.materials.title", "=== सामग्री श्रेणियाँ ===");
        translations.addProperty("help.materials.header", "उपलब्ध सामग्री प्रकार:");
        translations.addProperty("help.materials.mobs", "मॉब्स - सभी जीवित इकाइयाँ (ज़ोंबी, क्रीपर, आदि)");
        translations.addProperty("help.materials.blocks", "ब्लॉक्स - सभी रखने योग्य ब्लॉक (पत्थर, मिट्टी, आदि)");
        translations.addProperty("help.materials.items", "आइटम्स - सभी वस्तुएँ (तलवार, भोजन, उपकरण, आदि)");
        translations.addProperty("help.materials.all", "सभी - सब कुछ उपलब्ध");
        translations.addProperty("help.materials.usage", "उपयोग: /toggle <सामग्री_आईडी> <एक्शनबार|चैट|टाइटल> [स्टैट_श्रेणी]");

        translations.addProperty("help.color.title", "=== रंग अनुकूलन ===");
        translations.addProperty("help.color.header", "विभिन्न पाठ तत्वों के लिए रंग बदलें:");
        translations.addProperty("help.color.text", "/toggle color text <रंग> - मुख्य पाठ रंग");
        translations.addProperty("help.color.category", "/toggle color category <रंग> - स्टैट श्रेणी रंग");
        translations.addProperty("help.color.material", "/toggle color material <रंग> - सामग्री नाम रंग");
        translations.addProperty("help.color.number", "/toggle color number <रंग> - संख्या रंग");
        translations.addProperty("help.color.time", "/toggle color time <रंग> - खेलने का समय प्रदर्शन रंग");
        translations.addProperty("help.color.colors", "रंग हो सकते हैं: लाल, हरा, नीला, पीला, आदि");
        translations.addProperty("help.color.hex", "या हेक्स मानों का उपयोग करें: '#FF0000' (लाल), '#00FF00' (हरा)");
        translations.addProperty("help.color.none", "डिफ़ॉल्ट पर रीसेट करने के लिए 'NONE' का उपयोग करें");

        translations.addProperty("help.language.title", "=== भाषा विकल्प ===");
        translations.addProperty("help.language.available", "उपलब्ध भाषाएँ:");
        translations.addProperty("help.language.change", "अपनी प्रदर्शन भाषा बदलें:");
        translations.addProperty("help.language.command", "/toggle language <भाषा_कोड>");
        translations.addProperty("help.language.example", "उदाहरण: /toggle language hi_in");
    }

    private void addMagnetTranslations(JsonObject translations) {
        translations.addProperty("magnet.activated", "चुंबक सक्रिय - %d ब्लॉक के दायरे में वस्तुएँ आकर्षित कर रहा है");
        translations.addProperty("magnet.deactivated", "चुंबक निष्क्रिय");
        translations.addProperty("magnet.already_active", "चुंबक पहले से ही सक्रिय है");
        translations.addProperty("magnet.already_inactive", "चुंबक पहले से ही निष्क्रिय है");
    }

    private void addClearCommandTranslations(JsonObject translations) {
        translations.addProperty("help.clear.title", "=== सेटिंग्स रीसेट करें ===");
        translations.addProperty("help.clear.header", "अपनी प्राथमिकताएँ रीसेट करें:");
        translations.addProperty("help.clear.command", "/toggle clear - सभी सेटिंग्स रीसेट करें");
        translations.addProperty("help.clear.partial_command", "/toggle clear <प्रकार> - विशिष्ट सेटिंग्स रीसेट करें");
        translations.addProperty("help.clear.removes", "पूर्ण रीसेट हटाता है:");
        translations.addProperty("help.clear.toggles", "- आपकी वर्तमान सेटिंग्स");
        translations.addProperty("help.clear.colors", "- सभी रंग प्राथमिकताएँ");
        translations.addProperty("help.clear.language", "- भाषा चयन");
        translations.addProperty("help.clear.partial_removes", "आंशिक रीसेट विकल्प:");
        translations.addProperty("help.clear.partial_colors", "- /toggle clear color - सभी रंग");
        translations.addProperty("help.clear.partial_color_types", "- /toggle clear color <प्रकार> - विशिष्ट रंग");
        translations.addProperty("help.clear.partial_toggle", "- /toggle clear toggle - केवल टॉगल सेटिंग्स");
        translations.addProperty("help.clear.partial_language", "- /toggle clear language - केवल भाषा");
        translations.addProperty("help.clear.note", "रीसेट करने के बाद आपको चीजों को फिर से सेट करना होगा।");

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
        translations.addProperty("command.set.success", "%s को %s पर सेट किया गया");
        translations.addProperty("command.set.usage", "उपयोग: /toggle set <प्रकार> <मान>");
        translations.addProperty("command.set.types", "उपलब्ध प्रकार: object, location, category");
        translations.addProperty("command.set.invalid_stat_category", "अमान्य स्टैट श्रेणी");
        translations.addProperty("command.set.invalid_location", "अमान्य स्थान");
        translations.addProperty("command.set.object", "ऑब्जेक्ट को %s पर सेट किया गया");
        translations.addProperty("command.set.location", "स्थान को %s पर सेट किया गया");
        translations.addProperty("command.set.category", "स्टैट श्रेणी को %s पर सेट किया गया");
    }
}