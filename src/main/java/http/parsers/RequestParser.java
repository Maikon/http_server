package http.Parsers;

import http.Exceptions.InvalidRequestMethodException;
import http.Sockets.ClientSocket;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.Map;

public class RequestParser {
  private final RequestLineParser reqLineParser;
  private final HeadersParser reqHeadersParser;
  private final BodyParser reqBodyParser;
  private BufferedReader input;

  public RequestParser(ClientSocket client) {
    this.input = new Reader(client).getInput();
    this.reqLineParser = new RequestLineParser();
    this.reqHeadersParser = new HeadersParser();
    this.reqBodyParser = new BodyParser();
  }

  public String getRequestLine() {
    return reqLineParser.read(input);
  }

  public String requestMethod() {
    String requestLine = reqLineParser.read(input);
    String method = getMethod(requestLine);
    if (invalidMethod(method)) {
      throw new InvalidRequestMethodException();
    }
    return method;
  }

  public String requestURI() {
    String requestLine = reqLineParser.read(input);
    return getURI(requestLine);
  }

  public String getUriAndMethod() {
    String requestLine = reqLineParser.read(input);
    String method = getMethod(requestLine);
    String uri = getURI(requestLine);
    return method + " " + uri;
  }

  public Map<String, String> requestHeaders() {
    return reqHeadersParser.read(input);
  }

  public String requestBody() {
    return reqBodyParser.read(input);
  }

  private String getMethod(String requestLine) {
    return getLineContents(requestLine)[0];
  }

  private String getURI(String requestLine) {
    return getLineContents(requestLine)[1];
  }

  private String[] getLineContents(String requestLine) {
    return requestLine.split("\\s");
  }

  private boolean invalidMethod(String method) {
    String[] validMethods = {"GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS", "HEAD", "CONNECT", "TRACE"};
    return !Arrays.asList(validMethods).contains(method);
  }
}
