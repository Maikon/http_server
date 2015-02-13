package http.controllers;

import http.Request;
import http.responders.ServerResponse;

public interface Controller {
  ServerResponse respond(Request request);
}
