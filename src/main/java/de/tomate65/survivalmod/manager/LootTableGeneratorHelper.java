package de.tomate65.survivalmod.manager;

import java.io.File;
import java.io.IOException;


public interface LootTableGeneratorHelper {

    /**
     *
     * @param directory
     * @throws IOException
     */
    void generateLootTableFile(File directory) throws IOException;
}