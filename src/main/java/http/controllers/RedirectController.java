package http.controllers;

import http.Request;
import http.responders.ServerResponse;
import http.responders.StatusCodes;

public class RedirectController implements Controller {

  @Override
  public ServerResponse respond(Request request) {
    return ServerResponse.status(StatusCodes.REDIRECT).build();
  }
}
