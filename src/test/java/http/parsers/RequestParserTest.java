package http.Parsers;

import http.Exceptions.InvalidRequestMethodException;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.StringReader;
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
    parser = new RequestParser();
  }

  @Test
  public void returnsTheRequestLine() {
    BufferedReader reader = new BufferedReader(new StringReader(FULL_REQUEST));
    String firstLine = "GET / HTTP/1.1";
    assertThat(parser.getRequestLine(reader), is(firstLine));
  }

  @Test
  public void returnsTheMethod() {
   BufferedReader reader = new BufferedReader(new StringReader(FULL_REQUEST));
   assertThat(parser.requestMethod(reader), is("GET"));
  }

  @Test(expected = InvalidRequestMethodException.class)
  public void rejectsUnknownRequest() {
    BufferedReader reader = new BufferedReader(new StringReader("INVALID-REQUEST / HTTP/1.1\r\n"));
    parser.requestMethod(reader);
  }

  @Test
  public void returnsTheURI() {
    BufferedReader reader = new BufferedReader(new StringReader(FULL_REQUEST));
    assertThat(parser.requestURI(reader), is("/"));
  }

  @Test
  public void returnsBothUriAndMethod() {
    BufferedReader reader = new BufferedReader(new StringReader(FULL_REQUEST));
    assertThat(parser.getUriAndMethod(reader), is("GET /"));
  }

  @Test
  public void returnsTheHeaders() {
    BufferedReader reader = new BufferedReader(new StringReader(FULL_REQUEST));
    Map<String, String> headers = new HashMap<>();
    headers.put("Accept-Language", "en-us");
    headers.put("Host", "localhost");
    assertThat(parser.requestHeaders(reader), equalTo(headers));
  }

  @Test
  public void returnsTheBodyOfTheRequest() {
    BufferedReader reader = new BufferedReader(new StringReader(FULL_REQUEST));
    assertThat(parser.requestBody(reader), is("Body of the Request\n"));
  }
}

