package http.responders;

import org.junit.Test;

import static http.responders.StatusCodes.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MethodNotAllowedResponderTest {

  @Test
  public void respondsToAProhibitedRequestMethod() {
    ServerResponse res = new MethodNotAllowedResponder().response();
    assertThat(res.getStatus(), is(METHOD_NOT_ALLOWED));
  }
}
