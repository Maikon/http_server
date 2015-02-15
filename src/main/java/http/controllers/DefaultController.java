package http.controllers;

import http.Request;
import http.filesystem.FileSystem;
import http.responders.ServerResponse;
import http.responders.StatusCodes;

import java.io.File;

public class DefaultController implements Controller {

  private final FileSystem fs;
  private File directory;

  public DefaultController(File directory) {
    this.directory = directory;
    this.fs = new FileSystem();
  }

  @Override
  public ServerResponse respond(Request request) {
    if(request.getMethod().equals("OPTIONS")) {
      return ServerResponse.status(StatusCodes.OK)
                           .addHeader("Allow", "GET,HEAD,POST,OPTIONS,PUT")
                           .build();
    }
    if (fs.fileExists(directory, request.getUri())) {
      return ServerResponse.status(StatusCodes.OK).build();
    } else {
      return ServerResponse.status(StatusCodes.NOT_FOUND).build();
    }
  }
}
