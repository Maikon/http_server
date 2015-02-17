package http.responders;

import java.util.HashMap;
import java.util.Map;

public class ServerResponse {
  public final String CRLF = "\r\n";
  private final StatusCodes status;
  private String body;
  private Map<String, String> headers;

  private ServerResponse(Builder builder) {
    this.status = builder.status;
    this.headers = builder.headers;
    this.body = builder.body;
  }

  public static Builder status(StatusCodes code) {
    return new Builder(code);
  }

  public String statusLine() {
    return status.getLine();
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

  public byte[] toBytes() {
    return toString().getBytes();
  }

  public String getBody() {
    return body;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public int getContentLength(String body) {
    return body.length();
  }

  public StatusCodes getStatus() {
    return status;
  }

  public String getHeader(String key) {
    return headers.get(key);
  }

  public static class Builder {
    private StatusCodes status;
    private Map<String, String> headers = new HashMap<>();
    public String body = "";

    public Builder(StatusCodes code) {
      this.status = code;
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
