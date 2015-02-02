package http.Parsers;

import http.Exceptions.InvalidArgumentsException;
import http.Exceptions.InvalidPortException;
import http.Exceptions.WrongArgumentsNumberException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ArgumentsParser {
  private final String[] args;

  public ArgumentsParser(String ... args) {
    this.args = args;
  }

  public String stringValueFor(String key) {
    return getArguments().get(key);
  }

  public int integerValueFor(String key) {
    try {
      return Integer.parseInt(getArguments().get(key));
    } catch (NumberFormatException e) {
      throw new InvalidPortException();
    }
  }

  public Map<String, String> getArguments() {
    if (wrongArgumentsNumber()) {
      throw new WrongArgumentsNumberException();
    }
    return buildArguments();
  }

  private Map<String, String> buildArguments() {
    Map<String, String> arguments = new HashMap<>();
    for (int i = 0; i < args.length; i+=2) {
      arguments.put(args[i], args[i+1]);
    }
    checkForInvalidArguments(arguments);
    return arguments;
  }

  private void checkForInvalidArguments(Map<String, String> arguments) {
    Optional<String> badKey = arguments.keySet().stream()
                                                .filter(k -> !k.startsWith("-"))
                                                .findAny();
    if (badKey.isPresent()) {
      throw new InvalidArgumentsException();
    }
  }

  private boolean wrongArgumentsNumber() {
    return !(args.length % 2 == 0);
  }
}
