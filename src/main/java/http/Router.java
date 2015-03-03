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
  private Map<String, Responder> responders = new HashMap<>();
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
    if (request.getUri().contains("image")) {
      responder = new ImagesResponder(fileIO);
    }
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
    registerRoute("GET /",                   new RootResponder(fileIO));
    registerRoute("GET /redirect",           new RedirectResponder());
    registerRoute("GET /foobar",             new NotFoundResponder());
    registerRoute("GET /file1",              new FileContentsResponder(fileIO));
    registerRoute("GET /form",               new FormResponder(fileIO));
    registerRoute("POST /form",              new FormResponder(fileIO));
    registerRoute("PUT /form",               new FormResponder(fileIO));
    registerRoute("DELETE /form",            new FormResponder(fileIO));
    registerRoute("POST /text-file.txt",     new MethodNotAllowedResponder());
    registerRoute("PUT /file1",              new MethodNotAllowedResponder());
    registerRoute("OPTIONS /method_options", new MethodOptionsResponder());
    registerRoute("GET /parameters",         new ParamsResponder());
    registerRoute("GET /patch-content.txt",  new PatchResponder(fileIO));
    registerRoute("PATCH /patch-content.txt", new PatchResponder(fileIO));
    registerRoute("GET /logs",               new BasicAuthResponder(new Authenticator("admin", "hunter2")));
    registerRoute("GET /partial_content.txt", new RangeResponder(fileIO));
    return responders;
  }
}
