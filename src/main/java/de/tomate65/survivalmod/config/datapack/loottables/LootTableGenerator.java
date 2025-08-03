package de.tomate65.survivalmod.config.datapack.loottables;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import de.tomate65.survivalmod.config.ConfigReader;
import de.tomate65.survivalmod.manager.LootTableGeneratorHelper;
import de.tomate65.survivalmod.manager.LootTableHandler;
import net.minecraft.server.command.ServerCommandSource;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LootTableGenerator {
    private static final List<LootTableGeneratorHelper> helpers = new ArrayList<>();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    static {
        helpers.add(new DessertLootTable());
    }

    /**
     *
     * @param lootTableDir
     */
    public static void generateAllLootTables(File lootTableDir) {
        if (!lootTableDir.exists() && !lootTableDir.mkdirs()) {
            System.err.println("[SurvivalMod] Failed to create loot table directory at: " + lootTableDir.getAbsolutePath());
            return;
        }

        int generatedCount = 0;
        for (LootTableGeneratorHelper helper : helpers) {
            String lootTableId = getLootTableIdFromHelper(helper);
            // Annahme: Es gibt eine ähnliche Methode für Loottables in ConfigReader
            if (ConfigReader.isLootTableEnabled(lootTableId)) {
                try {
                    helper.generateLootTableFile(lootTableDir);
                    generatedCount++;
                } catch (Exception e) {
                    System.err.println("[SurvivalMod] Failed to generate loot table " + lootTableId + ": " + e.getMessage());
                }
            }
        }
        System.out.println("[SurvivalMod] Successfully generated " + generatedCount + " loot tables.");
    }

    private static String getLootTableIdFromHelper(LootTableGeneratorHelper helper) {
        String className = helper.getClass().getSimpleName();
        return className.replace("LootTable", "")
                .replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }

    public static List<String> getAvailableLootTableIds() {
        List<String> ids = new ArrayList<>();
        for (LootTableGeneratorHelper helper : helpers) {
            ids.add(getLootTableIdFromHelper(helper));
        }
        return ids;
    }

    public static void writeLootTableFile(File outputFile, Object lootTableData) throws IOException {
        outputFile.getParentFile().mkdirs();
        try (FileWriter writer = new FileWriter(outputFile)) {
            GSON.toJson(lootTableData, writer);
        }
    }

    private static final SuggestionProvider<ServerCommandSource> LOOT_TABLE_SUGGESTIONS =
            (context, builder) -> {
                builder.suggest("*");

                List<String> lootTables = LootTableHandler.getAllLootTableFiles();
                for (String lootTable : lootTables) {
                    String lootTableId = lootTable.replace(".json", "");
                    builder.suggest(lootTableId);
                }
                return CompletableFuture.completedFuture(builder.build());
            };
}