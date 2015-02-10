package http.Parsers;

import http.Exceptions.InvalidRequestMethodException;
import http.Request;
import http.Sockets.ClientSocket;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Map;

import static java.util.Arrays.asList;

public class RequestParser {
  private final RequestLineParser reqLineParser;
  private final HeadersParser reqHeadersParser;
  private final BodyParser reqBodyParser;
  private BufferedReader reader;

  public RequestParser(ClientSocket client) {
    this.reader = new Reader(client).invoke();
    this.reqLineParser = new RequestLineParser();
    this.reqHeadersParser = new HeadersParser();
    this.reqBodyParser = new BodyParser();
  }

  public Request buildRequest() {
    String requestLine = getRequestLine();
    String method = getMethod(requestLine);
    String uri = getURI(requestLine);
    Map<String, String> headers = requestHeaders();
    String body = requestBody(contentLength(headers));
    return new Request(method, uri, headers, body);
  }

  public String getUriAndMethod() {
    String requestLine = getRequestLine();
    String method = getMethod(requestLine);
    validateMethod(method);
    String uri = getURI(requestLine);
    return method + " " + uri;
  }

  private void validateMethod(String method) {
    ArrayList validMethods = new ArrayList<>(asList("GET", "POST", "PUT",
                                                    "DELETE", "PATCH", "OPTIONS",
                                                    "HEAD", "CONNECT", "TRACE"));
    if (!validMethods.contains(method)) {
      throw new InvalidRequestMethodException();
    }
  }

  private int contentLength(Map<String, String> headers) {
    int size;
    try {
      size = Integer.parseInt(headers.get("Content-Length"));
    } catch (NumberFormatException e) {
      size = 0;
    }
    return size;
  }

  private String getRequestLine() {
    return reqLineParser.read(reader);
  }

  private Map<String, String> requestHeaders() {
    return reqHeadersParser.read(reader);
  }

  private String requestBody(int size) {
    return reqBodyParser.read(reader, size);
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
}
