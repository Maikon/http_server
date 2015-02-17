package http.responders;

import org.junit.Test;

import static http.responders.StatusCodes.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SuccessResponderTest {

  @Test
  public void respondsToARootRequest() {
    ServerResponse res = new SuccessResponder().response();
    assertThat(res.getStatus(), is(OK));
  }
}
