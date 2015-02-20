package http.responders;

import http.Request;
import http.filesystem.FileReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class PostPutFormResponder implements Responder {
  private final FileReader reader;

  public PostPutFormResponder(FileReader reader) {
    this.reader = reader;
  }

  @Override
  public ServerResponse response(Request request) {
    File file = reader.findFile(request);
    try {
      PrintWriter writer = new PrintWriter(file, "UTF-8");
      writer.write(request.getBody());
      writer.close();
    } catch (FileNotFoundException | UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return ServerResponse.status(StatusCodes.OK).build();
  }
}
