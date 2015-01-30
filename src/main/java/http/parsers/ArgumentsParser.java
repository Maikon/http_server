package http.parsers;

import http.Exceptions.WrongArgumentsNumberException;

import java.util.HashMap;
import java.util.Map;

public class ArgumentsParser {
  private final String[] args;

  public ArgumentsParser(String[] args) {
    this.args = args;
  }

  public Map<String, String> getArguments() {
    Map<String, String> arguments = new HashMap<>();
    if (wrongArgumentsNumber()) {
      throw new WrongArgumentsNumberException();
    }
    return buildArguments(arguments);
  }

  private Map<String, String> buildArguments(Map<String, String> arguments) {
    for (int i = 0; i < args.length; i+=2) {
      arguments.put(args[i], args[i+1]);
    }
    return arguments;
  }

  private boolean wrongArgumentsNumber() {
    return !(args.length % 2 == 0);
  }
}
