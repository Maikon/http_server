package http.responders;

import http.Request;

import static http.responders.StatusCodes.OK;
import static http.responders.StatusCodes.UNAUTHORIZED;

public class BasicAuthResponder implements Responder {

  @Override
  public ServerResponse response(Request request) {
    String headerValue = getAuthorizationHeader(request);
    if (authenticated(headerValue)) {
      return ServerResponse.status(OK).build();
    }
    return ServerResponse.status(UNAUTHORIZED)
                         .addHeader("WWW-Authenticate", "SecureDomain")
                         .addBody("Authentication required")
                         .build();
  }

  private String getAuthorizationHeader(Request request) {
    return request.getHeaders().getOrDefault("Authorization", "");
  }

  private boolean authenticated(String headerValue) {
    if (headerValue.isEmpty()) {
      return false;
    }
    String encoded = encodedCredentials(headerValue);
    Authenticator authenticator = new Authenticator(encoded).invoke();
    return authenticator.validAuthentication();
  }

  private String encodedCredentials(String headerValue) {
    return headerValue.split("\\s")[1];
  }

  private class Authenticator {
    private String encodedCredentials;
    private String username;
    private String password;

    public Authenticator(String encodedCredentials) {
      this.encodedCredentials = encodedCredentials;
    }

    public Authenticator invoke() {
      String credentials = encodedCredentials;
      byte[] decoded = org.apache.commons.codec.binary.Base64.decodeBase64(credentials);
      String[] decodedCredentials = new String(decoded).split(":");
      username = decodedCredentials[0];
      password = decodedCredentials[1];
      return this;
    }

    public boolean validAuthentication() {
      return "admin".equals(username) && "hunter2".equals(password);
    }
  }
}
