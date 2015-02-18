package http.responders;

import http.Request;

public class MethodNotAllowedResponder implements Responder {
  public ServerResponse response(Request request) {
    ServerResponse response = ServerResponse.status(StatusCodes.METHOD_NOT_ALLOWED)
                                            .build();
    return response;
  }
}
