package services;

import beans.NewTaskAddBean;
import beans.NewTaskBean;
import beans.NewTaskTableBean;
import beans.UserBean;
import dao.TaskDAO;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import util.Constants;
import util.Dates;

public class TaskService {

  public String addTask(NewTaskAddBean draft, String user) {
    return addTask(
        user,
        draft.getName().getText(),
        draft.getDescription().getText(),
        draft.getEstimatedETC().getText(),
        draft.getEstimatedDate().getText());
  }

  public String addTask(
      String user, String name, String description, String estimatedHours, String estimatedDate) {
    if (name == null || name.trim().isEmpty()) {
      return "Enter a task name.";
    }
    ArrayList<NewTaskBean> tasks = TaskDAO.getAllTasks(user);
    try {
      tasks.add(new NewTaskBean(name, description, estimatedHours, estimatedDate));
    } catch (ParseException | NumberFormatException ex) {
      return "Use a number of hours and a date in dd/MM/yyyy.";
    }
    TaskDAO.putAllTasks(tasks, user);
    return null;
  }

  public String addPool(String user, NewTaskAddBean draft) {
    return addTask(
        user + "-pool",
        draft.getName().getText(),
        draft.getDescription().getText(),
        draft.getEstimatedETC().getText(),
        draft.getEstimatedDate().getText());
  }

  /** True when today's open estimates and completed actuals add up to at least eight hours. */
  public static boolean isEight(String user) {
    float hours = 0f;
    for (NewTaskBean task : TaskDAO.getAllTasks(user)) {
      hours += hoursOf(task.getEstimatedETC());
    }
    UserBean loginUser = new UserBean();
    loginUser.setId(user);
    String manager = new LoginService().getManager(loginUser);
    if (manager != null) {
      for (NewTaskBean task : TaskDAO.getAllTasks(manager + "-" + user)) {
        hours += hoursOf(task.getEstimatedETC());
      }
    }
    String today = Dates.format(new Date());
    for (NewTaskBean task : TaskDAO.getAllCompletedTasks(user)) {
      if (task.getEstimatedDate() != null && today.equals(Dates.format(task.getEstimatedDate()))) {
        hours += hoursOf(task.getActualETC());
      }
    }
    return hours >= 8f;
  }

  public ArrayList<NewTaskTableBean> getTask(String user) {
    ArrayList<NewTaskTableBean> taskTable = new ArrayList<>();
    for (NewTaskBean task : TaskDAO.getAllTasks(user)) {
      taskTable.add(new NewTaskTableBean(task));
    }
    return taskTable;
  }

  public void deleteTask(String id, String user) {
    ArrayList<NewTaskBean> tasks = TaskDAO.getAllTasks(user);
    int taskId = Integer.parseInt(id);
    Iterator<NewTaskBean> iterator = tasks.iterator();
    while (iterator.hasNext()) {
      NewTaskBean task = iterator.next();
      if (task.getID() != null && task.getID() == taskId) {
        iterator.remove();
        break;
      }
    }
    TaskDAO.putAllTasks(tasks, user);
  }

  /** Moves a task onto the owner's completed file and removes it from the open list. */
  public void completeTask(String id, String storeKey, float actualEtc) {
    ArrayList<NewTaskBean> tasks = TaskDAO.getAllTasks(storeKey);
    int taskId = Integer.parseInt(id);
    Iterator<NewTaskBean> iterator = tasks.iterator();
    while (iterator.hasNext()) {
      NewTaskBean task = iterator.next();
      if (task.getID() != null && task.getID() == taskId) {
        task.setActualETC(actualEtc);
        task.setCompletedDate(new Date());
        TaskDAO.complete(task, ownerId(storeKey));
        iterator.remove();
        break;
      }
    }
    TaskDAO.putAllTasks(tasks, storeKey);
  }

  public ArrayList<NewTaskTableBean> getpool(String user) {
    ArrayList<NewTaskTableBean> taskTable = new ArrayList<>();
    for (NewTaskBean task : TaskDAO.getAllTasks(user + "-pool")) {
      taskTable.add(new NewTaskTableBean(task));
    }
    return taskTable;
  }

