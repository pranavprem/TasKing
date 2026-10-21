package services;

import beans.ExportTaskBean;
import beans.NewTaskBean;
import beans.UserBean;
import dao.ExportDAO;
import dao.TaskDAO;
import dao.UserDAO;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class LoginService {
  private ArrayList<UserBean> users;

  public static String getName(String id) {
    for (UserBean user : UserDAO.getAllUsers()) {
      if (user.getId() != null && user.getId().equals(id)) {
        return user.getName() == null ? "" : user.getName();
      }
    }
    return "";
  }

  public boolean authorize(UserBean loginUser) {
    users = UserDAO.getAllUsers();
    for (UserBean user : users) {
      if (Objects.equals(user.getId(), loginUser.getId())
          && Objects.equals(user.getPassword(), loginUser.getPassword())) {
        return true;
      }
    }
    return false;
  }

  public static boolean isManager(String loginUser) {
    for (UserBean user : UserDAO.getAllUsers()) {
      if (Objects.equals(user.getManager(), loginUser)) {
        return true;
      }
    }
    return false;
  }

  public String getManager(UserBean loginUser) {
    users = UserDAO.getAllUsers();
    for (UserBean user : users) {
      if (Objects.equals(user.getId(), loginUser.getId())) {
        return user.getManager();
      }
    }
    return null;
  }

  public static ArrayList<UserBean> getAssociates(String currentUser) {
    ArrayList<UserBean> associates = new ArrayList<>();
    for (UserBean user : UserDAO.getAllUsers()) {
      if (Objects.equals(user.getManager(), currentUser)) {
        associates.add(user);
      }
    }
    return associates;
  }

  public ArrayList<ExportTaskBean> getList(UserBean manager) {
    return getList(manager, new HashSet<String>());
  }

  private ArrayList<ExportTaskBean> getList(UserBean manager, Set<String> visited) {
    ArrayList<ExportTaskBean> exportList = new ArrayList<>();
    if (manager.getId() == null || !visited.add(manager.getId())) {
      return exportList;
    }
    for (UserBean user : users) {
      if (Objects.equals(user.getManager(), manager.getId())
          && !Objects.equals(user.getId(), manager.getId())) {
        for (NewTaskBean task : TaskDAO.getAllCompletedTasks(user.getId())) {
          exportList.add(new ExportTaskBean(task, user.getId()));
        }
        if (isManager(user.getId())) {
          exportList.addAll(getList(user, visited));
        }
      }
    }
    return exportList;
  }

  public File generate(UserBean manager) {
    users = UserDAO.getAllUsers();
    ArrayList<ExportTaskBean> exportList = getList(manager);
    for (NewTaskBean task : TaskDAO.getAllCompletedTasks(manager.getId())) {
      exportList.add(new ExportTaskBean(task, manager.getId()));
    }
    return ExportDAO.export(exportList);
  }

  public void changePassword(String id, String password) {
    ArrayList<UserBean> everyone = UserDAO.getAllUsers();
    for (UserBean user : everyone) {
      if (Objects.equals(user.getId(), id)) {
        user.setPassword(password);
      }
    }
    UserDAO.putAllUsers(everyone);
  }
}
