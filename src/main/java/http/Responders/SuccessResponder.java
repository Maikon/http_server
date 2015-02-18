package http.responders;

import http.Request;

public class SuccessResponder implements Responder {
  public ServerResponse response(Request request) {
    ServerResponse response = ServerResponse.status(StatusCodes.OK).build();
    return response;
  }
}
