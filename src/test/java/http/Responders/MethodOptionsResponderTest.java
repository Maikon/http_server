package http.responders;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MethodOptionsResponderTest {

  @Test
  public void respondsToAnOptionsMethod() {
    ServerResponse res = new MethodOptionsResponder().response();
    assertThat(res.toString(), is("HTTP/1.1 200 OK\r\n" +
                                  "Allow: GET,HEAD,POST,OPTIONS,PUT\r\n\r\n"));
  }
}
