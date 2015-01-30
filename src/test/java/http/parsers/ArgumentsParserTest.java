package http.parsers;

import http.Exceptions.InvalidArgumentsException;
import http.Exceptions.WrongArgumentsNumberException;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ArgumentsParserTest {

  private final String DIRECTORY_PATH = "directory-path";
  private final String DIRECTORY_FLAG = "-d";
  private final String PORT_FLAG = "-p";
  private final String PORT_NUMBER = "5000";

  @Test
  public void associatesFlagsWithValues() {
    ArgumentsParser parser = createParserWith(PORT_FLAG, PORT_NUMBER, DIRECTORY_FLAG, DIRECTORY_PATH);
    Map<String, String> arguments = new HashMap<>();
    arguments.put(PORT_FLAG, PORT_NUMBER);
    arguments.put(DIRECTORY_FLAG, DIRECTORY_PATH);
    assertThat(parser.getArguments(), equalTo(arguments));
  }

  @Test(expected = WrongArgumentsNumberException.class)
  public void raisesExceptionIfIncorrectNumberOfArguments() {
    ArgumentsParser parser = createParserWith(PORT_FLAG, PORT_NUMBER, DIRECTORY_FLAG);
    parser.getArguments();
  }

  @Test(expected = InvalidArgumentsException.class)
  public void raisesExceptionIfInvalidArguments() {
    ArgumentsParser parser = createParserWith(PORT_FLAG, PORT_NUMBER, DIRECTORY_PATH, DIRECTORY_FLAG);
    parser.getArguments();
  }

  @Test
  public void gettingValueForAnArgument() {
    ArgumentsParser parser = createParserWith(PORT_FLAG, PORT_NUMBER);
    Map<String, String> arguments = new HashMap<>();
    arguments.put(PORT_FLAG, PORT_NUMBER);
    assertThat(parser.getArgument(PORT_FLAG), is(PORT_NUMBER));
  }

  private ArgumentsParser createParserWith(String ... args) {
    return new ArgumentsParser(args);
  }
}