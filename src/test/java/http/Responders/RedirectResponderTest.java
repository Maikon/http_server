package http.responders;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RedirectResponderTest {

  @Test
  public void respondsToARedirect() {
    RedirectResponder res = new RedirectResponder();
    assertThat(res.response(), is("HTTP/1.1 301 Moved Permanently\r\nLocation: http://localhost:5000/\r\n"));
  }
}
