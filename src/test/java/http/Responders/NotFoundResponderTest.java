package http.responders;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class NotFoundResponderTest {

  @Test
  public void respondsToAnUnknownResourceRequest() {
    NotFoundResponder res = new NotFoundResponder();
    assertThat(res.response(), is("HTTP/1.1 404 Not Found\r\n"));
  }
}
