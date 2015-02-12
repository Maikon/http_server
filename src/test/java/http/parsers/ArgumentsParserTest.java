package http.parsers;

import http.exceptions.InvalidArgumentsException;
import http.exceptions.InvalidPortException;
import http.exceptions.WrongArgumentsNumberException;
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

  @Test(expected = InvalidPortException.class)
  public void raisesExceptionIfInvalidPort() {
    ArgumentsParser parser = createParserWith(PORT_FLAG, "invalidPort", DIRECTORY_FLAG, DIRECTORY_PATH);
    parser.integerValueFor("-p");
  }

  @Test
  public void gettingIntegerValueForAnArgument() {
    ArgumentsParser parser = createParserWith(PORT_FLAG, PORT_NUMBER);
    assertThat(parser.integerValueFor(PORT_FLAG), is(5000));
  }

  @Test
  public void gettingStringValueForAnArgument() {
    ArgumentsParser parser = createParserWith(DIRECTORY_FLAG, DIRECTORY_PATH);
    assertThat(parser.stringValueFor(DIRECTORY_FLAG), is(DIRECTORY_PATH));
  }

  private ArgumentsParser createParserWith(String ... args) {
    return new ArgumentsParser(args);
  }
}