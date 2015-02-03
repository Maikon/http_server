package http.Parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HeadersParser {

  private final int FIELD = 0;
  private final int VALUE = 1;

  public Map<String, String> read(BufferedReader reader) {
    Map<String, String> headers = new HashMap<>();
    String line;
    try {
      while ((line = reader.readLine()) != null) {
        if (line.equals("")) {
          break;
        }
        if (line.contains(":")) {
          String[] header = line.split(":", 2);
          headers.put(clean(header[FIELD]), clean(header[VALUE]));
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return headers;
  }

  private String clean(String s) {
    return s.trim();
  }
}
