package http;

import http.responders.*;

import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class Router {
  private FileSystem fs;
  private Map<String, Responder> responders;
  private String lastResponse;

  public Router() {
    this.responders = generateResponders();
  }

  public void dispatch(Request request, PrintStream output) throws IOException {
    String identifier = request.methodWithUri();
    Responder res = responders.get(identifier);
    if (res == null) {
      lastResponse = "404";
      res = new NotFoundResponder();
    }
    output.write(res.response().getBytes());
  }

  private Map<String, Responder> generateResponders() {
    Map<String, Responder> responders = new HashMap<>();
    responders.put("GET /", new RootResponder());
    responders.put("GET /redirect", new RedirectResponder());
    responders.put("GET /foobar", new NotFoundResponder());
    responders.put("GET /file1", new FileContentsResponder());
    responders.put("POST /form", new SuccessResponder());
    responders.put("POST /text-file.txt", new MethodNotAllowedResponder());
    responders.put("PUT /form", new SuccessResponder());
    responders.put("PUT /file1", new MethodNotAllowedResponder());
    responders.put("OPTIONS /method_options", new MethodOptionsResponder());
    return responders;
  }

  public String lastResponse() {
    return lastResponse;
  }
}
