package services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import beans.NewTaskBean;
import beans.UserBean;
import dao.TaskDAO;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import support.DataDirectoryFixture;
import util.Constants;
import util.Dates;

class TaskFlowTest extends DataDirectoryFixture {

  @Test
  void addEditDeleteAndCompleteRoundTrip() throws Exception {
    NewUserService.createUser(new UserBean("10000002", "welcome", "Arun Mehta", "10000001"));
    TaskService tasks = new TaskService();
    assertNull(tasks.addTask("10000002", "Close the books", "Quarter close", "1.5", "15/10/2026"));
    assertNull(tasks.addTask("10000002", "Reconcile vendors", "File", "2", "16/10/2026"));

    List<NewTaskBean> open = TaskDAO.getAllTasks("10000002");
    assertEquals(2, open.size());
    assertEquals(1, open.get(0).getID());
    assertEquals(2, open.get(1).getID());
    assertEquals("15/10/2026", Dates.format(open.get(0).getEstimatedDate()));
    assertEquals(1.5f, open.get(0).getEstimatedETC());

    tasks.edit("1", "10000002", "Updated notes");
    assertEquals("Updated notes", TaskDAO.getAllTasks("10000002").get(0).getDescription());

    tasks.deleteTask("2", "10000002");
    tasks.deleteTask("1", "10000002");
    assertTrue(TaskDAO.getAllTasks("10000002").isEmpty());
  }

  @Test
  void invalidDraftsDoNotRewriteTheFile() throws Exception {
    TaskService tasks = new TaskService();
    assertNotNull(tasks.addTask("10000002", "   ", "x", "1", "15/10/2026"));
    assertFalse(Files.exists(dataDir().resolve("10000002.json")));

    assertNull(tasks.addTask("10000002", "Real", "x", "1", "15/10/2026"));
    String before = Files.readString(dataDir().resolve("10000002.json"));
    assertNotNull(tasks.addTask("10000002", "Broken", "x", "hours", "15/10/2026"));
    assertNotNull(tasks.addTask("10000002", "Broken", "x", "1", "32/13/2026"));
    assertEquals(before, Files.readString(dataDir().resolve("10000002.json")));
    assertEquals(1, TaskDAO.getAllTasks("10000002").size());
  }

  @Test
  void completingAManagerTaskLandsOnTheReportee() {
    NewUserService.createUser(new UserBean("10000001", "welcome", "Priya Shah", "10000000"));
    NewUserService.createUser(new UserBean("10000002", "welcome", "Arun Mehta", "10000001"));
    TaskService tasks = new TaskService();
    tasks.addTask("10000001-10000002", "Review the pack", "From Priya", "2", "15/10/2026");
    String id = TaskDAO.getAllTasks("10000001-10000002").get(0).getID().toString();

    tasks.completeTask(id, "10000001-10000002", 1.25f);

    assertTrue(TaskDAO.getAllTasks("10000001-10000002").isEmpty());
    List<NewTaskBean> completed = TaskDAO.getAllCompletedTasks("10000002");
    assertEquals(1, completed.size());
    assertEquals(1.25f, completed.get(0).getActualETC());
    assertEquals(Dates.format(new Date()), Dates.format(completed.get(0).getCompletedDate()));
    assertEquals(1, TaskService.getCompletedToday("10000002").size());
  }

  @Test
  void oldCompletionsAreNotTodaysWorkAndEightHoursTurnsGreen() {
    NewUserService.createUser(new UserBean("10000002", "welcome", "Arun Mehta", "10000001"));
    NewTaskBean old = new NewTaskBean();
    old.setID(99);
    old.setName("Ancient");
    old.setDescription("done long ago");
    old.setEstimatedETC(8f);
    old.setActualETC(8f);
    old.setAssignDate(new Date(0));
    old.setEstimatedDate(new Date(0));
    old.setCompletedDate(new Date(0));
    TaskDAO.complete(old, "10000002");
    assertTrue(TaskService.getCompletedToday("10000002").isEmpty());

    TaskService tasks = new TaskService();
    assertFalse(TaskService.isEight("10000002"));
    tasks.addTask("10000002", "Long day", "own work", "5", "15/10/2026");
    tasks.addTask("10000001-10000002", "Assigned", "from manager", "3", "15/10/2026");
    assertTrue(TaskService.isEight("10000002"));
  }

  @Test
  void pullAndReleaseMovePoolTasks() {
    NewUserService.createUser(new UserBean("10000002", "welcome", "Arun Mehta", "10000001"));
    TaskService tasks = new TaskService();
    assertNull(tasks.addTask("10000001-pool", "Cover the desk", "Shared", "1", "15/10/2026"));
    String id = TaskDAO.getAllTasks("10000001-pool").get(0).getID().toString();

    tasks.pull(id, "10000002", "10000001");
    assertTrue(TaskDAO.getAllTasks("10000001-pool").isEmpty());
    NewTaskBean pulled = TaskDAO.getAllTasks("10000001-10000002").get(0);
    assertTrue(pulled.getName().endsWith(Constants.POOL_MARKER));

    tasks.release(id, "10000002", "10000001");
    assertTrue(TaskDAO.getAllTasks("10000001-10000002").isEmpty());
    assertEquals("Cover the desk", TaskDAO.getAllTasks("10000001-pool").get(0).getName());

    tasks.deletePool(id, "10000001");
    assertTrue(TaskDAO.getAllTasks("10000001-pool").isEmpty());
  }

  @Test
  void exportWritesCsvAndSurvivesAReportingCycle() throws Exception {
    UserBean priya = new UserBean("10000001", "welcome", "Priya, Shah", "10000002");
    UserBean arun = new UserBean("10000002", "welcome", "Arun", "10000001");
    ArrayList<UserBean> users = new ArrayList<>();
    users.add(priya);
    users.add(arun);
    dao.UserDAO.putAllUsers(users);

    TaskService tasks = new TaskService();
    tasks.addTask("10000002", "Close, books", "line\nbreak", "2", "15/10/2026");
    String arunTask = TaskDAO.getAllTasks("10000002").get(0).getID().toString();
    tasks.completeTask(arunTask, "10000002", 1f);
    tasks.addTask("10000001", "Sign off", "manager", "1", "15/10/2026");
    String priyaTask = TaskDAO.getAllTasks("10000001").get(0).getID().toString();
    tasks.completeTask(priyaTask, "10000001", 1f);

    var csv = new LoginService().generate(priya);
    String text = Files.readString(csv.toPath());
    assertTrue(text.startsWith(beans.ExportTaskBean.getHeaders()));
    assertTrue(text.contains("Priya. Shah"));
    assertTrue(text.contains("Close. books"));
    assertTrue(text.contains("linebreak"));
    assertTrue(text.contains("1.0"));
  }
}
