package http.responders;

import http.Request;

import static http.responders.StatusCodes.*;

public class RedirectResponder implements Responder {
  public ServerResponse response(Request request) {
    ServerResponse response = ServerResponse.status(REDIRECT)
                                            .addHeader("Location", "http://localhost:5000/")
                                            .addBody("")
                                            .build();
    return response;
  }
}
