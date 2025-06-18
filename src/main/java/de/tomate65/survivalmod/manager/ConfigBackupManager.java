package de.tomate65.survivalmod.manager;

import com.google.gson.JsonObject;
import de.tomate65.survivalmod.config.ConfigGenerator;
import de.tomate65.survivalmod.config.ConfigReader;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Supplier;
import java.util.zip.*;

import static de.tomate65.survivalmod.manager.UpdateHelper.findHighestExistingVersion;

public class ConfigBackupManager {
    private static ConfigBackupManager instance;
    private final File backupRoot;
    private final File configRoot;
    private final String backupPrefix = "backup_";
    private final String backupExt = ".zip";

    // Configuration with defaults
    private boolean enabled = true;
    private int maxBackups = 5;
    private int minBackupIntervalHours = 24;
    private boolean backupOnVersionChange = true;

    public static ConfigBackupManager getInstance() {
        if (instance == null) {
            instance = new ConfigBackupManager(
                    new File(FabricLoader.getInstance().getConfigDir().toFile(), "survival")
            );
        }
        return instance;
    }

    private ConfigBackupManager(File configRoot) {
        this.configRoot = configRoot;
        this.backupRoot = new File(configRoot, ".backups");
    }

    public void loadConfig(JsonObject backupConfig) {
        this.enabled = backupConfig.get("enabled").getAsBoolean();
        this.maxBackups = backupConfig.get("max_backups").getAsInt();
        this.minBackupIntervalHours = backupConfig.get("min_backup_interval_hours").getAsInt();
        this.backupOnVersionChange = backupConfig.get("force_backup_on_version_change").getAsBoolean();
    }

    public void checkAndUpdateConfigs() {
        String currentVersion = UpdateHelper.getCurrentVersion();
        String configVersion = ConfigReader.getModVersion();

        // Always check for migration, even if versions match
        String highestVersion = findHighestExistingVersion(Arrays.asList(ConfigGenerator.VERSIONS));
        if (highestVersion != null && !highestVersion.equals(currentVersion)) {
            try {
                System.out.println("[MIGRATION] Migrating from " + highestVersion + " to " + currentVersion);
                UpdateHelper.migrateThroughVersions(highestVersion);
            } catch (Exception e) {
                System.err.println("[MIGRATION] Failed: " + e.getMessage());
            }
        }

        // Rest of your backup logic...
        if (shouldCreateBackup(currentVersion, configVersion)) {
            try {
                createBackup();
            } catch (IOException e) {
                System.err.println("[BACKUP] Failed: " + e.getMessage());
            }
        }

        ConfigReader.loadConfig();
    }

    public boolean shouldCreateBackup(String currentVersion, String configVersion) {
        System.out.println("[DEBUG] Checking backup conditions:");
        System.out.println("- Enabled: " + enabled);
        System.out.println("- Current version: " + currentVersion);
        System.out.println("- Config version: " + configVersion);

        if (!enabled) {
            System.out.println("[DEBUG] Backups disabled in config");
            return false;
        }

        if (backupOnVersionChange && !currentVersion.equals(configVersion)) {
            System.out.println("[DEBUG] Version change detected, backup required");
            return true;
        }

        try {
            Path lastBackup = backupRoot.toPath().resolve(".lastbackup");
            if (!Files.exists(lastBackup)) {
                System.out.println("[DEBUG] No previous backup found");
                return true;
            }

            long lastBackupTime = Long.parseLong(Files.readString(lastBackup));
            long hoursSinceLast = (System.currentTimeMillis() - lastBackupTime) / 3_600_000;
            System.out.println("[DEBUG] Hours since last backup: " + hoursSinceLast);

            return hoursSinceLast >= minBackupIntervalHours;
        } catch (Exception e) {
            System.out.println("[DEBUG] Backup check error: " + e.getMessage());
            return true;
        }
    }

