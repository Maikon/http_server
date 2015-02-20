package http.responders;

import http.Request;
import http.filesystem.FileReader;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;

import static http.responders.StatusCodes.OK;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RootResponderTest {

  @Rule
  public TemporaryFolder directory = new TemporaryFolder();
  private ServerResponse response;

  @Before
  public void setUp() throws IOException {
    directory.newFile("file1");
    FileReader reader = new FileReader(directory.getRoot());
    response = new RootResponder(reader).response(Request.withMethod("GET").addURI("/").build());
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
