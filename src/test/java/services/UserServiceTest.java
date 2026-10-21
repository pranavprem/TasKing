package services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import beans.UserBean;
import dao.TaskDAO;
import dao.UserDAO;
import java.nio.file.Files;
import org.junit.jupiter.api.Test;
import support.DataDirectoryFixture;

class UserServiceTest extends DataDirectoryFixture {

  @Test
  void createUserWritesTheFilesTheBoardExpects() {
    assertTrue(NewUserService.createUser(person("10000002", "Ada Lovelace", "10000001")));

    assertEquals("Ada Lovelace", LoginService.getName("10000002"));
    assertTrue(Files.exists(dataDir().resolve("User.json")));
    assertTrue(Files.exists(dataDir().resolve("10000002.json")));
    assertTrue(Files.exists(dataDir().resolve("10000001-10000002.json")));
    assertTrue(Files.exists(dataDir().resolve("10000002-completed.json")));
    assertTrue(Files.exists(dataDir().resolve("10000001-pool.json")));
  }

  @Test
  void secondReportDoesNotWipeTheManagersPool() {
    NewUserService.createUser(person("10000002", "Ada Lovelace", "10000001"));
    new TaskService().addTask("10000001-pool", "Cover the desk", "Shared", "1", "15/10/2026");

    assertTrue(NewUserService.createUser(person("10000003", "Grace Hopper", "10000001")));
    assertEquals(1, TaskDAO.getAllTasks("10000001-pool").size());
    assertEquals("Cover the desk", TaskDAO.getAllTasks("10000001-pool").get(0).getName());
  }

  @Test
  void duplicateUserDoesNotWipeExistingTasks() {
    UserBean ada = person("10000002", "Ada Lovelace", "10000001");
    assertTrue(NewUserService.createUser(ada));
    assertNull(new TaskService().addTask("10000002", "Keep me", "notes", "1.5", "15/10/2026"));

    assertFalse(NewUserService.createUser(ada));
    assertEquals(1, TaskDAO.getAllTasks("10000002").size());
    assertEquals("Keep me", TaskDAO.getAllTasks("10000002").get(0).getName());
  }

  @Test
  void pathShapedIdsAreRejected() {
    assertFalse(NewUserService.createUser(person("../User", "Bad", "10000001")));
    assertFalse(NewUserService.createUser(person("10000002", "Ada", "bad/id")));
    assertTrue(UserDAO.getAllUsers().isEmpty());
  }

  @Test
  void loginChangePasswordAndReportingLine() {
    NewUserService.createUser(person("10000001", "Priya Shah", "10000000"));
    NewUserService.createUser(person("10000002", "Arun Mehta", "10000001"));

    LoginService login = new LoginService();
    assertTrue(login.authorize(new UserBean("10000002", "welcome")));
    assertFalse(login.authorize(new UserBean("10000002", "nope")));
    assertEquals("10000001", login.getManager(new UserBean("10000002", "welcome")));
    assertTrue(LoginService.isManager("10000001"));
    assertFalse(LoginService.isManager("10000002"));
    assertEquals(1, LoginService.getAssociates("10000001").size());
    assertEquals("Arun Mehta", LoginService.getAssociates("10000001").get(0).getName());

    login.changePassword("10000002", "new-secret");
    assertFalse(login.authorize(new UserBean("10000002", "welcome")));
    assertTrue(login.authorize(new UserBean("10000002", "new-secret")));
    assertFalse(new UserBean("10000002", "new-secret").toString().contains("new-secret"));
  }

  private static UserBean person(String id, String name, String manager) {
    return new UserBean(id, "welcome", name, manager);
  }
}
