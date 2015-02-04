package http.Parsers;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BodyParserTest {

  @Test
  public void parsesBodyWithOneLine() {
    BodyParser parser = new BodyParser();
    StringReader request = new StringReader("GET / HTTP/1.1\r\n" +
                                            "Header-1: content\r\n" +
                                            "Header-2: content\r\n\r\n" +
                                            "Body of the Request");
    BufferedReader reader = new BufferedReader(request);
    assertThat(parser.read(reader), is("Body of the Request\n"));
  }

  @Test
  public void parsesBodyWithMultipleLines() {
    BodyParser parser = new BodyParser();
    StringReader request = new StringReader("GET / HTTP/1.1\r\n" +
                                            "Header-1: content\r\n" +
                                            "Header-2: content\r\n\r\n" +
                                            "First Line\n" +
                                            "Second Line\n" +
                                            "Third Line");
    BufferedReader reader = new BufferedReader(request);
    assertThat(parser.read(reader), is("First Line\nSecond Line\nThird Line\n"));
  }
}
