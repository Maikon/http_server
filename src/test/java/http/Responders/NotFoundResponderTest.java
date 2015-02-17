package http.responders;

import org.junit.Test;

import static http.responders.StatusCodes.NOT_FOUND;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class NotFoundResponderTest {

  @Test
  public void respondsToAnUnknownResourceRequest() {
    ServerResponse res = new NotFoundResponder().response();
    assertThat(res.getStatus(), is(NOT_FOUND));
  }
}
