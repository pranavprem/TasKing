package dao;

import beans.UserBean;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.util.ArrayList;
import util.Utilities;

public final class UserDAO {
  private UserDAO() {}

  public static void putAllUsers(ArrayList<UserBean> users) {
    JsonFiles.write(userFile(), users);
  }

  public static ArrayList<UserBean> getAllUsers() {
    return JsonFiles.readList(userFile(), new TypeReference<ArrayList<UserBean>>() {});
  }

  private static File userFile() {
    return new File(Utilities.getLocation() + "User.json");
  }
}
