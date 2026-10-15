package dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/** Reads and writes the JSON files TasKing uses as its database. */
final class JsonFiles {
  private static final ObjectMapper MAPPER = new ObjectMapper();

  private JsonFiles() {}

  static <T> ArrayList<T> readList(File file, TypeReference<ArrayList<T>> type) {
    if (!file.exists()) {
      return new ArrayList<>();
    }
    try {
      ArrayList<T> value = MAPPER.readValue(file, type);
      return value == null ? new ArrayList<>() : value;
    } catch (IOException e) {
      throw new IllegalStateException("Could not read " + file.getAbsolutePath(), e);
    }
  }

  static void write(File file, Object value) {
    File parent = file.getParentFile();
    if (parent != null && !parent.isDirectory() && !parent.mkdirs()) {
      throw new IllegalStateException("Could not create " + parent.getAbsolutePath());
    }
    try {
      MAPPER.writeValue(file, value);
    } catch (IOException e) {
      throw new IllegalStateException("Could not write " + file.getAbsolutePath(), e);
    }
  }
}
