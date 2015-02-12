package http.filesystem;

import java.io.File;
import java.nio.file.FileAlreadyExistsException;
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

  public void createFile(File root, String file) throws FileAlreadyExistsException {
    ArrayList<String> directory = allFiles(root);
    if (directory.contains(file)) {
      throw new FileAlreadyExistsException(file);
    }
    directory.add(file);
  }

  public void deleteFile(File root, String file) {
    allFiles(root).remove(file);
  }
}
