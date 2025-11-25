package edu.ccrm.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import edu.ccrm.config.AppConfig;

public class BackupService {
    private final Path root = AppConfig.getInstance().getDataDir();

    public Path backupDirectory(Path sourceDir) throws IOException {
        String stamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path target = root.resolve("backup_" + stamp);
        Files.createDirectories(target);
        try (Stream<Path> stream = Files.walk(sourceDir)) {
            stream.forEach(src -> {
                try {
                    Path rel = sourceDir.relativize(src);
                    Path dest = target.resolve(rel);
                    if (Files.isDirectory(src)) {
                        if (!Files.exists(dest)) Files.createDirectories(dest);
                    } else {
                        Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        return target;
    }

    public long computeSizeRecursively(Path dir) throws IOException {
        if (!Files.exists(dir)) return 0L;
        try (Stream<Path> s = Files.walk(dir)) {
            return s.filter(p -> Files.isRegularFile(p)).mapToLong(p -> {
                try { return Files.size(p); } catch (IOException e) { return 0L; }
            }).sum();
        }
    }
}
