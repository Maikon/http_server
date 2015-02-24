package http.responders;

import http.Request;
import http.TestHelper;
import http.filesystem.FileIO;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static http.responders.StatusCodes.OK;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RootResponderTest extends TestHelper {
  private ServerResponse response;

  @Before
  public void setUp() throws IOException {
    directory.newFile("file1");
    FileIO fileIO = new FileIO(directory.getRoot());
    response = new RootResponder(fileIO).response(Request.withMethod("GET").addURI("/").build());
  }

  @Test
  public void rootResponseIsOK() {
    assertThat(response.getStatus(), is(OK));
  }

  @Test
  public void rootResponseIsHTML() {
    assertThat(response.getHeader("Content-Type"), is("text/html"));
  }

  @Test
  public void rootResponseContainsBody() {
    assertThat(response.getBody(), containsString("<a href='file1'>file1</a>"));
  }
}
