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

  @Test
  public void associatesFlagsWithValues() {
    ArgumentsParser parser = createParserWith("-p", "5000", "-d", "directory-path");
    Map<String, String> arguments = new HashMap<>();
    arguments.put("-p", "5000");
    arguments.put("-d", "directory-path");
    assertThat(parser.getArguments(), equalTo(arguments));
  }

  @Test(expected = WrongArgumentsNumberException.class)
  public void raisesExceptionIfIncorrectNumberOfArguments() {
    ArgumentsParser parser = createParserWith("-p", "5000", "-d");
    parser.getArguments();
  }

  @Test(expected = InvalidArgumentsException.class)
  public void raisesExceptionIfInvalidArguments() {
    ArgumentsParser parser = createParserWith("-p", "5000", "directory-path", "-d");
    parser.getArguments();
  }

  @Test
  public void gettingValueForAnArgument() {
    ArgumentsParser parser = createParserWith("-p", "5000");
    Map<String, String> arguments = new HashMap<>();
    arguments.put("-p", "5000");
    assertThat(parser.getArgument("-p"), is("5000"));
  }

  private ArgumentsParser createParserWith(String ... args) {
    return new ArgumentsParser(args);
  }
}