package http.Responders;

import java.util.HashMap;
import java.util.Map;

public class ServerResponse {
  private String body;
  private Map<String, String> headers;
  private int statusCode;

  private ServerResponse(Builder builder) {
    this.statusCode = builder.statusCode;
    this.headers = builder.headers;
    this.body = builder.body;
  }

  public String statusLine() {
    Map<Integer, String> responses = new HashMap<>();
    responses.put(200, "HTTP/1.1 200 OK\r\n");
    return responses.get(statusCode);
  }

  public String getBody() {
    return body;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  protected static class Builder {
    private int statusCode = 0;
    private Map<String, String> headers = new HashMap<>();
    public String body;

    public Builder(int statusCode) {
      this.statusCode = statusCode;
    }

    public ServerResponse build() {
      return new ServerResponse(this);
    }

    public Builder addHeader(String field, String value) {
      headers.put(field, value);
      return this;
    }

    public Builder addBody(String body) {
      this.body = body;
      return this;
    }
  }
}
