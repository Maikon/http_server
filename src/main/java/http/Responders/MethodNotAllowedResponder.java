package http.responders;

import http.Request;

public class MethodNotAllowedResponder implements Responder {
    public ServerResponse response(Request request) {
        return ServerResponse.status(StatusCodes.METHOD_NOT_ALLOWED)
          .build();
    }
}
