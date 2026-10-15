package dao;

import beans.ExportTaskBean;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import util.Utilities;

public final class ExportDAO {
  private ExportDAO() {}

  public static File export(ArrayList<ExportTaskBean> completedTasks) {
    SimpleDateFormat stamp = new SimpleDateFormat("dd-MM-yyyy_HHmm", Locale.ENGLISH);
    File file =
        new File(
            Utilities.getLocation() + "Completed Tasks List " + stamp.format(new Date()) + ".csv");
    try (PrintWriter writer = new PrintWriter(file, StandardCharsets.UTF_8)) {
      writer.println(ExportTaskBean.getHeaders());
      for (ExportTaskBean task : completedTasks) {
        writer.println(task.toString());
      }
    } catch (IOException e) {
      throw new IllegalStateException("Could not export " + file.getAbsolutePath(), e);
    }
    return file;
  }
}
