package http;

import http.Responders.*;

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

  public void dispatch(String uri, PrintStream output) throws IOException {
    Response res = responders.get(uri);
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
    responders.put("/", new RootResponder());
    return responders;
  }

  public String lastResponse() {
    return lastResponse;
  }
}
