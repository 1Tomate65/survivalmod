package de.tomate65.survivalmod.translation;

import com.google.gson.JsonObject;

public class ArSaTranslationGenerator extends TranslationGenerator {
    @Override
    public String getLanguageCode() {
        return "ar_sa";
    }

    @Override
    public JsonObject generateTranslations() {
        JsonObject translations = new JsonObject(); // Needs a special case (0.3.2 Update)

        // Time units (وحدات الوقت)
        translations.addProperty("time.years", "س"); // y
        translations.addProperty("time.days", "ي");  // d
        translations.addProperty("time.hours", "ساعة"); // h
        translations.addProperty("time.minutes", "دقيقة"); // m
        translations.addProperty("time.seconds", "ثانية"); // s

        // Command feedback (ردود فعل الأوامر)
        translations.addProperty("command.disabled", "الأمر معطل");
        translations.addProperty("command.player_only", "يجب تنفيذ الأمر بواسطة لاعب فقط");
        translations.addProperty("command.language_disabled", "اللغة %s معطلة حاليًا في الإعدادات");
        translations.addProperty("command.invalid_language", "رمز لغة غير صالح");
        translations.addProperty("command.invalid_toggle", "هذا التبديل غير مسموح به في الوضع الحالي (%s)");
        translations.addProperty("command.invalid_hex_color", "تنسيق لون سداسي عشري غير صالح. استخدم RRGGBB أو #RRGGBB");
        translations.addProperty("command.error_saving", "حدث خطأ أثناء حفظ تفضيل %s الخاص بك.");
        translations.addProperty("command.reset_success", "تمت إعادة تعيين تفضيلات التبديل الخاصة بك.");
        translations.addProperty("command.reset_failed", "فشل في إعادة تعيين تفضيلات التبديل.");
        translations.addProperty("command.reload_success", "تمت إعادة تحميل الإعدادات بنجاح.");
        translations.addProperty("command.reload_failed", "فشل في إعادة تحميل الإعدادات.");
        translations.addProperty("command.backup_created", "تم إنشاء نسخة احتياطية يدوية بنجاح.");
        translations.addProperty("command.backup_failed", "فشل النسخ الاحتياطي: %s");

        // Help command (أمر المساعدة)
        translations.addProperty("help.general", "الأوامر المتاحة:");
        translations.addProperty("help.toggle", "/toggle - تبديل عرض HUD");
        translations.addProperty("help.toggle.info", "/toggle info - عرض إعدادات التبديل الحالية");
        translations.addProperty("help.toggle.set", "/toggle set <النوع> <القيمة> - تعيين إعداد معين");
        translations.addProperty("help.toggle.clear", "/toggle clear <النوع> - مسح إعدادات معينة");
        translations.addProperty("help.toggle.color", "/toggle color <النوع> <اللون> - تعيين لون نص HUD");
        translations.addProperty("help.toggle.language", "/toggle language <رمز اللغة> - تعيين لغة HUD");
        translations.addProperty("help.survival", "/survival - أمر معلومات البقاء");
        translations.addProperty("help.survival.subcommand", "/survival <الأمر الفرعي> - عرض معلومات بقاء محددة");
        translations.addProperty("help.recipetoggle", "/recipetoggle - تمكين/تعطيل الوصفات");
        translations.addProperty("help.recipetoggle.enable", "/recipetoggle enable <معرف الوصفة> - تمكين وصفة معينة");
        translations.addProperty("help.recipetoggle.disable", "/recipetoggle disable <معرف الوصفة> - تعطيل وصفة معينة");
        translations.addProperty("help.recipetoggle.list", "/recipetoggle list - قائمة الوصفات المتاحة");
        translations.addProperty("help.recipetoggle.enable_all", "/recipetoggle enable_all - تمكين جميع الوصفات المخصصة");
        translations.addProperty("help.recipetoggle.disable_all", "/recipetoggle disable_all - تعطيل جميع الوصفات المخصصة");
        translations.addProperty("help.backup", "/survival backup - نسخ إعداداتك احتياطيًا يدويًا");

        // Toggle command feedback (ردود فعل أمر التبديل)
        translations.addProperty("command.clear.color.text", "تم مسح لون النص");
        translations.addProperty("command.clear.color.category", "تم مسح لون الفئة");
        translations.addProperty("command.clear.color.material", "تم مسح لون المادة");
        translations.addProperty("command.clear.color.number", "تم مسح لون الرقم");
        translations.addProperty("command.clear.color.time", "تم مسح لون الوقت");
        translations.addProperty("command.clear.toggle", "تم مسح إعدادات التبديل");
        translations.addProperty("command.clear.language", "تم مسح إعداد اللغة");
        translations.addProperty("help.clear.partial", "/toggle clear <النوع> - مسح إعدادات معينة");
        translations.addProperty("help.clear.types", "الأنواع المتاحة: color, toggle, language");
        translations.addProperty("help.clear.color_types", "أنواع الألوان المتاحة: text, category, material, number, time");

        translations.addProperty("command.set.success", "تم تعيين %s إلى %s");
        translations.addProperty("command.set.usage", "الاستخدام: /toggle set <النوع> <القيمة>");
        translations.addProperty("command.set.types", "الأنواع المتاحة: object, location, category");
        translations.addProperty("command.set.invalid_stat_category", "فئة إحصائية غير صالحة");
        translations.addProperty("command.set.invalid_location", "موقع غير صالح");
        translations.addProperty("command.set.object", "تم تعيين الكائن إلى %s");
        translations.addProperty("command.set.location", "تم تعيين الموقع إلى %s");
        translations.addProperty("command.set.category", "تم تعيين فئة الإحصائيات إلى %s");

        // Toggle info (معلومات التبديل)
        translations.addProperty("toggle.info.current_object", "الكائن الحالي: %s");
        translations.addProperty("toggle.info.current_location", "الموقع الحالي: %s");
        translations.addProperty("toggle.info.current_category", "الفئة الحالية: %s");
        translations.addProperty("toggle.info.current_language", "اللغة الحالية: %s");
        translations.addProperty("toggle.info.colors", "الألوان:");
        translations.addProperty("toggle.info.text_color", "النص: %s");
        translations.addProperty("toggle.info.category_color", "الفئة: %s");
        translations.addProperty("toggle.info.material_color", "المادة: %s");
        translations.addProperty("toggle.info.number_color", "الرقم: %s");
        translations.addProperty("toggle.info.time_color", "الوقت: %s");
        translations.addProperty("toggle.info.toggle_enabled", "التبديل ممكن");
        translations.addProperty("toggle.info.toggle_disabled", "التبديل معطل");

        // Toggle categories (فئات التبديل)
        translations.addProperty("toggle.category.mined", "تم التعدين");
        translations.addProperty("toggle.category.broken", "تم الكسر");
        translations.addProperty("toggle.category.crafted", "تم الصنع");
        translations.addProperty("toggle.category.used", "تم الاستخدام");
        translations.addProperty("toggle.category.picked_up", "تم الالتقاط");
        translations.addProperty("toggle.category.dropped", "تم الإسقاط");
        translations.addProperty("toggle.category.killed", "تم القتل");
        translations.addProperty("toggle.category.killed_by", "قتل بواسطة");
        translations.addProperty("toggle.category.custom", "مخصص");
        translations.addProperty("toggle.category.time", "الوقت");
        translations.addProperty("toggle.category.magnet", "المغناطيس");

        // Toggle locations (مواقع التبديل)
        translations.addProperty("toggle.location.actionbar", "شريط الإجراءات");
        translations.addProperty("toggle.location.title", "العنوان");
        translations.addProperty("toggle.location.subtitle", "العنوان الفرعي");
        translations.addProperty("toggle.location.chat", "الدردشة");

        // Recipe command feedback (ردود فعل أمر الوصفة)
        translations.addProperty("recipe.toggle.enabled", "الوصفة %s ممكّنة. قم بتشغيل /reload لتطبيق التغييرات.");
        translations.addProperty("recipe.toggle.disabled", "الوصفة %s معطلة. قم بتشغيل /reload لتطبيق التغييرات.");
        translations.addProperty("recipe.file_not_found", "ملف الوصفة غير موجود: %s");
        translations.addProperty("recipe.no_custom_recipes", "لم يتم العثور على وصفات مخصصة.");
        translations.addProperty("recipe.available_recipes", "الوصفات المتاحة:");
        translations.addProperty("recipe.status_enabled", "ممكّن");
        translations.addProperty("recipe.status_disabled", "معطل");
        translations.addProperty("recipe.toggle_all_enabled", "تم تمكين جميع الوصفات المخصصة (%d). قم بتشغيل /reload لتطبيق التغييرات.");
        translations.addProperty("recipe.toggle_all_disabled", "تم تعطيل جميع الوصفات المخصصة (%d). قم بتشغيل /reload لتطبيق التغييرات.");

        // Survival Command (أمر البقاء)
        translations.addProperty("survival.no_content", "لا يوجد محتوى متاح لهذا الأمر الفرعي.");
        translations.addProperty("survival.available_subcommands", "الأوامر الفرعية المتاحة:");
        translations.addProperty("survival.no_subcommands", "لا توجد أوامر فرعية متاحة. تحقق من إعداداتك.");
        translations.addProperty("survival.update_available", "يتوفر تحديث جديد! الحالي: %s, الأحدث: %s");
        translations.addProperty("survival.no_update", "لا يوجد تحديث جديد.");
        translations.addProperty("survival.update_check_failed", "فشل التحقق من التحديث.");

        return translations;
    }
}
