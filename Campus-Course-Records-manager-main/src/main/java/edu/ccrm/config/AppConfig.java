package edu.ccrm.config;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class AppConfig {
    private static final AppConfig INSTANCE = new AppConfig();
    private final Path dataDir;

    private AppConfig() {
        // default data dir inside user's current directory
        this.dataDir = Paths.get(System.getProperty("user.dir")).resolve("ccrm_data");
        try {
            if (!Files.exists(dataDir)) Files.createDirectories(dataDir);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create data dir: " + dataDir, e);
        }
    }

    public static AppConfig getInstance() { return INSTANCE; }

    public Path getDataDir() { return dataDir; }
}
