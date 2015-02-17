package http.controllers;

import http.Request;
import http.responders.ServerResponse;
import http.responders.StatusCodes;

public class BaseController {

  public void dispatch(Request request) {
    switch (request.getMethod()) {
      case "GET":
        doGet();
        break;
      case "POST":
        doPost();
        break;
      case "PUT":
        doPut();
        break;
      case "DELETE":
        doDelete();
        break;
      case "OPTIONS":
        doOptions();
        break;
    }
  }

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
