package http.Parsers;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HeadersParserTest {

  @Test
  public void parsesHeaders() {
    HeadersParser parser = new HeadersParser();
    StringReader input = new StringReader("GET / HTTP/1.1\r\n" +
                                          "Host: localhost\r\n\r\n" +
                                          "Body of the Request");
    BufferedReader reader = new BufferedReader(input);
    Map<String, String> headers = new HashMap<>();
    headers.put("Host", "localhost");
    assertThat(parser.read(reader), is(headers));
  }

  @Test
  public void parsesHeadersEvenIfBodyContainsColons() {
    HeadersParser parser = new HeadersParser();
    StringReader input = new StringReader("GET / HTTP/1.1\r\n" +
                                          "Accept-Language: en-us\r\n" +
                                          "Host: localhost\r\n\r\n" +
                                          "Body of Request contains : ");
    BufferedReader reader = new BufferedReader(input);
    Map<String, String> headers = new HashMap<>();
    headers.put("Host", "localhost");
    headers.put("Accept-Language", "en-us");
    assertThat(parser.read(reader), is(headers));
  }

  @Test
  public void parsesHeadersThatContainMultipleColons() {
    HeadersParser parser = new HeadersParser();
    StringReader input = new StringReader("GET / HTTP/1.1\r\n" +
                                          "Accept-Language: en-us\r\n" +
                                          "Host: localhost\r\n" +
                                          "Date: Wed, 04 Feb 2015 08:12:31 GMT\r\n\r\n" +
                                          "Body of Request");
    BufferedReader reader = new BufferedReader(input);
    Map<String, String> headers = new HashMap<>();
    headers.put("Host", "localhost");
    headers.put("Accept-Language", "en-us");
    headers.put("Date", "Wed, 04 Feb 2015 08:12:31 GMT");
    assertThat(parser.read(reader), is(headers));
  }
}
