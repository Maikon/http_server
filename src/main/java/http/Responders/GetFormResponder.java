package http.responders;

import http.Request;
import http.filesystem.FileReader;

public class GetFormResponder implements Responder {
  private final FileReader reader;

  public GetFormResponder(FileReader reader) {
    this.reader = reader;
  }

  @Override
  public ServerResponse response(Request request) {
    String body = reader.getFileContents(request);
    return ServerResponse.status(StatusCodes.OK).addBody(body).build();
  }
}
