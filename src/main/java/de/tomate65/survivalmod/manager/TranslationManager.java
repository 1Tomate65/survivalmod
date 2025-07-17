package de.tomate65.survivalmod.manager;

import de.tomate65.survivalmod.translation.*;
import de.tomate65.survivalmod.config.ConfigReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TranslationManager {
    private static final List<TranslationGenerator> generators = new ArrayList<>();

    static {
        generators.add(new EnUsTranslationGenerator());
        generators.add(new EnGbTranslationGenerator());
        generators.add(new DeDeTranslationGenerator());
        generators.add(new FrFrTranslationGenerator());
        generators.add(new EsEsTranslationGenerator());
        generators.add(new NlNlTranslationGenerator());
        generators.add(new RuRuTranslationGenerator());
        generators.add(new HiInTranslationGenerator());
        generators.add(new EnPtTranslationGenerator());
        generators.add(new ZhCnTranslationGenerator());
        generators.add(new TrTrTranslationGenerator());
        generators.add(new ItItTranslationGenerator());
        generators.add(new JaJpTranslationGenerator());
        //generators.add(new ArSaTranslationGenerator());
        generators.add(new PlPlTranslationGenerator());
        generators.add(new KoKrTranslationGenerator());
        generators.add(new DeChTranslationGenerator());
        generators.add(new DeAtTranslationGenerator());
    }

    public static void generateAllTranslations(File langDir) {
        if (!langDir.exists() && !langDir.mkdirs()) {
            System.err.println("Failed to create language directory");
            return;
        }

        ConfigReader.loadConfig();

        for (TranslationGenerator generator : generators) {
            String langCode = generator.getLanguageCode(); // Use the generator's method directly
            if (ConfigReader.isLanguageEnabled(langCode)) {
                generator.generateLangFile(langDir);
            }
        }
    }

    private static String getLangCodeFromGenerator(TranslationGenerator generator) {
        // Convert class name like "EnUsTranslationGenerator" to "en_us"
        String className = generator.getClass().getSimpleName();
        String code = className.replace("TranslationGenerator", "");
        // Insert underscore between lowercase and uppercase letters
        code = code.replaceAll("([a-z])([A-Z])", "$1_$2");
        return code.toLowerCase();
    }

    public static List<String> getAvailableLanguageCodes() {
        List<String> codes = new ArrayList<>();
        for (TranslationGenerator generator : generators) {
            codes.add(getLangCodeFromGenerator(generator));
        }
        return codes;
    }
}