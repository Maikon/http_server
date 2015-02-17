package http;

import java.util.HashMap;
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

  public Request(Builder builder) {
    this.method = builder.method;
    this.uri = builder.uri;
    this.headers = builder.headers;
    this.body = builder.body;
  }

  public String getMethod() {
    return method;
  }

  public String getUri() {
    if (uri.length() == 1) {
      return uri;
    }
    return uri.substring(1);
  }

  public Map getHeaders() {
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

  public static class Builder {

   private final String method;
   private Map<String, String> headers = new HashMap<>();
   private String body;
   private String uri;

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
 }
}
