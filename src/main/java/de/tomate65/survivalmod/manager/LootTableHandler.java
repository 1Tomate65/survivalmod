package de.tomate65.survivalmod.manager;

import com.google.gson.*;
import de.tomate65.survivalmod.config.datapack.datapackgen.DatapackGen;

import java.io.*;
import java.util.*;

import static de.tomate65.survivalmod.Survivalmod.ModVersion;


public class LootTableHandler {
    public static final File LOOT_TABLE_DIR = new File("config/survival/" + ModVersion + "/loottables");
    private static final File TOGGLE_FILE = new File(LOOT_TABLE_DIR.getParentFile(), "loottable_toggles.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static final Map<String, Boolean> lootTableStates = new HashMap<>();

    public static void initialize() {
        createDirectories();
        loadToggles();
    }

    private static void createDirectories() {
        if (!LOOT_TABLE_DIR.exists()) {
            LOOT_TABLE_DIR.mkdirs();
        }
    }

    private static void loadToggles() {
        lootTableStates.clear();
        File[] availableLootTableFiles = LOOT_TABLE_DIR.listFiles((dir, name) -> name.endsWith(".json"));
        if (availableLootTableFiles == null) {
            availableLootTableFiles = new File[0];
        }

        if (!TOGGLE_FILE.exists()) {
            for (File lootTableFile : availableLootTableFiles) {
                lootTableStates.put(lootTableFile.getName().replace(".json", ""), false);
            }
            saveToggles();
            return;
        }

        try (FileReader reader = new FileReader(TOGGLE_FILE)) {
            JsonObject lootTableObject = JsonParser.parseReader(reader).getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : lootTableObject.entrySet()) {
                if (entry.getValue().isJsonPrimitive() && entry.getValue().getAsJsonPrimitive().isBoolean()) {
                    lootTableStates.put(entry.getKey(), entry.getValue().getAsBoolean());
                }
            }

            boolean needsSave = false;
            for (File lootTableFile : availableLootTableFiles) {
                String lootTableName = lootTableFile.getName().replace(".json", "");
                if (!lootTableStates.containsKey(lootTableName)) {
                    lootTableStates.put(lootTableName, false);
                    needsSave = true;
                }
            }
            if (needsSave) {
                saveToggles();
            }
        } catch (IOException | JsonSyntaxException e) {
            System.err.println("Failed to read or parse loot table toggle file: " + e.getMessage());
        }
    }

    private static void saveToggles() {
        try (FileWriter writer = new FileWriter(TOGGLE_FILE)) {
            JsonObject lootTableObject = new JsonObject();
            List<String> sortedKeys = new ArrayList<>(lootTableStates.keySet());
            Collections.sort(sortedKeys);

            for (String key : sortedKeys) {
                lootTableObject.addProperty(key, lootTableStates.get(key));
            }
            GSON.toJson(lootTableObject, writer);
        } catch (IOException e) {
            System.err.println("Failed to save loot table toggles: " + e.getMessage());
        }
    }

    /**
     *
     * @param lootTableName
     * @param enable
     * @return
     */
    public static boolean toggleLootTable(String lootTableName, boolean enable) {
        if (lootTableName.endsWith(".json")) {
            lootTableName = lootTableName.replace(".json", "");
        }
        if (!lootTableStates.containsKey(lootTableName)) {
            return false;
        }
        lootTableStates.put(lootTableName, enable);
        saveToggles();

        File datapackFolder = new File("world/datapacks/survivalmod_datapack");
        Set<String> enabledLootTables = new HashSet<>();
        lootTableStates.forEach((lootTable, enabled) -> {
            if (enabled) {
                enabledLootTables.add(lootTable + ".json");
            }
        });
        DatapackGen.registerDataToDatapack(datapackFolder, enabledLootTables, LOOT_TABLE_DIR, "loot_tables");
        return true;
    }

    public static boolean isLootTableEnabled(String lootTableName) {
        if (lootTableName.endsWith(".json")) {
            lootTableName = lootTableName.replace(".json", "");
        }
        return lootTableStates.getOrDefault(lootTableName, false);
    }

    /**
     * @return
     */
    public static int toggleAllLootTables(boolean enable) {
        for (String key : lootTableStates.keySet()) {
            lootTableStates.put(key, enable);
        }
        saveToggles();
        return lootTableStates.size();
    }

    public static Map<String, Boolean> getLootTableStates() {
        return Collections.unmodifiableMap(lootTableStates);
    }

    public static List<String> getAllLootTableFiles() {
        return new ArrayList<>(lootTableStates.keySet());
    }
}