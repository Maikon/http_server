package http.responders;

import http.Request;
import http.filesystem.FileReader;

public class DeleteFormResponder implements Responder {
  private final FileReader reader;

  public DeleteFormResponder(FileReader reader) {
    this.reader = reader;
  }

  @Override
  public ServerResponse response(Request request) {
    reader.findFile(request).delete();
    return ServerResponse.status(StatusCodes.OK).build();
  }
}
