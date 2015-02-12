package http;

import http.parsers.ArgumentsParser;

import java.io.IOException;

public class Main {
  public static void main(String[] args) {
    ArgumentsParser parser = new ArgumentsParser(args);
    int port = parser.integerValueFor("-p");
    String directory = parser.stringValueFor("-d");
    try {
      Server server = new Server(port, directory);
      server.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
