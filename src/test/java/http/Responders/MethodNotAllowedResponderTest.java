package http.Responders;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MethodNotAllowedResponderTest {

  @Test
  public void respondsToAProhibitedRequestMethod() {
    MethodNotAllowedResponder res = new MethodNotAllowedResponder();
    assertThat(res.response(), is("HTTP 405 Method Not Allowed\r\n"));
  }
}