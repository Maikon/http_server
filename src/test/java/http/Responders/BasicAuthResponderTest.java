package http.responders;

import http.Request;
import org.junit.Test;

import static http.responders.StatusCodes.UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BasicAuthResponderTest {

  private Responder responder = new BasicAuthResponder();
  private Request request = Request.withMethod("GET").addURI("/resource").build();

  @Test
  public void preventsUnauthorizedAccessToResource() {
    ServerResponse response = responder.response(request);
    assertThat(response.getStatus(), is(UNAUTHORIZED));
  }

  @Test
  public void headersContainChallenge() {
    ServerResponse response = responder.response(request);
    assertThat(response.getHeader("WWW-Authenticate"), is("SecureDomain"));
  }

  @Test
  public void bodyContainsAuthorizationWarning() {
    ServerResponse response = responder.response(request);
    assertThat(response.getBody(), is("Authentication required"));
  }
}
