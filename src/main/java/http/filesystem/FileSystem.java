package http.filesystem;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileSystem {

  private ArrayList<String> allFiles = new ArrayList<>();

  public ArrayList<String> allFiles(File directory) {
    File[] files = directory.listFiles();
    for (File file : files) {
      Path path = Paths.get(file.getPath());
      allFiles.add(path.getFileName().toString());
    }
    return allFiles;
  }

  public void createFile(File root, String file) {
    allFiles(root).add(file);
  }

  public void deleteFile(File root, String file) {
    allFiles(root).remove(file);
  }
}
