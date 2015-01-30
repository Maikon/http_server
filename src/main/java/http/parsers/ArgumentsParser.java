package http.parsers;

import java.util.HashMap;
import java.util.Map;

public class ArgumentsParser {
  private final String[] args;

  public ArgumentsParser(String[] args) {
    this.args = args;
  }

  public Map<String, String> getArguments() {
    Map<String, String> arguments = new HashMap<>();
    for (int i = 0; i < args.length; i+=2) {
      arguments.put(args[i], args[i+1]);
    }
    return arguments;
  }
}
