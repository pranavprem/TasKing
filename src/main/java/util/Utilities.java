package util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Scanner;

/**
 * Data-folder location and the shared task-number counter.
 *
 * <p>Location precedence is the {@code tasking.dataDir} system property, then the {@code
 * TASKING_DATA_DIR} environment variable, then {@code location.txt} in the working directory, then
 * a {@code data} folder next to the working directory.
 */
public final class Utilities {
  public static final String DATA_DIR_PROPERTY = "tasking.dataDir";
  public static final String BACKUP_DIR_PROPERTY = "tasking.backupDir";

  private Utilities() {}

  public static String getLocation() {
    String location = firstNonBlank(System.getProperty(DATA_DIR_PROPERTY));
    if (location == null) {
      location = firstNonBlank(System.getenv("TASKING_DATA_DIR"));
    }
    if (location == null) {
      location = readLocationFile();
    }
    if (location == null) {
      location = "data";
    }
    File dir = new File(location.trim());
    if (!dir.isDirectory() && !dir.mkdirs()) {
      throw new IllegalStateException("Could not create data directory " + dir.getAbsolutePath());
    }
    if (!dir.isDirectory()) {
      throw new IllegalStateException("Data location is not a directory: " + dir.getAbsolutePath());
    }
    String path = dir.getAbsolutePath();
    if (!path.endsWith(File.separator)) {
      path = path + File.separator;
    }
    return path;
  }

  /** Folder that dated backup directories are created in. */
  public static File backupRoot() {
    String override = firstNonBlank(System.getProperty(BACKUP_DIR_PROPERTY));
    if (override == null) {
      override = firstNonBlank(System.getenv("TASKING_BACKUP_DIR"));
    }
    if (override != null) {
      return new File(override.trim());
    }
    File parent = new File(getLocation()).getAbsoluteFile().getParentFile();
    return parent == null ? new File(".") : parent;
  }

  public static String cleanString(String stringToBeCleaned) {
    if (stringToBeCleaned == null) {
      return "";
    }
    return stringToBeCleaned.replace("\n", "").replace("\r", "").replace(Constants.comma, ".");
  }

  /** Next task number that would be assigned. Does not advance the counter. */
  public static int peekNextTaskId() {
    return readTaskId() + 1;
  }

  /** Advances the shared counter and returns the new task number. */
  public static int nextTaskId() {
    int id = readTaskId() + 1;
    writeTaskId(id);
    return id;
  }

  private static String readLocationFile() {
    File file = new File("location.txt");
    if (!file.isFile()) {
      return null;
    }
    try {
      String line = Files.readString(file.toPath(), StandardCharsets.UTF_8);
      // Keep the first line only, matching the original one-line location file.
      int newline = line.indexOf('\n');
      if (newline >= 0) {
        line = line.substring(0, newline);
      }
      line = line.replace("\r", "").trim();
      return line.isEmpty() ? null : line;
    } catch (IOException e) {
      throw new IllegalStateException("Could not read location.txt", e);
    }
  }

  private static File taskNumFile() {
    return new File(getLocation() + Constants.tasknum + Constants.dotTxt);
  }

  private static int readTaskId() {
    File file = taskNumFile();
    if (!file.isFile()) {
      return 0;
    }
    try (Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)) {
      if (scanner.hasNextInt()) {
        return scanner.nextInt();
      }
      return 0;
    } catch (IOException e) {
      throw new IllegalStateException("Could not read " + file.getAbsolutePath(), e);
    }
  }

  private static void writeTaskId(int id) {
    try {
      Files.writeString(taskNumFile().toPath(), Integer.toString(id), StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new IllegalStateException("Could not write the task number file", e);
    }
  }

  private static String firstNonBlank(String value) {
    if (value == null) {
      return null;
    }
    String trimmed = value.trim();
    return trimmed.isEmpty() ? null : trimmed;
  }
}
