package http.responders;

import http.Request;

public class BasicAuthResponder implements Responder {
  @Override
  public ServerResponse response(Request request) {
    return ServerResponse.status(StatusCodes.UNAUTHORIZED)
                         .addHeader("WWW-Authenticate", "SecureDomain")
                         .addBody("Authentication required")
                         .build();
  }
}
