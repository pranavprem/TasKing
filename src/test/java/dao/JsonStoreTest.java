package dao;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import support.DataDirectoryFixture;

class JsonStoreTest extends DataDirectoryFixture {

  @Test
  void missingFilesAreEmptyLists() {
    assertTrue(UserDAO.getAllUsers().isEmpty());
    assertTrue(TaskDAO.getAllTasks("10000001").isEmpty());
    assertTrue(TaskDAO.getAllCompletedTasks("10000001").isEmpty());
  }

  @Test
  void corruptUserFileFailsInsteadOfLookingEmpty() throws Exception {
    Files.writeString(dataDir().resolve("User.json"), "{not json");
    assertThrows(IllegalStateException.class, UserDAO::getAllUsers);
  }

  @Test
  void corruptTaskFileFailsInsteadOfLookingEmpty() throws Exception {
    Path file = dataDir().resolve("10000001.json");
    Files.writeString(file, "[");
    assertThrows(IllegalStateException.class, () -> TaskDAO.getAllTasks("10000001"));
  }
}
