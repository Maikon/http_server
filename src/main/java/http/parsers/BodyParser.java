package http.Parsers;

import java.io.BufferedReader;

public class BodyParser {
  public String read(BufferedReader reader) {
    Object[] lines = reader.lines().toArray();
    return getBodyLines(lines);
  }

  private String getBodyLines(Object[] lines) {
    int emptyLineIndex = findEmptyLine(lines);
    String body = "";
    for (int i = emptyLineIndex; i < lines.length; i++) {
      body += lines[i] + "\n";
    }
    return body;
  }

  private int findEmptyLine(Object[] lines) {
    int emptyLineIndex = 0;
    for (int i = 0; i < lines.length; i++) {
      if(lines[i].equals("")) {
        emptyLineIndex = i + 1;
      }
    }
    return emptyLineIndex;
  }
}

