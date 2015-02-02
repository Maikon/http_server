package http;

import http.Parsers.ArgumentsParser;

import java.io.IOException;

public class Main {
  public static void main(String[] args) {
    ArgumentsParser parser = new ArgumentsParser(args);
    int port = parser.integerValueFor("-p");
    String directory = parser.stringValueFor("-d");
    try {
      new Server(port, directory);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
