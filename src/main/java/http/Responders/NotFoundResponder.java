package http.responders;

import http.Request;

public class NotFoundResponder implements Responder {
  public ServerResponse response(Request request) {
    ServerResponse response = ServerResponse.status(StatusCodes.NOT_FOUND).build();
    return response;
  }
}
