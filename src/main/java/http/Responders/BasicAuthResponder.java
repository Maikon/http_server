package http.responders;

import http.Request;
import http.filters.Authenticator;
import http.utils.Logger;

import static http.responders.StatusCodes.OK;
import static http.responders.StatusCodes.UNAUTHORIZED;

public class BasicAuthResponder implements Responder {

  private final Authenticator authenticator;

  public BasicAuthResponder(Authenticator authenticator) {
    this.authenticator = authenticator;
  }

  @Override
  public ServerResponse response(Request request) {
    String headerValue = getAuthorizationHeader(request);
    if (authenticated(headerValue)) {
      return ServerResponse.status(OK)
                           .addBody(bodyContainingLogs())
                           .build();
    }
    return ServerResponse.status(UNAUTHORIZED)
                         .addHeader("WWW-Authenticate", "SecureDomain")
                         .addBody("Authentication required")
                         .build();
  }

  private String bodyContainingLogs() {
    String body = "";
    for (String requestLine : Logger.loggedRequests()) {
      body += requestLine + "\n";
    }
    return body;
  }

  private String getAuthorizationHeader(Request request) {
    return request.getHeaders().getOrDefault("Authorization", "");
  }

  private boolean authenticated(String headerValue) {
    if (headerValue.isEmpty()) {
      return false;
    }
    String encoded = encodedCredentials(headerValue);
    return authenticator.validAuthentication(encoded);
  }

  private String encodedCredentials(String headerValue) {
    return headerValue.split("\\s")[1];
  }
}
