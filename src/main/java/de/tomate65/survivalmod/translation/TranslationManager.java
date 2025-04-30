package de.tomate65.survivalmod.translation;

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
    }

    public static void generateAllTranslations(File langDir) {
        if (!langDir.exists() && !langDir.mkdirs()) {
            System.err.println("Failed to create language directory");
            return;
        }

        for (TranslationGenerator generator : generators) {
            generator.generateLangFile(langDir);
        }
    }
}