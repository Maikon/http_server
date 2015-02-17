package http.controllers;

import http.responders.ServerResponse;
import http.responders.StatusCodes;

public class BaseController {
  public ServerResponse doGet() {
    return defaultResponse();
  }

  public ServerResponse doPost() {
    return defaultResponse();
  }

  public ServerResponse doPut() {
    return defaultResponse();
  }

  public ServerResponse doDelete() {
    return defaultResponse();
  }

  public ServerResponse doOptions() {
    return defaultResponse();
  }

  private ServerResponse defaultResponse() {
    return ServerResponse.status(StatusCodes.METHOD_NOT_ALLOWED).build();
  }
}
