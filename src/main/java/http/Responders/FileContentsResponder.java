package http.responders;

import http.Request;

public class FileContentsResponder implements Responder {
  public ServerResponse response(Request request) {
    return ServerResponse.status(StatusCodes.OK)
                         .addHeader("Content-Type", "text/html")
                         .addBody("<html><head></head><body>" +
                                  "<p>file1 contents</p>" +
                                  "</body></html>")
                         .build();
  }
}
