package de.tomate65.survivalmod.manager;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.tomate65.survivalmod.Survivalmod;
import de.tomate65.survivalmod.config.ConfigReader;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

public class SurvivalInfoManager {
    public static final String MOD_VERSION = ConfigReader.getModVersion();
    private static final String MODRINTH_API_URL = "https://api.modrinth.com/v2/project/survivalmod/version";
    private static String latestVersion = null;

    public static boolean isUpdateAvailable() {
        try {
            String currentVersion = MOD_VERSION;
            String latest = getLatestVersionFromModrinth();

            if (latest == null) {
                Survivalmod.LOGGER.warn("Could not determine latest version from Modrinth");
                return false;
            }

            Survivalmod.LOGGER.info("Version check - Current: " + currentVersion + " Latest: " + latest);
            return compareVersions(currentVersion, latest) < 0;
        } catch (Exception e) {
            Survivalmod.LOGGER.error("Failed to check for updates: " + e.getMessage());
            return false;
        }
    }

    private static String getModVersion() {
        Optional<ModContainer> modContainer = FabricLoader.getInstance().getModContainer("survivalmod");
        return modContainer.map(container -> container.getMetadata().getVersion().getFriendlyString())
                .orElse("Unknown");
    }

    private static String getLatestVersionFromModrinth() {
        if (latestVersion != null) {
            return latestVersion;
        }

        try {
            URL url = new URL(MODRINTH_API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "SurvivalMod/" + MOD_VERSION);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                JsonArray versions = JsonParser.parseReader(new InputStreamReader(connection.getInputStream()))
                        .getAsJsonArray();

                if (versions.size() > 0) {
                    JsonObject latest = versions.get(0).getAsJsonObject();
                    String fullVersion = latest.get("version_number").getAsString();

                    // Extract just the mod version part (0.2.4 from survivalmod-1.21.5-0.2.4)
                    String[] parts = fullVersion.split("-");
                    if (parts.length >= 3) {
                        latestVersion = parts[2]; // Gets the 0.2.4 part
                        return latestVersion;
                    } else {
                        Survivalmod.LOGGER.warn("Unexpected version format from Modrinth: " + fullVersion);
                    }
                }
            }
        } catch (IOException e) {
            Survivalmod.LOGGER.warn("Failed to fetch latest version from Modrinth: " + e.getMessage());
        }

        return null;
    }

    private static int compareVersions(String current, String latest) {
        String[] currentParts = current.split("\\.");
        String[] latestParts = latest.split("\\.");

        int length = Math.max(currentParts.length, latestParts.length);
        for (int i = 0; i < length; i++) {
            int currentPart = i < currentParts.length ? Integer.parseInt(currentParts[i]) : 0;
            int latestPart = i < latestParts.length ? Integer.parseInt(latestParts[i]) : 0;

            if (currentPart < latestPart) return -1;
            if (currentPart > latestPart) return 1;
        }
        return 0;
    }
}