package http.controllers;

import http.responders.ServerResponse;

public interface Controller {
  ServerResponse respond();
}
