package http.filesystem;

import http.Request;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
    String body = "";
    try {
      byte[] data = Files.readAllBytes(this.findFile(request).toPath());
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
