package http;

import http.parsers.ArgumentsParser;
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
      Router router = new Router(new File(directory));
      Socket clientSocket = new Socket(new ServerSocket(port));
      Worker worker = new Worker(router, clientSocket);
      Server server = new Server(Executors.newFixedThreadPool(20), worker);
      System.out.println("Starting server at port: " + port);
      server.start();
      System.out.println("Closing server...");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
