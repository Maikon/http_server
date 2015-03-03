package http;

import http.responders.NotFoundResponder;
import http.responders.Responder;
import http.utils.Logger;

import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class Router {
  private Map<String, Responder> responders = new HashMap<>();
  private String lastResponse;

  public Router() {
    this.responders = generateResponders();
  }

  public void dispatch(Request request, PrintStream output) throws IOException {
    String identifier = request.methodWithUri();
    Responder responder = responders.get(identifier);
    Logger.log(request);
    if (responder == null) {
      lastResponse = "404";
      responder = new NotFoundResponder();
    }
    output.write(responder.response(request).toBytes());
  }

  public String lastResponse() {
    return lastResponse;
  }

  public void registerRoute(String uriWithMethod, Responder responder) {
    responders.put(uriWithMethod, responder);
  }

  public Responder getResponderFor(String identifier) {
    return responders.get(identifier);
  }

  private Map<String, Responder> generateResponders() {
    return responders;
  }
}
