package http.parsers;

import http.Request;
import http.exceptions.InvalidRequestMethodException;
import http.sockets.ClientSocket;
import http.utils.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Map;

import static java.util.Arrays.asList;

public class RequestParser {
  private final Logger logger = new Logger(org.apache.log4j.Logger.getLogger(RequestParser.class));
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
    Map<String, String> headers = requestHeaders();
    String body = requestBody(contentLength(headers));
    Request.Builder builder = Request.withMethod(getMethod(requestLine))
                                     .addURI(getURI(requestLine))
                                     .addParams(decodeURIParams(requestLine))
                                     .addBody(body);
    Request.Builder request = addHeaders(headers, builder);
    return request.build();
  }

  public String getUriAndMethod() {
    String requestLine = getRequestLine();
    String method = getMethod(requestLine);
    validateMethod(method);
    String uri = getURI(requestLine);
    return method + " " + uri;
  }

  private String decodeURIParams(String requestLine) {
    String[] allParams = findParams(requestLine).split("&");
    return buildParams(allParams);
  }

  private String findParams(String line) {
    String params;
    String wholeURI = getLineContents(line)[1];
    String[] uri = wholeURI.split("\\?");
    if (uri.length > 1) {
      params = uri[1];
    } else {
      params = "";
    }
    return params;
  }

  private String buildParams(String[] allParams) {
    String decodedParams = "";
    try {
      for (String param : allParams) {
        if (param.contains("=")) {
          decodedParams += formatParam(param);
        } else {
          decodedParams += decode(param) + "\n";
        }
      }
    } catch (UnsupportedEncodingException e) {
      logger.logError(e);
    }
    return decodedParams;
  }

  private String formatParam(String param) throws UnsupportedEncodingException {
    String[] parts = param.split("=");
    return parts[0] + " = " + decode(parts[1]) + "\n";
  }

  private String decode(String param) throws UnsupportedEncodingException {
    return URLDecoder.decode(param, "UTF-8");
  }

  private Request.Builder addHeaders(Map<String, String> headers, Request.Builder builder) {
    for (Map.Entry<String, String> header : headers.entrySet()) {
      builder.addHeader(header.getKey(), header.getValue());
    }
    return builder;
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
