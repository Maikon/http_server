package http.responders;

import http.Request;
import org.junit.Test;

import static http.responders.StatusCodes.NOT_FOUND;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class NotFoundResponderTest {

  @Test
  public void respondsToAnUnknownResourceRequest() {
    ServerResponse res = new NotFoundResponder().response(Request.withMethod("GET").build());
    assertThat(res.getStatus(), is(NOT_FOUND));
  }
}
