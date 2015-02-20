package http.responders;

import http.Request;
import http.filesystem.FileReader;

public class FileContentsResponder implements Responder {
  private FileReader reader;

  public FileContentsResponder(FileReader reader) {
    this.reader = reader;
  }

  public ServerResponse response(Request request) {
    String body = reader.getFileContents(request);
    return ServerResponse.status(StatusCodes.OK)
                         .addHeader("Content-Type", "text/html")
                         .addBody(body).build();
  }
}
