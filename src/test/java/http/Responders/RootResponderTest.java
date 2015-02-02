package http.Responders;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RootResponderTest {

  @Test
  public void respondsToARootRequest() {
    RootResponder res = new RootResponder();
    assertThat(res.response(), is("HTTP/1.1 200 OK\r\n"));
  }
}
