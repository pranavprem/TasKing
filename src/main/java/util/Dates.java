package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** Parses and formats the day-month-year dates stored on tasks. */
public final class Dates {
  private Dates() {}

  public static Date parse(String text) throws ParseException {
    SimpleDateFormat format = formatter();
    format.setLenient(false);
    return format.parse(text);
  }

  public static String format(Date date) {
    return formatter().format(date);
  }

  private static SimpleDateFormat formatter() {
    return new SimpleDateFormat(Constants.dateFormat);
  }
}
