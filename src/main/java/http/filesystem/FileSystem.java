package http.filesystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileSystem {

  private File directory;
  private List<String> allFiles = new ArrayList<>();

  public FileSystem(File directory) {
    this.directory = directory;
  }

  public FileSystem() {
  }

  public List<String> allFiles(File directory) {
    File[] files = directory.listFiles();
    for (File file : files) {
      Path path = Paths.get(file.getPath());
      allFiles.add(path.getFileName().toString());
    }
    return allFiles;
  }

  public void createFile(File root, String file) throws FileAlreadyExistsException {
    List<String> directory = allFiles(root);
    if (directory.contains(file)) {
      throw new FileAlreadyExistsException(file);
    }
    directory.add(file);
  }

  public void deleteFile(File root, String file) {
    allFiles(root).remove(file);
  }

  public boolean fileExists(File root, String file) {
    return allFiles(root).contains(file);
  }

  public byte[] readFile(String file) {
    int size = file.length();
    Path path = Paths.get(directory + "/" + file);
    byte[] result = new byte[size];
    try {
      result = Files.readAllBytes(path);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  public int contentLength(String file) {
    return readFile(file).length;
  }
}
