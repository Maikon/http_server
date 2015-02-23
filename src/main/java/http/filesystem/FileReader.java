package http.filesystem;

import http.Request;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileReader {
  private final File directory;

  public FileReader(File directory) {
    this.directory = directory;
  }

  public File[] getDirectoryFiles() {
    return directory.listFiles();
  }

  public File findFile(Request request) {
    File file = fileThatMatchesURI(request);
    if (file == null) {
      file = new File(directory.getAbsolutePath() + request.getUri());
    }
    return file;
  }

  public String getFileContents(Request request) {
    Path path = findFile(request).toPath();
    return getFileContents(path);
  }

  public String getFileContents(File file) {
    Path path = file.toPath();
    return getFileContents(path);
  }

  public boolean fileExists(Request request) {
    return !(fileThatMatchesURI(request) == null);
  }

  private String getFileContents(Path path) {
    String body = "";
    try {
      byte[] data = Files.readAllBytes(path);
      body = new String(data);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return body;
  }

  private File fileThatMatchesURI(Request request) {
    File file = null;
    for (File directoryFile : getDirectoryFiles()) {
      String uri = request.getUri().substring(1);
      if (directoryFile.getName().equals(uri)) {
        file = directoryFile;
      }
    }
    return file;
  }
}
