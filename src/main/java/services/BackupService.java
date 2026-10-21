package services;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.stream.Stream;
import util.Utilities;

public class BackupService {

  /**
   * Copies the data folder to a dated directory beside it (or under {@code TASKING_BACKUP_DIR} /
   * {@code tasking.backupDir} when that is set). The original build always wrote to {@code D:\},
   * which only worked on one Windows machine.
   */
  public File backup() throws IOException {
    Path source = new File(Utilities.getLocation()).toPath().toAbsolutePath().normalize();
    String stamp = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).format(new Date());
    Path destination =
        new File(Utilities.backupRoot(), "TasKing Backup " + stamp)
            .toPath()
            .toAbsolutePath()
            .normalize();
    if (destination.startsWith(source)) {
      throw new IOException("Refusing to back up into the data directory: " + destination);
    }
    Files.createDirectories(destination);
    try (Stream<Path> walk = Files.walk(source)) {
      for (Path path : walk.toList()) {
        Path target = destination.resolve(source.relativize(path));
        if (Files.isDirectory(path)) {
          Files.createDirectories(target);
        } else {
          if (target.getParent() != null) {
            Files.createDirectories(target.getParent());
          }
          Files.copy(path, target, StandardCopyOption.REPLACE_EXISTING);
        }
      }
    }
    return destination.toFile();
  }

  /** Resets every task file in the data folder to an empty list. Users and the id counter stay. */
  public static void flush() throws IOException {
    File source = new File(Utilities.getLocation());
    String[] paths = source.list();
    if (paths == null) {
      return;
    }
    for (String path : paths) {
      if (path.contains("User") || path.contains("location") || path.contains("tasknum")) {
        continue;
      }
      File file = new File(source, path);
      if (!file.isFile()) {
        continue;
      }
      Files.writeString(file.toPath(), "[]" + System.lineSeparator(), StandardCharsets.UTF_8);
    }
  }
}
