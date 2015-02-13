package http.controllers;

import http.Request;
import http.responders.ServerResponse;
import http.responders.StatusCodes;

public class NotFoundController implements Controller {
  @Override
  public ServerResponse respond(Request request) {
    return ServerResponse.status(StatusCodes.NOT_FOUND).build();
  }
}
