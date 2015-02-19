package http.filesystem;

import http.Request;

import java.io.File;

public class FileReader {
  private final File directory;

  public FileReader(File directory) {
    this.directory = directory;
  }

  public File findFile(Request request) {
    File f = fileThatMatchesURI(request);
    if (f == null) {
      f = new File(directory.getAbsolutePath() + request.getUri());
    }
    return f;
  }

  private File fileThatMatchesURI(Request request) {
    File file = null;
    for (File f : getDirectoryFiles()) {
      String uri = request.getUri().substring(1);
      if (f.getName().equals(uri)) {
        file = f;
      }
    }
    return file;
  }

  private File[] getDirectoryFiles() {
    return directory.listFiles();
  }
}
