package util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import support.DataDirectoryFixture;

class UtilitiesTest extends DataDirectoryFixture {

  @Test
  void cleanStringStripsNewlinesAndCommas() {
    assertEquals("a.bc", Utilities.cleanString("a,b\nc\r"));
    assertEquals("", Utilities.cleanString(null));
  }

  @Test
  void dataDirectoryPropertyCreatesATrailingSeparator() {
    String location = Utilities.getLocation();
    assertTrue(location.endsWith(java.io.File.separator));
    assertTrue(Files.isDirectory(Path.of(location)));
  }

  @Test
  @DisabledIfEnvironmentVariable(named = "TASKING_DATA_DIR", matches = ".+")
  void locationFileIsUsedWhenThePropertyIsUnset() throws IOException {
    System.clearProperty(Utilities.DATA_DIR_PROPERTY);
    Path configured = Files.createTempDirectory("tasking-location");
    Path locationFile = Path.of("location.txt");
    String previous = Files.exists(locationFile) ? Files.readString(locationFile) : null;
    try {
      Files.writeString(locationFile, configured.toString());
      String location = Utilities.getLocation();
      assertTrue(location.startsWith(configured.toAbsolutePath().toString()));
      assertTrue(location.endsWith(java.io.File.separator));
    } finally {
      if (previous == null) {
        Files.deleteIfExists(locationFile);
      } else {
        Files.writeString(locationFile, previous);
      }
      deleteTree(configured);
    }
  }

  @Test
  void taskIdsStartAtOneAndPeekDoesNotConsumeThem() {
    assertEquals(1, Utilities.peekNextTaskId());
    assertEquals(1, Utilities.peekNextTaskId());
    assertEquals(1, Utilities.nextTaskId());
    assertEquals(2, Utilities.nextTaskId());
    assertEquals(3, Utilities.peekNextTaskId());
  }

  @Test
  void datesRejectImpossibleCalendarDays() {
    assertThrows(ParseException.class, () -> Dates.parse("32/13/2026"));
    assertThrows(ParseException.class, () -> Dates.parse("not-a-date"));
  }

  private static void deleteTree(Path root) throws IOException {
    if (!Files.exists(root)) {
      return;
    }
    try (var walk = Files.walk(root)) {
      walk.sorted(java.util.Comparator.reverseOrder())
          .forEach(
              path -> {
                try {
                  Files.deleteIfExists(path);
                } catch (IOException ignored) {
                  // Best-effort cleanup.
                }
              });
    }
  }
}
