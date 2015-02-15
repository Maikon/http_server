package http.filesystem;

import java.io.*;
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

  public List<String> allFiles() {
    File[] files = directory.listFiles();
    for (File file : files) {
      Path path = Paths.get(file.getPath());
      allFiles.add(path.getFileName().toString());
    }
    return allFiles;
  }

  public void createFile(String file) throws FileAlreadyExistsException {
    List<String> dir = allFiles();
    if (dir.contains(file)) {
      throw new FileAlreadyExistsException(file);
    }
    dir.add(file);
  }

  public void deleteFile(String file) {
    allFiles().remove(file);
  }

  public boolean fileExists(String file) {
    return allFiles().contains(file);
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

  public void writeTo(String file, String content) {
    Path path = Paths.get(directory + "/" + file);
    byte[] data = content.getBytes();
    try {
      OutputStream out = new FileOutputStream(path.toString());
      out.write(data, 0, data.length);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
