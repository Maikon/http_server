package http.responders;

import http.Request;

public class MethodOptionsResponder implements Responder {
  public ServerResponse response(Request request) {
    ServerResponse response = ServerResponse.status(StatusCodes.OK)
                                            .addHeader("Allow", "GET,HEAD,POST,OPTIONS,PUT")
                                            .addBody("")
                                            .build();
    return response;
  }
}
