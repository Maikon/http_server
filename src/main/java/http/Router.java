package http;

import http.filesystem.FileIO;
import http.filters.Authenticator;
import http.responders.*;
import http.utils.Logger;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class Router {
  private FileIO fileIO;
  private Map<String, Responder> responders;
  private String lastResponse;

  public Router() {
    this.responders = generateResponders();
  }

  public Router(File directory) {
    this.fileIO = new FileIO(directory);
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

  private Map<String, Responder> generateResponders() {
    Map<String, Responder> responders = new HashMap<>();
    responders.put("GET /",                   new RootResponder(fileIO));
    responders.put("GET /redirect",           new RedirectResponder());
    responders.put("GET /foobar",             new NotFoundResponder());
    responders.put("GET /file1",              new FileContentsResponder(fileIO));
    responders.put("GET /form",               new FormResponder(fileIO));
    responders.put("POST /form",              new FormResponder(fileIO));
    responders.put("PUT /form",               new FormResponder(fileIO));
    responders.put("DELETE /form",            new FormResponder(fileIO));
    responders.put("POST /text-file.txt",     new MethodNotAllowedResponder());
    responders.put("PUT /file1",              new MethodNotAllowedResponder());
    responders.put("OPTIONS /method_options", new MethodOptionsResponder());
    responders.put("GET /parameters",         new ParamsResponder());
    responders.put("GET /patch-content.txt",  new PatchResponder(fileIO));
    responders.put("PATCH /patch-content.txt", new PatchResponder(fileIO));
    responders.put("GET /logs",               new BasicAuthResponder(new Authenticator("admin", "hunter2")));
    return responders;
  }

  public String lastResponse() {
    return lastResponse;
  }
}