  public void deletePool(String id, String user) {
    deleteTask(id, user + "-pool");
  }

  public void pull(String id, String user, String manager) {
    ArrayList<NewTaskBean> poolTasks = TaskDAO.getAllTasks(manager + "-pool");
    ArrayList<NewTaskBean> managerTasks = TaskDAO.getAllTasks(manager + "-" + user);
    int taskId = Integer.parseInt(id);
    Iterator<NewTaskBean> iterator = poolTasks.iterator();
    while (iterator.hasNext()) {
      NewTaskBean task = iterator.next();
      if (task.getID() != null && task.getID() == taskId) {
        String name = task.getName() == null ? "" : task.getName();
        if (!name.contains(Constants.POOL_MARKER)) {
          task.setName(name + Constants.POOL_MARKER);
        }
        managerTasks.add(task);
        iterator.remove();
        break;
      }
    }
    TaskDAO.putAllTasks(managerTasks, manager + "-" + user);
    TaskDAO.putAllTasks(poolTasks, manager + "-pool");
  }

  public static ArrayList<NewTaskTableBean> getCompletedToday(String user) {
    String today = Dates.format(new Date());
    ArrayList<NewTaskTableBean> todays = new ArrayList<>();
    for (NewTaskBean task : TaskDAO.getAllCompletedTasks(user)) {
      if (task.getCompletedDate() != null && today.equals(Dates.format(task.getCompletedDate()))) {
        todays.add(new NewTaskTableBean(task));
      }
    }
    return todays;
  }

  /**
   * 2 when saved hours pass 20, 1 when they pass 10, otherwise 0. The badge images are optional.
   */
  public static Integer isProSaver(String user) {
    float hours = 0f;
    for (NewTaskBean task : TaskDAO.getAllCompletedTasks(user)) {
      hours += hoursOf(task.getEstimatedETC()) - hoursOf(task.getActualETC());
    }
    if (hours > 20f) {
      return 2;
    }
    if (hours > 10f) {
      return 1;
    }
    return 0;
  }

  public void release(String id, String user, String manager) {
    ArrayList<NewTaskBean> poolTasks = TaskDAO.getAllTasks(manager + "-pool");
    ArrayList<NewTaskBean> managerTasks = TaskDAO.getAllTasks(manager + "-" + user);
    int taskId = Integer.parseInt(id);
    Iterator<NewTaskBean> iterator = managerTasks.iterator();
    while (iterator.hasNext()) {
      NewTaskBean task = iterator.next();
      if (task.getID() != null && task.getID() == taskId) {
        task.setName(stripPoolMarker(task.getName()));
        poolTasks.add(task);
        iterator.remove();
        break;
      }
    }
    TaskDAO.putAllTasks(managerTasks, manager + "-" + user);
    TaskDAO.putAllTasks(poolTasks, manager + "-pool");
  }

  public void edit(String id, String user, String description) {
    ArrayList<NewTaskBean> tasks = TaskDAO.getAllTasks(user);
    int taskId = Integer.parseInt(id);
    for (NewTaskBean task : tasks) {
      if (task.getID() != null && task.getID() == taskId) {
        task.setDescription(description);
      }
    }
    TaskDAO.putAllTasks(tasks, user);
  }

  static String ownerId(String storeKey) {
    int dash = storeKey.lastIndexOf('-');
    if (dash > 0 && dash < storeKey.length() - 1) {
      String tail = storeKey.substring(dash + 1);
      if (!"pool".equals(tail) && !"completed".equals(tail)) {
        return tail;
      }
    }
    return storeKey;
  }

  private static String stripPoolMarker(String name) {
    if (name == null) {
      return "";
    }
    if (name.endsWith(Constants.POOL_MARKER)) {
      return name.substring(0, name.length() - Constants.POOL_MARKER.length());
    }
    return name;
  }

  private static float hoursOf(Float hours) {
    return hours == null ? 0f : hours;
  }
}
