package http;

import http.responders.*;

import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class Router {
  private Map<String, Response> responders;
  private String lastResponse;

  public Router() {
    this.responders = generateResponders();
  }

  public void dispatch(Request request, PrintStream output) {
  }

  public void dispatch(String identifier, PrintStream output) throws IOException {
    Response res = responders.get(identifier);
    if (res == null) {
      lastResponse = "404";
      Response notFound = new NotFoundResponder();
      output.write(notFound.response().getBytes());
    } else {
      output.write(res.response().getBytes());
    }
  }

  private Map<String, Response> generateResponders() {
    Map<String, Response> responders = new HashMap<>();
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
