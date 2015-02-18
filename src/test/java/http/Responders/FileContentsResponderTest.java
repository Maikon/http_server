package http.responders;

import http.Request;
import org.junit.Test;

import static http.responders.StatusCodes.OK;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FileContentsResponderTest {

  private ServerResponse response = new FileContentsResponder().response(Request.withMethod("GET").build());

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
    assertThat(response.getBody(), containsString("<p>file1 contents</p>"));
  }
}