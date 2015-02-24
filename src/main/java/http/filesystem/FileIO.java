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

  public String getFileContents(Request request) {
    Optional<File> file = findFile(request);
    if (file.isPresent()) {
      return getFileContents(file.get());
    }
    return "";
  }

  public Optional<File> findFile(Request request) {
    return fileThatMatchesURI(request);
  }

  public String getFileContents(File file) {
    Path path = file.toPath();
    return getFileContents(path);
  }

  public boolean fileExists(Request request) {
    return fileThatMatchesURI(request).isPresent();
  }

  public boolean fileExists(String fileName) {
    return new File(directory.getAbsolutePath() + fileName).exists();
  }

  public File createFile(String uri) {
    File file = new File(directory.getAbsolutePath() + uri);
    try {
      file.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return file;
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
