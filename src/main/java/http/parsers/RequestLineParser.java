package http.parsers;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestLineParser {

  public String read(BufferedReader reader) {
    String line = null;
    try {
      line = reader.readLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return line;
  }
}
