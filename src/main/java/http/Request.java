package http;

import java.util.HashMap;
import java.util.Map;

public class Request {
  private static final String PROTOCOL = "HTTP/1.1";
  private final String method;
  private final String uri;
  private final Map<String, String> headers;
  private final String body;
  private String params;

  public Request(Builder builder) {
    this.method = builder.method;
    this.uri = builder.uri;
    this.headers = builder.headers;
    this.body = builder.body;
    this.params = builder.params;
  }

  public String getMethod() {
    return method;
  }

  public String getUri() {
    return uri;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public String getBody() {
    return body;
  }

  public static Builder withMethod(String method) {
    return new Builder(method);
  }

  public String methodWithUri() {
    return getMethod() + " " + getUri();
  }

  public String getParams() {
    return params;
  }

  public String getStatusLine() {
    return methodWithUri() + " " + PROTOCOL;
  }

  public static class Builder {

   private final String method;
   private Map<String, String> headers = new HashMap<>();
   private String body;
   private String uri;
    public String params;

    public Builder(String method) {
     this.method = method;
   }

   public Request build() {
     return new Request(this);
   }

   public Builder addHeader(String header, String value) {
     headers.put(header, value);
     return this;
   }

   public Builder addBody(String body) {
     this.body = body;
     return this;
   }

   public Builder addURI(String uri) {
     this.uri = uri;
     return this;
   }

    public Builder addParams(String params) {
      this.params = params;
      return this;
    }
  }
}
