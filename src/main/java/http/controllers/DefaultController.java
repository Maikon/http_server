package http.controllers;

import http.Request;
import http.filesystem.FileSystem;
import http.responders.ServerResponse;
import http.responders.StatusCodes;

import java.io.File;

public class DefaultController implements Controller {

  private final FileSystem fs;

  public DefaultController(File directory) {
    this.fs = new FileSystem(directory);
  }

  @Override
  public ServerResponse respond(Request request) {
    if(request.getMethod().equals("OPTIONS")) {
      return ServerResponse.status(StatusCodes.OK)
                           .addHeader("Allow", "GET,HEAD,POST,OPTIONS,PUT")
                           .build();
    }
    if (fs.fileExists(request.getUri()) && request.getMethod().equals("GET")) {
      String file = request.getUri();
      return ServerResponse.status(StatusCodes.OK)
                           .addHeader("Content-Length", String.valueOf(fs.contentLength(file)))
                           .addBody(new String(fs.readFile(request.getUri())))
                           .build();
    } else {
      return ServerResponse.status(StatusCodes.OK)
                           .addBody("")
                           .build();
    }
  }
}
