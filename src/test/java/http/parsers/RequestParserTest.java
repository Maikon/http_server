package http.Parsers;

import http.Exceptions.InvalidRequestMethodException;
import http.Fakes.FakeClientSocket;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RequestParserTest {
  private final String FULL_REQUEST = "GET / HTTP/1.1\r\n" +
                                      "Accept-Language: en-us\r\n" +
                                      "Host: localhost\r\n\r\n" +
                                      "Body of the Request";
  private RequestParser parser;

  @Before
  public void setup() {
    parser = new RequestParser(new FakeClientSocket(FULL_REQUEST));
  }

  @Test
  public void returnsTheRequestLine() {
    String firstLine = "GET / HTTP/1.1";
    assertThat(parser.getRequestLine(), is(firstLine));
  }

  @Test
  public void returnsTheMethod() {
   assertThat(parser.requestMethod(), is("GET"));
  }

  @Test(expected = InvalidRequestMethodException.class)
  public void rejectsUnknownRequest() {
    parser = new RequestParser(new FakeClientSocket("INVALID-REQUEST / HTTP/1.1\r\n"));
    parser.requestMethod();
  }

  @Test
  public void returnsTheURI() {
    assertThat(parser.requestURI(), is("/"));
  }

  @Test
  public void returnsBothUriAndMethod() {
    assertThat(parser.getUriAndMethod(), is("GET /"));
  }

  @Test
  public void returnsTheHeaders() {
    Map<String, String> headers = new HashMap<>();
    headers.put("Accept-Language", "en-us");
    headers.put("Host", "localhost");
    assertThat(parser.requestHeaders(), equalTo(headers));
  }

  @Test
  public void returnsTheBodyOfTheRequest() {
    assertThat(parser.requestBody(), is("Body of the Request\n"));
  }
}

