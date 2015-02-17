package http.responders;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MethodNotAllowedResponderTest {

  @Test
  public void respondsToAProhibitedRequestMethod() {
    ServerResponse res = new MethodNotAllowedResponder().response();
    assertThat(res.toString(), is("HTTP/1.1 405 Method Not Allowed\r\n\r\n"));
  }
}
