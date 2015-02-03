package http.Parsers;

import java.io.BufferedReader;
import java.util.Scanner;

public class RequestLine {

  public static String parse(BufferedReader reader) {
    Scanner scanner = new Scanner(reader);
    String line = scanner.nextLine();
    while (isEmpty(line)) {
      line = scanner.nextLine();
    }
    return line.trim();
  }

  private static boolean isEmpty(String line) {
    return line.equals("");
  }
}
