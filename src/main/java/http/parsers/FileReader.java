package http.parsers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReader {
  private File file;

  public FileReader(File file) {
    this.file = file;
  }

  public static boolean fileExists(File root, String file) {
    return new File(root, file).isFile();
  }

  public byte[] readFile() {
    int size = (int) file.length();
    Path path = Paths.get(file.getAbsolutePath());
    byte[] result = new byte[size];
    try {
      result = Files.readAllBytes(path);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }
}
