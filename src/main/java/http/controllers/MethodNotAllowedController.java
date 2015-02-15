package http.controllers;

import http.Request;
import http.responders.ServerResponse;
import http.responders.StatusCodes;

public class MethodNotAllowedController implements Controller {

  @Override
  public ServerResponse respond(Request request) {
    return ServerResponse.status(StatusCodes.METHOD_NOT_ALLOWED).build();
  }
}
