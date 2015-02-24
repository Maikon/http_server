package http.responders;

import http.Request;
import http.filesystem.FileIO;

public class FileContentsResponder implements Responder {
  private FileIO fileIO;

  public FileContentsResponder(FileIO fileIO) {
    this.fileIO = fileIO;
  }

  public ServerResponse response(Request request) {
    String body = fileIO.getFileContents(request);
    return ServerResponse.status(StatusCodes.OK)
                         .addHeader("Content-Type", "text/html")
                         .addBody(body).build();
  }
}
