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
    assertThat(fileIO.findFile(request), is(file));
  }

  @Test
  public void retrievesContentsOfFile() throws IOException {
    File file = directory.newFile("file");
    FileWriter writer = new FileWriter(file);
    writer.write("Body");
    writer.close();
    Request request = Request.withMethod("GET").addURI("/file").build();
    assertThat(fileIO.getFileContents(request), is("Body"));
  }

  @Test
  public void returnsContentOfAGivenFile() throws IOException {
    File file = directory.newFile("file");
    FileWriter writer = new FileWriter(file);
    writer.write("Body");
    writer.close();
    assertThat(fileIO.getFileContents(file), is("Body"));
  }

  @Test
  public void checksIfAFileExists() throws IOException {
    directory.newFile("file");
    Request requestNoFile = Request.withMethod("GET").addURI("/some-file").build();
    Request requestWithFile = Request.withMethod("GET").addURI("/file").build();
    assertThat(fileIO.fileExists(requestNoFile), is(false));
    assertThat(fileIO.fileExists(requestWithFile), is(true));
  }

  @Test
  public void canCreateAFile() {
    fileIO.createFile("new-file");
    assertThat(fileIO.fileExists("new-file"), is(true));
  }
}