    public void createBackup() throws IOException {
        // Ensure backup directory exists
        if (!backupRoot.exists()) {
            System.out.println("[DEBUG] Creating backup directory: " + backupRoot.getAbsolutePath());
            boolean dirCreated = backupRoot.mkdirs();
            if (!dirCreated) {
                throw new IOException("Failed to create backup directory");
            }
            setRestrictedPermissions(backupRoot);
        }

        // Debug output
        System.out.println("[DEBUG] Scanning files in: " + configRoot.getAbsolutePath());
        File[] files = configRoot.listFiles();
        if (files != null) {
            System.out.println("[DEBUG] Found " + files.length + " files to potentially backup");
            for (File f : files) {
                System.out.println("- " + f.getName() + (f.isDirectory() ? " (dir)" : " (file)"));
            }
        }

        // Proceed with backup creation...
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File backupFile = new File(backupRoot, backupPrefix + timestamp + backupExt);
        System.out.println("[DEBUG] Creating backup file: " + backupFile.getAbsolutePath());

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(backupFile))) {
            addToZip(configRoot, zos, "");
            setRestrictedPermissions(backupFile);
            updateLastBackupTimestamp();
            System.out.println("[DEBUG] Backup completed successfully");
        }
    }

    private void cleanOldBackups() throws IOException {
        File[] backups = backupRoot.listFiles((dir, name) ->
                name.startsWith(backupPrefix) && name.endsWith(backupExt));

        if (backups != null && backups.length >= maxBackups) {
            Arrays.sort(backups, Comparator.comparingLong(File::lastModified));
            for (int i = 0; i <= backups.length - maxBackups; i++) {
                Files.delete(backups[i].toPath());
            }
        }
    }

    private void addToZip(File file, ZipOutputStream zos, String basePath) throws IOException {
        // Skip ONLY the backup folder itself
        if (file.equals(backupRoot)) {
            System.out.println("[DEBUG] Skipping backup folder");
            return;
        }

        // Special handling for version folders
        if (file.isDirectory() && file.getName().matches(".*\\d+\\.\\d+\\.\\d+.*")) {
            // Only process the CURRENT version folder
            if (file.getName().equals(UpdateHelper.getCurrentVersion())) {
                System.out.println("[DEBUG] Processing current version folder: " + file.getName());
                File[] children = file.listFiles();
                if (children != null) {
                    for (File child : children) {
                        addToZip(child, zos, basePath + file.getName() + "/");
                    }
                }
            }
            return;
        }

        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    addToZip(child, zos, basePath + file.getName() + "/");
                }
            }
        } else {
            System.out.println("[DEBUG] Adding to ZIP: " + basePath + file.getName());
            ZipEntry entry = new ZipEntry(basePath + file.getName());
            zos.putNextEntry(entry);
            Files.copy(file.toPath(), zos);
            zos.closeEntry();
        }
    }

    private void setRestrictedPermissions(File file) throws IOException {
        System.out.println("[DEBUG] Setting permissions for: " + file.getAbsolutePath());
        if (!System.getProperty("os.name").toLowerCase().contains("win")) {
            Set<PosixFilePermission> perms = new HashSet<>();
            perms.add(PosixFilePermission.OWNER_READ);
            perms.add(PosixFilePermission.OWNER_WRITE);
            try {
                Files.setPosixFilePermissions(file.toPath(), perms);
                System.out.println("[DEBUG] POSIX permissions set successfully");
            } catch (UnsupportedOperationException e) {
                System.out.println("[DEBUG] POSIX permissions not supported");
            }
        } else {
            Files.setAttribute(file.toPath(), "dos:hidden", true);
            System.out.println("[DEBUG] Windows hidden attribute set");
        }
    }

    private void updateLastBackupTimestamp() throws IOException {
        Files.writeString(
                backupRoot.toPath().resolve(".lastbackup"),
                String.valueOf(System.currentTimeMillis())
        );
    }

    // Getters for configuration
    public boolean isEnabled() { return enabled; }
    public int getMaxBackups() { return maxBackups; }
    public int getMinBackupIntervalHours() { return minBackupIntervalHours; }
    public boolean isBackupOnVersionChange() { return backupOnVersionChange; }

    public static int testBackup(ServerCommandSource source) {
        try {
            ConfigBackupManager.getInstance().createBackup();
            source.sendFeedback((Supplier<Text>) Text.literal("Manual backup created successfully"), false);
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("Backup failed: " + e.getMessage()));
            e.printStackTrace();
            return 0;
        }
    }
}