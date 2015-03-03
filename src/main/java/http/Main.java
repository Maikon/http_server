package http;

import http.parsers.ArgumentsParser;

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
      ServerSocket socket = new ServerSocket(port);
      Router router = new Router(new File(directory));
      Server server = new Server(Executors.newFixedThreadPool(20), socket, router);
      System.out.println("Starting server at port: " + port);
      server.start();
      System.out.println("Closing server...");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
