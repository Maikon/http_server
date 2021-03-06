package http.responders;

import http.Request;
import org.junit.Test;

import static http.responders.StatusCodes.OK;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MethodOptionsResponderTest {

  private ServerResponse response = new MethodOptionsResponder().response(Request.withMethod("GET").build());

  @Test
  public void respondsWithSuccess() {
    assertThat(response.getStatus(), is(OK));
  }

  @Test
  public void respondsWithCorrectHeader() {
    assertThat(response.getHeader("Allow"), is("GET,HEAD,POST,OPTIONS,PUT"));
  }
}
