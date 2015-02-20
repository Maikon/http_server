package http.responders;

import http.Request;
import http.filesystem.FileReader;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class FormResponder implements Responder {
  private final FileReader reader;

  public FormResponder(FileReader reader) {
    this.reader = reader;
  }

  public Map<String, Responder> getResponders() {
    Map<String, Responder> responders = new HashMap<>();
    responders.put("POST", new PostPutFormResponder());
    responders.put("PUT", new PostPutFormResponder());
    responders.put("DELETE", new DeleteFormResponder());
    responders.put("GET", new GetFormResponder());
    return responders;
  }

  @Override
  public ServerResponse response(Request request) {
    String method = request.getMethod();
    Responder responder = getResponders().get(method);
    return responder.response(request);
  }

  private File getFile(Request request) {
    return reader.findFile(request);
  }

  private class PostPutFormResponder implements Responder {
    @Override
    public ServerResponse response(Request request) {
      File file = getFile(request);
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

  private class DeleteFormResponder implements Responder {
    @Override
    public ServerResponse response(Request request) {
      getFile(request).delete();
      return ServerResponse.status(StatusCodes.OK).build();
    }
  }

  private class GetFormResponder implements Responder {
    @Override
    public ServerResponse response(Request request) {
      File file = getFile(request);
      String body = "";
      try {
        byte[] data = Files.readAllBytes(file.toPath());
        body = new String(data);
      } catch (IOException e) {
        e.printStackTrace();
      }
      return ServerResponse.status(StatusCodes.OK).addBody(body).build();
    }
  }
}
