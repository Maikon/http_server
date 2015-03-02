package http;

import http.parsers.ArgumentsParser;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

public class Main {
  public static void main(String[] args) {
    ArgumentsParser parser = new ArgumentsParser(args);
    int port = parser.integerValueFor("-p");
    String directory = parser.stringValueFor("-d");
    try {
      Server server = new Server(new ServerSocket(port), new Router(new File(directory)));
      server.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
