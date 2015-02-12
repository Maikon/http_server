package http.parsers;

import java.io.BufferedReader;
import java.io.IOException;

public class BodyParser {
  public String read(BufferedReader reader, int contentLength) {
    String body = "";
    char[] buffer = new char[contentLength];
    try {
      reader.read(buffer);
      for (char s : buffer) {
        body += s;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return body.trim();
  }
}

