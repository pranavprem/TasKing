package dao;

import beans.NewTaskBean;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.util.ArrayList;
import util.Utilities;

public final class TaskDAO {
  private TaskDAO() {}

  public static void putAllTasks(ArrayList<NewTaskBean> tasks, String user) {
    JsonFiles.write(taskFile(user), tasks);
  }

  public static void complete(NewTaskBean task, String user) {
    ArrayList<NewTaskBean> tasks = getAllCompletedTasks(user);
    tasks.add(task);
    JsonFiles.write(completedFile(user), tasks);
  }

  public static ArrayList<NewTaskBean> getAllTasks(String user) {
    return JsonFiles.readList(taskFile(user), new TypeReference<ArrayList<NewTaskBean>>() {});
  }

  public static ArrayList<NewTaskBean> getAllCompletedTasks(String id) {
    return JsonFiles.readList(completedFile(id), new TypeReference<ArrayList<NewTaskBean>>() {});
  }

  private static File taskFile(String user) {
    return new File(Utilities.getLocation() + user + ".json");
  }

  private static File completedFile(String user) {
    return new File(Utilities.getLocation() + user + "-completed.json");
  }
}
