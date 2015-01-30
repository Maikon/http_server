package http.parsers;

import http.Exceptions.WrongArgumentsNumberException;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ArgumentsParserTest {

  @Test
  public void associatesFlagsWithValues() {
    ArgumentsParser parser = new ArgumentsParser(new String[] {"-p", "5000", "-d", "directory-path"});
    Map<String, String> arguments = new HashMap<>();
    arguments.put("-p", "5000");
    arguments.put("-d", "directory-path");
    assertThat(parser.getArguments(), equalTo(arguments));
  }

  @Test(expected = WrongArgumentsNumberException.class)
  public void raisesExceptionIfIncorrectNumberOfArguments() {
    ArgumentsParser parser = new ArgumentsParser(new String[] {"-p", "5000", "-d"});
    parser.getArguments();
  }
}