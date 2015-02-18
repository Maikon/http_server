package http.responders;

import http.Request;

public class SuccessResponder implements Responder {
  public ServerResponse response(Request request) {
    return ServerResponse.status(StatusCodes.OK)
                         .build();
  }
}
