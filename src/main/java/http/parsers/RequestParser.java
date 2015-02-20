package http.parsers;

import http.exceptions.InvalidRequestMethodException;
import http.Request;
import http.sockets.ClientSocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Map;

import static java.util.Arrays.asList;

public class RequestParser {
  private final RequestLineParser reqLineParser;
  private final HeadersParser reqHeadersParser;
  private final BodyParser reqBodyParser;
  private BufferedReader reader;

  public RequestParser(ClientSocket client) {
    this.reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
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

  public String decodeURIParams() {
    String decoded = "";
    String wholeURI = getLineContents(getRequestLine())[1];
    String params = wholeURI.split("\\?")[1];
    try {
      decoded = URLDecoder.decode(params, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return decoded;
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
    String wholeURI = getLineContents(requestLine)[1];
    return wholeURI.split("\\?")[0];
  }

  private String[] getLineContents(String requestLine) {
    return requestLine.split("\\s");
  }
}
