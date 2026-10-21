package services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import beans.UserBean;
import dao.TaskDAO;
import dao.UserDAO;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.junit.jupiter.api.Test;
import support.DataDirectoryFixture;
import util.Utilities;

class BackupServiceTest extends DataDirectoryFixture {

  @Test
  void backupCopiesTheDataFolderBesideIt() throws Exception {
    NewUserService.createUser(new UserBean("10000002", "welcome", "Arun Mehta", "10000001"));
    new TaskService().addTask("10000002", "Keep me", "notes", "1", "15/10/2026");

    Path destination = new BackupService().backup().toPath();
    assertFalse(destination.toString().startsWith("D:"));
    assertTrue(destination.getFileName().toString().startsWith("TasKing Backup "));
    assertEquals(
        "TasKing Backup " + new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).format(new Date()),
        destination.getFileName().toString());
    assertTrue(Files.exists(destination.resolve("User.json")));
    assertTrue(Files.exists(destination.resolve("10000002.json")));
    assertTrue(destination.startsWith(dataDir().getParent()));
    assertFalse(destination.startsWith(dataDir()));
  }

  @Test
  void flushClearsTasksAndKeepsUsersAndTheCounter() throws Exception {
    NewUserService.createUser(new UserBean("10000002", "welcome", "Arun Mehta", "10000001"));
    new TaskService().addTask("10000002", "Keep me", "notes", "1", "15/10/2026");
    int nextId = Utilities.peekNextTaskId();

    BackupService.flush();

    assertEquals(1, UserDAO.getAllUsers().size());
    assertTrue(TaskDAO.getAllTasks("10000002").isEmpty());
    assertTrue(TaskDAO.getAllTasks("10000001-pool").isEmpty());
    assertEquals(nextId, Utilities.peekNextTaskId());
    assertTrue(
        Files.readString(dataDir().resolve("tasknum.txt"))
            .startsWith(Integer.toString(nextId - 1)));
  }
}
