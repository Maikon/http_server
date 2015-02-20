package http.responders;

import http.Request;
import http.filesystem.FileReader;

import java.util.HashMap;
import java.util.Map;

public class FormResponder implements Responder {
  private final FileReader reader;

  public FormResponder(FileReader reader) {
    this.reader = reader;
  }

  public Map<String, Responder> getResponders() {
    Map<String, Responder> responders = new HashMap<>();
    responders.put("POST", new PostPutFormResponder(reader));
    responders.put("PUT", new PostPutFormResponder(reader));
    responders.put("DELETE", new DeleteFormResponder(reader));
    responders.put("GET", new GetFormResponder(reader));
    return responders;
  }

  @Override
  public ServerResponse response(Request request) {
    String method = request.getMethod();
    Responder responder = getResponders().get(method);
    return responder.response(request);
  }
}
