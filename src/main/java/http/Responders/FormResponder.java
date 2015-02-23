package http.responders;

import http.Request;
import http.filesystem.FileReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class FormResponder implements Responder {
  private final FileReader reader;

  public FormResponder(FileReader reader) {
    this.reader = reader;
  }

  @Override
  public ServerResponse response(Request request) {
    String method = request.getMethod();
    Responder responder = getResponders().get(method);
    return responder.response(request);
  }

  private Map<String, Responder> getResponders() {
    Map<String, Responder> responders = new HashMap<>();
    responders.put("POST", postPutResponder());
    responders.put("PUT",  postPutResponder());
    responders.put("DELETE", deleteResponder());
    responders.put("GET", getResponder());
    return responders;
  }

  private Responder postPutResponder() {
    return request -> {
      File file = reader.findFile(request);
      try {
        PrintWriter writer = new PrintWriter(file, "UTF-8");
        writer.write(request.getBody());
        writer.close();
      } catch (FileNotFoundException | UnsupportedEncodingException e) {
        e.printStackTrace();
      }
      return ServerResponse.status(StatusCodes.OK).build();
    };
  }

  private Responder deleteResponder() {
    return request -> {
      reader.findFile(request).delete();
      return ServerResponse.status(StatusCodes.OK).build();
    };
  }

  private Responder getResponder() {
    return request -> {
      String body = reader.getFileContents(request);
      return ServerResponse.status(StatusCodes.OK).addBody(body).build();
    };
  }
}
