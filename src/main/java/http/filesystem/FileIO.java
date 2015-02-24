package http.filesystem;

import http.Request;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

public class FileIO {
  private final File directory;

  public FileIO(File directory) {
    this.directory = directory;
  }

  public File[] getDirectoryFiles() {
    return directory.listFiles();
  }

  public File findFile(Request request) {
    Optional<File> reqFile = fileThatMatchesURI(request);
    if (reqFile.isPresent()) {
      return reqFile.get();
    } else {
      return new File(directory.getAbsolutePath() + request.getUri());
    }
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
    return fileThatMatchesURI(request).isPresent();
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

  private Optional<File> fileThatMatchesURI(Request request) {
    String uri = request.getUri().substring(1);
    return Stream.of(getDirectoryFiles()).filter(f -> f.getName().equals(uri))
                                         .findFirst();
  }
}
