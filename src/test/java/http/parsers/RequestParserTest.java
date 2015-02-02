package http.Parsers;

import http.Exceptions.InvalidRequestMethodException;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RequestParserTest {

  private final String REQUEST_FIRST_LINE = "OPTIONS /some-uri HTTP/1.1\r\n";
  private final String FULL_REQUEST = "GET / HTTP/1.1\r\n" +
                                      "Accept-Language: en-us\r\n" +
                                      "Host: localhost\r\n" +
                                      "Body of the Request";
  private RequestParser parser;

  @Before
  public void setup() {
    parser = new RequestParser();
  }

  @Test
  public void returnsTheRequestLine() {
    String firstLine = "GET / HTTP/1.1";
    String request = FULL_REQUEST;
    assertThat(parser.getRequestLine(request), is(firstLine));
  }

  @Test
  public void returnsTheMethod() {
    String firstLine = REQUEST_FIRST_LINE;
   assertThat(parser.requestMethod(firstLine), is("OPTIONS"));
  }

  @Test
  public void returnsTheURI() {
    String firstLine = REQUEST_FIRST_LINE;
    assertThat(parser.requestURI(firstLine), is("/some-uri"));
  }

  @Test
  public void returnsTheHeaders() {
    String request = FULL_REQUEST;
    Map<String, String> headers = new HashMap<>();
    headers.put("Accept-Language", "en-us");
    headers.put("Host", "localhost");
    assertThat(parser.requestHeaders(request), equalTo(headers));
  }

  @Test
  public void returnsTheBodyOfTheRequest() {
    assertThat(parser.requestBody(FULL_REQUEST), is("Body of the Request"));
  }

  @Test(expected = InvalidRequestMethodException.class)
  public void rejectsUnknownRequest() {
    String request = "INVALID-REQUEST / HTTP/1.1\r\n";
    parser.requestMethod(request);
  }
}