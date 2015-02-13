package http.controllers;

import http.responders.ServerResponse;
import http.responders.StatusCodes;

public class NotFoundController implements Controller {
  @Override
  public ServerResponse respond() {
    return ServerResponse.status(StatusCodes.NOT_FOUND).build();
  }
}
