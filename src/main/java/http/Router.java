package http;

import http.responders.NotFoundResponder;
import http.responders.Responder;
import http.responders.ServerResponse;
import http.responders.StatusCodes;
import http.utils.RequestLogger;

import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static http.responders.StatusCodes.NOT_FOUND;

public class Router {
  private Map<String, Responder> responders = new HashMap<>();
  private StatusCodes lastResponse;

  public void dispatch(Request request, PrintStream output) throws IOException {
    String identifier = request.methodWithUri();
    Responder responder = responders.get(identifier);
    RequestLogger.log(request);
    if (responder == null) {
      lastResponse = NOT_FOUND;
      responder = new NotFoundResponder();
    }
    ServerResponse response = responder.response(request);
    lastResponse = response.getStatus();
    output.write(response.toBytes());
  }

  public StatusCodes lastResponse() {
    return lastResponse;
  }

  public void registerRoute(String uriWithMethod, Responder responder) {
    responders.put(uriWithMethod, responder);
  }

  public Responder getResponderFor(String identifier) {
    return responders.get(identifier);
  }
}
