package http.responders;

import http.Request;

public class MethodOptionsResponder implements Responder {
    public ServerResponse response(Request request) {
        return ServerResponse.status(StatusCodes.OK)
          .addHeader("Allow", "GET,HEAD,POST,OPTIONS,PUT")
          .addBody("")
          .build();
    }
}
