package http.parsers;

import http.exceptions.InvalidRequestMethodException;
import http.fakes.FakeClientSocket;
import http.Request;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RequestParserTest {
  private RequestParser parser;

  @Before
  public void setup() {
    String request = "GET / HTTP/1.1\r\n" +
                     "Content-Length: 25\r\n" +
                     "\r\n" +
                     "First Line\n" +
                     "Second Line\r\n";
    parser = new RequestParser(new FakeClientSocket(request));
  }

  @Test
  public void returnsBothUriAndMethod() {
    assertThat(parser.getUriAndMethod(), is("GET /"));
  }

  @Test(expected = InvalidRequestMethodException.class)
  public void rejectsUnknownRequest() {
    parser = new RequestParser(new FakeClientSocket("INVALID-REQUEST / HTTP/1.1\r\n"));
    parser.getUriAndMethod();
  }

  @Test
  public void returnsRequestInParts() {
    Request req = parser.buildRequest();
    assertThat(req.getMethod(), is("GET"));
    assertThat(req.getUri(), is("/"));
    assertThat(req.getHeaders().get("Content-Length"), is("25"));
    assertThat(req.getBody(), is("First Line\nSecond Line"));
  }

  @Test
  public void ignoresTheBodyIfNoContentLengthHeaderIsPresent() {
    String request = "GET / HTTP/1.1\r\n" +
                     "\r\n" +
                     "First Line\n" +
                     "Second Line\r\n";
    parser = new RequestParser(new FakeClientSocket(request));
    Request req = parser.buildRequest();
    assertThat(req.getBody(), is(""));
  }

  @Test
  public void stripsParametersWhenReturningURI() {
    String request = "GET /some-url?%3Cexample-decoding%3E HTTP/1.1\r\n";
    parser = new RequestParser(new FakeClientSocket(request));
    Request req = parser.buildRequest();
    assertThat(req.getUri(), is("/some-url"));
  }

  @Test
  public void decodesSingleURLParameters() {
    String request = "GET /some-url?%3Cexample-decoding%3E HTTP/1.1\r\n";
    parser = new RequestParser(new FakeClientSocket(request));
    Request req = parser.buildRequest();
    assertThat(req.getParams(), is("<example-decoding>\n"));
  }

  @Test
  public void decodesMultipleURLParameters() {
    String request = "GET /some-url?%3Cone%3E&%3Ctwo%3E HTTP/1.1\r\n";
    parser = new RequestParser(new FakeClientSocket(request));
    Request req = parser.buildRequest();
    assertThat(req.getParams(), is("<one>\n<two>\n"));
  }

  @Test
  public void formatsURLParamsCorrectly() {
    String request = "GET /parameters?param1=has%3D&param2=two HTTP/1.1\r\n";
    parser = new RequestParser(new FakeClientSocket(request));
    Request req = parser.buildRequest();
    assertThat(req.getParams(), is("param1 = has=\nparam2 = two\n"));
  }
}

