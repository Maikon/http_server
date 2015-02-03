package http.Parsers;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RequestLineParserTest {

  private final String REQUEST_LINE = "GET / HTTP/1.1";
  private final String REQUEST = "GET / HTTP/1.1\r\n" +
                                "Host: localhost\r\n\r\n" +
                                "Body of the Request";

  @Test
  public void parsesRequestLine() {
    RequestLineParser parser = new RequestLineParser();
    StringReader request = new StringReader(REQUEST);
    BufferedReader reader = new BufferedReader(request);
    assertThat(parser.read(reader), is(REQUEST_LINE));
  }

  @Test
  public void parsesRequestLineThatIsPrecededByEmptyLines() {
    RequestLineParser parser = new RequestLineParser();
    StringReader request = new StringReader("\n\n" + REQUEST);
    BufferedReader reader = new BufferedReader(request);
    assertThat(parser.read(reader), is(REQUEST_LINE));
  }
}
