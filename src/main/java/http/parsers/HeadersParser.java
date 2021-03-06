package http.parsers;

import http.utils.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HeadersParser {
  private final Logger logger = new Logger(org.apache.log4j.Logger.getLogger(HeadersParser.class));

  private final int FIELD = 0;
  private final int VALUE = 1;
  private Map<String, String> headers = new HashMap<>();

  public Map<String, String> read(BufferedReader reader) {
    String line;
    try {
      while ((line = reader.readLine()) != null) {
        if (empty(line)) {
          break;
        }
        createHeaderFrom(line);
      }
    } catch (IOException e) {
      logger.logError(e);
    }
    return headers;
  }

  private boolean empty(String line) {
    return line.equals("");
  }

  private void createHeaderFrom(String line) {
    if (line.contains(":")) {
      String[] header = line.split(":", 2);
      headers.put(clean(header[FIELD]), clean(header[VALUE]));
    }
  }

  private String clean(String s) {
    return s.trim();
  }
}
