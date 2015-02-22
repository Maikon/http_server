package http.responders;

import http.Request;
import http.filesystem.FileReader;

import static http.responders.StatusCodes.*;

public class PatchResponder implements Responder {
  private final FileReader reader;

  public PatchResponder(FileReader reader) {
    this.reader = reader;
  }

  @Override
  public ServerResponse response(Request request) {
    String body = reader.getFileContents(request);
    return ServerResponse.status(OK)
                         .addBody(body)
                         .build();
  }
}
