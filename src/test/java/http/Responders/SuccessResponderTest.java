package http.responders;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SuccessResponderTest {

  @Test
  public void respondsToARootRequest() {
    ServerResponse res = new SuccessResponder().response();
    assertThat(res.toString(), is("HTTP/1.1 200 OK\r\n\r\n"));
  }
}
