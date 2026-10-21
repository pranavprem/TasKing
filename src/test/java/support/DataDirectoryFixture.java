package support;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import util.Utilities;

/** Points every test at its own data folder so JSON files never land in the repo. */
public abstract class DataDirectoryFixture {
  private Path root;

  @BeforeEach
  void createDataDirectory() throws IOException {
    root = Files.createTempDirectory("tasking-test");
    Path data = root.resolve("data");
    Files.createDirectories(data);
    System.setProperty(Utilities.DATA_DIR_PROPERTY, data.toString());
  }

  @AfterEach
  void deleteDataDirectory() throws IOException {
    System.clearProperty(Utilities.DATA_DIR_PROPERTY);
    System.clearProperty(Utilities.BACKUP_DIR_PROPERTY);
    if (root == null) {
      return;
    }
    try (Stream<Path> walk = Files.walk(root)) {
      walk.sorted(Comparator.reverseOrder())
          .forEach(
              path -> {
                try {
                  Files.deleteIfExists(path);
                } catch (IOException ignored) {
                  // Best-effort cleanup of a temp folder.
                }
              });
    }
  }

  protected Path dataDir() {
    return Path.of(Utilities.getLocation());
  }
}
