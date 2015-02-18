package http.responders;

import http.Request;

public class NotFoundResponder implements Responder {
  public ServerResponse response(Request request) {
    return ServerResponse.status(StatusCodes.NOT_FOUND)
                         .build();
  }
}
