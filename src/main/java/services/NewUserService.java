package services;

import beans.NewTaskBean;
import beans.UserBean;
import dao.TaskDAO;
import dao.UserDAO;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public final class NewUserService {
  private NewUserService() {}

  /**
   * Adds a person and the empty task files the board expects.
   *
   * <p>The shared pool file is created for a manager the first time someone reports to them. A
   * later report does not overwrite that pool.
   *
   * @return false when the user was not created
   */
  public static boolean createUser(UserBean addUser) {
    if (!validId(addUser.getId()) || !validId(addUser.getManager())) {
      System.out.println("ID and manager ID are required and cannot contain a path.");
      return false;
    }
    if (addUser.getPassword() == null || addUser.getPassword().isEmpty()) {
      System.out.println("Password is required.");
      return false;
    }
    if (addUser.getName() == null || addUser.getName().isBlank()) {
      System.out.println("Name is required.");
      return false;
    }

    ArrayList<UserBean> users = UserDAO.getAllUsers();
    for (UserBean user : users) {
      if (addUser.getId().equals(user.getId())) {
        System.out.println("User already exists: " + addUser.getId());
        return false;
      }
    }

    boolean managerAlreadyHasReports = false;
    for (UserBean user : users) {
      if (Objects.equals(addUser.getManager(), user.getManager())) {
        managerAlreadyHasReports = true;
        break;
      }
    }

    users.add(addUser);
    UserDAO.putAllUsers(users);
    ArrayList<NewTaskBean> tasks = new ArrayList<>();
    TaskDAO.putAllTasks(tasks, addUser.getId());
    TaskDAO.putAllTasks(tasks, addUser.getManager() + "-" + addUser.getId());
    TaskDAO.putAllTasks(tasks, addUser.getId() + "-completed");
    if (!managerAlreadyHasReports) {
      TaskDAO.putAllTasks(tasks, addUser.getManager() + "-pool");
    }
    return true;
  }

  private static boolean validId(String id) {
    if (id == null || id.isBlank()) {
      return false;
    }
    if (id.contains("..")
        || id.indexOf(File.separatorChar) >= 0
        || id.contains("/")
        || id.contains("\\")) {
      return false;
    }
    for (int i = 0; i < id.length(); i++) {
      if (Character.isWhitespace(id.charAt(i))) {
        return false;
      }
    }
    return true;
  }
}
