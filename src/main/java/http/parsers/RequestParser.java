package http.Parsers;

import http.Exceptions.InvalidRequestMethodException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {
  public String getRequestLine(String request) {
    String[] lines = separateRequestLines(request);
    return lines[0];
  }

  public String requestMethod(String requestLine) {
    String method = getLineContents(requestLine)[0];
    if (invalidMethod(method)) {
      throw new InvalidRequestMethodException();
    }
    return method;
  }

  public String requestURI(String requestLine) {
    return getLineContents(requestLine)[1];
  }

  public Map<String, String> requestHeaders(String request) {
    Map<String, String> headers = new HashMap<>();
    addHeaderNameWithValue(headers, separateRequestLines(request));
    return headers;
  }

  public String requestBody(String request) {
    String[] lines = separateRequestLines(request);
    return lines[lastLine(lines)];
  }

  private void addHeaderNameWithValue(Map<String, String> headers, String[] lines) {
    for (int i = 1; i < lastLine(lines); i++) {
      String[] header = lines[i].split(":\\s");
      String headerName = header[0];
      String headerValue = header[1];
      headers.put(headerName, headerValue);
    }
  }

  private String[] separateRequestLines(String request) {
    return request.split("\r\n");
  }

  private String[] getLineContents(String requestLine) {
    return requestLine.split("\\s");
  }

  private boolean invalidMethod(String method) {
    String[] validMethods = {"GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS", "HEAD", "CONNECT", "TRACE"};
    return !Arrays.asList(validMethods).contains(method);
  }

  private int lastLine(String[] lines) {
    return lines.length - 1;
  }
}
