package http.Parsers;

import java.io.BufferedReader;
import java.util.Scanner;

public class RequestLineParser {

  public String read(BufferedReader reader) {
    Scanner scanner = new Scanner(reader);
    String line = scanner.nextLine();
    while (isEmpty(line)) {
      line = scanner.nextLine();
    }
    return line.trim();
  }

  private boolean isEmpty(String line) {
    return line.equals("");
  }
}
