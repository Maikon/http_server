package http.parsers;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RequestLineParserTest {


  @Test
  public void parsesRequestLine() {
    String request = "GET / HTTP/1.1\r\n" +
                     "Irrelevant Information";
    BufferedReader reader = new BufferedReader(new StringReader(request));
    RequestLineParser parser = new RequestLineParser();

    assertThat(parser.read(reader), is("GET / HTTP/1.1"));
  }
}
