package http.responders;

import java.util.HashMap;
import java.util.Map;

public class ServerResponse {
  public final String CRLF = "\r\n";
  private String body;
  private Map<String, String> headers;
  private int statusCode;
  private Map<Integer, StatusLine> responses = new HashMap<>();

  private ServerResponse(Builder builder) {
    this.statusCode = builder.statusCode;
    this.headers = builder.headers;
    this.body = builder.body;
  }

  public static Builder status(int code) {
    return new Builder(code);
  }

  public String statusLine() {
    Map<Integer, StatusLine> responses = statusLinesList();
    return responses.get(statusCode).toString();
  }

  public String stringifyHeaders() {
    String result = "";
    for (Map.Entry<String, String> header : headers.entrySet()) {
      result += header.getKey() + ": " + header.getValue() + CRLF;
    }
    return result;
  }

  @Override
  public String toString() {
    return statusLine() + stringifyHeaders() + CRLF + getBody();
  }

  public String getBody() {
    return body;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  private Map<Integer, StatusLine> statusLinesList() {
    responses.put(200, new StatusLine(200, "OK"));
    return responses;
  }

  protected static class Builder {
    private int statusCode = 0;
    private Map<String, String> headers = new HashMap<>();
    public String body;

    private Builder(int statusCode) {
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
