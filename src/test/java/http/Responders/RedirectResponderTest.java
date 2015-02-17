package http.responders;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RedirectResponderTest {

  @Test
  public void respondsToARedirect() {
    ServerResponse res = new RedirectResponder().response();
    assertThat(res.toString(), is("HTTP/1.1 301 Moved Permanently\r\n" +
                                  "Location: http://localhost:5000/\r\n\r\n"));
  }
}
