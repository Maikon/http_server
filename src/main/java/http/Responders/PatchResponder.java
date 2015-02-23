package http.responders;

import http.Request;
import http.filesystem.FileReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import static http.responders.StatusCodes.NO_CONTENT;
import static http.responders.StatusCodes.OK;

public class PatchResponder implements Responder {
  private final FileReader reader;

  public PatchResponder(FileReader reader) {
    this.reader = reader;
  }

  @Override
  public ServerResponse response(Request request) {
    String body = reader.getFileContents(request);
    if (request.getMethod().equals("PATCH")) {
      try {
        File file = reader.findFile(request);
        PrintWriter writer = new PrintWriter(file, "UTF-8");
        writer.write(request.getBody());
        writer.close();
      } catch (FileNotFoundException | UnsupportedEncodingException e) {
        e.printStackTrace();
      }
      return ServerResponse.status(NO_CONTENT).build();
    }
    return ServerResponse.status(OK)
                         .addBody(body)
                         .build();
  }
}
