package http.filesystem;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileSystem {
  public ArrayList<String> allFiles(File directory) {
    ArrayList<String> allFiles = new ArrayList<>();
    File[] files = directory.listFiles();
    for (File file : files) {
      Path path = Paths.get(file.getPath());
      allFiles.add(path.getFileName().toString());
    }
    return allFiles;
  }
}
