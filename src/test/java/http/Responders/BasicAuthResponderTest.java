package http.responders;

import http.Request;
import http.filters.Authenticator;
import http.utils.Logger;
import org.junit.Test;

import static http.responders.StatusCodes.OK;
import static http.responders.StatusCodes.UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class BasicAuthResponderTest {

  private Responder responder = new BasicAuthResponder(new Authenticator("user", "password"));
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

  @Test
  public void allowsAccessIfCredentialsMatch() {
    Responder responder = new BasicAuthResponder(new Authenticator("admin", "hunter2"));
    Request request = Request.withMethod("GET")
                             .addURI("/logs")
                             .addHeader("Authorization", "Basic YWRtaW46aHVudGVyMg==")
                             .build();
    ServerResponse response = responder.response(request);
    assertThat(response.getStatus(), is(OK));
  }

  @Test
  public void bodyOfResponseContainsStatusLinesOfPastRequests() {
    Authenticator authenticator = new Authenticator("admin", "hunter2");
    Responder responder = new BasicAuthResponder(authenticator);
    Request request2 = Request.withMethod("POST").addURI("/file2").build();
    Request request3 = Request.withMethod("GET").addURI("/logs")
                                                .addHeader("Authorization", "Basic YWRtaW46aHVudGVyMg==")
                                                .build();
    Logger.log(request2);
    Logger.log(request3);
    ServerResponse response = responder.response(request3);
    assertThat(response.getBody(), allOf(containsString("GET /logs HTTP/1.1"),
                                         containsString("POST /file2 HTTP/1.1")));
  }
}
