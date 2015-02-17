package http.responders;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class NotFoundResponderTest {

  @Test
  public void respondsToAnUnknownResourceRequest() {
    ServerResponse res = new NotFoundResponder().response();
    assertThat(res.toString(), is("HTTP/1.1 404 Not Found\r\n\r\n"));
  }
}
