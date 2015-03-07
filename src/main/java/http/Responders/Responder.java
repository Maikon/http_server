package http.responders;

import http.Request;

public interface Responder {
    ServerResponse response(Request request);
}
