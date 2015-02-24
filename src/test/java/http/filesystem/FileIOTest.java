package http.filesystem;

import http.Request;
import http.TestHelper;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FileIOTest extends TestHelper {
  private FileIO fileIO;

  @Before
  public void setUp() {
    fileIO = new FileIO(directory.getRoot());
  }

  @Test
  public void returnsAllFilesFromDirectory() throws IOException {
    directory.newFile("file1");
    directory.newFile("file2");
    directory.newFile("file3");

    assertThat(fileIO.getDirectoryFiles().length, is(3));
  }

  @Test
  public void returnsAFileBasedOnRequest() throws IOException {
    File file = directory.newFile("file");
    Request request = Request.withMethod("GET").addURI("/file").build();
    assertThat(fileIO.findFile(request).get(), is(file));
  }

  @Test
  public void retrievesContentsOfFile() throws IOException {
    File file = directory.newFile("file");
    writeToFile(file, "Body");
    Request request = Request.withMethod("GET").addURI("/file").build();
    assertThat(fileIO.getFileContents(request), is("Body"));
  }

  @Test
  public void returnsContentOfAGivenFile() throws IOException {
    File file = directory.newFile("file");
    writeToFile(file, "Body");
    assertThat(fileIO.getFileContents(file), is("Body"));
  }

  @Test
  public void returnsTrueIfFileExists() throws IOException {
    directory.newFile("file");
    Request requestWithFile = Request.withMethod("GET").addURI("/file").build();
    assertThat(fileIO.fileExists(requestWithFile), is(true));
  }

  @Test
  public void returnsFalseIfFileDoesNotExists() throws IOException {
    directory.newFile("file");
    Request requestNoFile = Request.withMethod("GET").addURI("/some-file").build();
    assertThat(fileIO.fileExists(requestNoFile), is(false));
  }

  @Test
  public void canCreateAFile() {
    fileIO.createFile("new-file");
    assertThat(fileIO.fileExists("new-file"), is(true));
  }

  @Test
  public void writesToAFile() {
    File file = fileIO.createFile("file");
    fileIO.writeToFile(file, "some content");
    assertThat(fileIO.getFileContents(file), is("some content"));
  }

  private void writeToFile(File file, String content) throws IOException {
    FileWriter writer = new FileWriter(file);
    writer.write(content);
    writer.close();
  }
}
