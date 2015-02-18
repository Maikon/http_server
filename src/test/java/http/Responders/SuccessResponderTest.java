package http.responders;

import http.Request;
import org.junit.Test;

import static http.responders.StatusCodes.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SuccessResponderTest {

  @Test
  public void respondsToARootRequest() {
    ServerResponse res = new SuccessResponder().response(Request.withMethod("GET").build());
    assertThat(res.getStatus(), is(OK));
  }
}
