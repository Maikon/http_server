package http.Parsers;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RequestLineTest {

  public final String REQUEST_LINE = "GET / HTTP/1.1";
  public final String REQUEST = "GET / HTTP/1.1\r\n" +
                                "Host: localhost\r\n\r\n" +
                                "Body of the Request";

  @Test
  public void parsesRequestLine() {
    StringReader request = new StringReader(REQUEST);
    BufferedReader reader = new BufferedReader(request);
    assertThat(RequestLine.parse(reader), is(REQUEST_LINE));
  }

  @Test
  public void parsesRequestLineThatIsPrecededByEmptyLines() {
    StringReader request = new StringReader("\n\n" + REQUEST);
    BufferedReader reader = new BufferedReader(request);
    assertThat(RequestLine.parse(reader), is(REQUEST_LINE));
  }
}
