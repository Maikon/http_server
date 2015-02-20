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

public class FileReaderTest extends TestHelper {
  private FileReader reader;

  @Before
  public void setUp() {
    reader = new FileReader(directory.getRoot());
  }

  @Test
  public void returnsAllFilesFromDirectory() throws IOException {
    directory.newFile("file1");
    directory.newFile("file2");
    directory.newFile("file3");

    assertThat(reader.getDirectoryFiles().length, is(3));
  }

  @Test
  public void returnsAFileBasedOnRequest() throws IOException {
    File file = directory.newFile("file");
    Request request = Request.withMethod("GET").addURI("/file").build();
    assertThat(reader.findFile(request), is(file));
  }

  @Test
  public void retrievesContentsOfFile() throws IOException {
    File file = directory.newFile("file");
    FileWriter writer = new FileWriter(file);
    writer.write("Body");
    writer.close();
    Request request = Request.withMethod("GET").addURI("/file").build();
    assertThat(reader.getFileContents(request), is("Body"));
  }
}
