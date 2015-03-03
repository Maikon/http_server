package http;

import http.filesystem.FileIO;
import http.filters.Authenticator;
import http.parsers.ArgumentsParser;
import http.responders.*;
import http.sockets.Socket;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class Main {
  public static void main(String[] args) {
    ArgumentsParser parser = new ArgumentsParser(args);
    int port = parser.integerValueFor("-p");
    String directory = parser.stringValueFor("-d");
    try {
      Router router = new Router();
      Socket clientSocket = new Socket(new ServerSocket(port));
      Worker worker = new Worker(router, clientSocket);
      FileIO fileIO = new FileIO(new File(directory));
      registerRoutes(router, fileIO);
      Server server = new Server(Executors.newFixedThreadPool(20), worker);
      startingServerMessage(port);
      server.start();
      closingServerMessage();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void startingServerMessage(int port) {
    System.out.println("Starting server at port: " + port);
  }

  private static void closingServerMessage() {
    System.out.println("Closing server...");
  }

  private static void registerRoutes(Router router, FileIO fileIO) {
    router.registerRoute("GET /", new RootResponder(fileIO));
    router.registerRoute("GET /redirect", new RedirectResponder());
    router.registerRoute("GET /foobar", new NotFoundResponder());
    router.registerRoute("GET /file1", new FileContentsResponder(fileIO));
    router.registerRoute("GET /form", new FormResponder(fileIO));
    router.registerRoute("GET /patch-content.txt", new PatchResponder(fileIO));
    router.registerRoute("GET /parameters", new ParamsResponder());
    router.registerRoute("GET /logs", new BasicAuthResponder(new Authenticator("admin", "hunter2")));
    router.registerRoute("GET /image.jpeg", new ImagesResponder(fileIO));
    router.registerRoute("GET /image.png", new ImagesResponder(fileIO));
    router.registerRoute("GET /image.gif", new ImagesResponder(fileIO));
    router.registerRoute("GET /partial_content.txt", new RangeResponder(fileIO));
    router.registerRoute("POST /form", new FormResponder(fileIO));
    router.registerRoute("POST /text-file.txt", new MethodNotAllowedResponder());
    router.registerRoute("PUT /form", new FormResponder(fileIO));
    router.registerRoute("PUT /file1", new MethodNotAllowedResponder());
    router.registerRoute("PATCH /patch-content.txt", new PatchResponder(fileIO));
    router.registerRoute("DELETE /form", new FormResponder(fileIO));
    router.registerRoute("OPTIONS /method_options", new MethodOptionsResponder());
  }
}
