package http.responders;

import org.junit.Test;

import static http.responders.StatusCodes.REDIRECT;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RedirectResponderTest {

  @Test
  public void respondsWithARedirect() {
    ServerResponse res = new RedirectResponder().response();
    assertThat(res.getStatus(), is(REDIRECT));
  }

  @Test
  public void hasTheCorrectLocation() {
    ServerResponse res = new RedirectResponder().response();
    assertThat(res.getHeader("Location"), is("http://localhost:5000/"));
  }
}
