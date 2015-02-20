package http.responders;

import http.Request;
import http.TestHelper;
import http.filesystem.FileReader;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static http.responders.StatusCodes.OK;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FileContentsResponderTest extends TestHelper {
  private ServerResponse response;


  @Before
  public void setUp() throws IOException {
    File file = directory.newFile("file");
    FileWriter writer = new FileWriter(file);
    writer.write("file contents");
    writer.close();
    FileReader reader = new FileReader(directory.getRoot());
    response = new FileContentsResponder(reader).response(Request.withMethod("GET").addURI("/file").build());
  }

  @Test
  public void respondsWithSuccess() {
    assertThat(response.getStatus(), is(OK));
  }

  @Test
  public void respondsWithHTML() {
    assertThat(response.getHeader("Content-Type"), is("text/html"));
  }

  @Test
  public void respondsWithTheContentsOfTheFile() {
    assertThat(response.getBody(), containsString("file contents"));
  }
}
