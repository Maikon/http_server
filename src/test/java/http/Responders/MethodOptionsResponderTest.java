package http.Responders;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MethodOptionsResponderTest {

  @Test
  public void respondsToAnOptionsMethod() {
    MethodOptionsResponder res = new MethodOptionsResponder();
    assertThat(res.response(), is("HTTP/1.1 200 OK\r\nAllow: GET,HEAD,POST,OPTIONS,PUT\r\n"));
  }
}
