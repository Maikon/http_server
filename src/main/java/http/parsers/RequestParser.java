package http.Parsers;

import http.Exceptions.InvalidRequestMethodException;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.Map;

public class RequestParser {
  private final RequestLineParser reqLineParser;
  private final HeadersParser reqHeadersParser;
  private final BodyParser reqBodyParser;

  public RequestParser() {
    this.reqLineParser = new RequestLineParser();
    this.reqHeadersParser = new HeadersParser();
    this.reqBodyParser = new BodyParser();
  }

  public String getRequestLine(BufferedReader reader) {
    return reqLineParser.read(reader);
  }

  public String requestMethod(BufferedReader reader) {
    String requestLine = reqLineParser.read(reader);
    String method = getLineContents(requestLine)[0];
    if (invalidMethod(method)) {
      throw new InvalidRequestMethodException();
    }
    return method;
  }

  public String requestURI(BufferedReader reader) {
    String requestLine = reqLineParser.read(reader);
    return getLineContents(requestLine)[1];
  }

  public Map<String, String> requestHeaders(BufferedReader reader) {
    return reqHeadersParser.read(reader);
  }

  public String requestBody(BufferedReader reader) {
    return reqBodyParser.read(reader);
  }

  public String getUriAndMethod(BufferedReader reader) {
    String requestLine = reqLineParser.read(reader);
    String method = getLineContents(requestLine)[0];
    String uri = getLineContents(requestLine)[1];
    return method + " " + uri;
  }

  private String[] getLineContents(String requestLine) {
    return requestLine.split("\\s");
  }

  private boolean invalidMethod(String method) {
    String[] validMethods = {"GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS", "HEAD", "CONNECT", "TRACE"};
    return !Arrays.asList(validMethods).contains(method);
  }
}
