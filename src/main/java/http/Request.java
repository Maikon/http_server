package http;

import java.util.Map;

public class Request {
  private final String method;
  private final String uri;
  private final Map<String, String> headers;
  private final String body;

  public Request(String method, String uri, Map headers, String body) {
    this.method = method;
    this.uri = uri;
    this.headers = headers;
    this.body = body;
  }

  public String getMethod() {
    return method;
  }

  public String getUri() {
    return uri;
  }

  public Map getHeaders() {
    return headers;
  }

  public String getBody() {
    return body;
  }
}
