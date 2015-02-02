package http.Responders;

public class MethodOptionsResponder implements Response {
  public String response() {
    return "HTTP/1.1 200 OK\r\nAllow: GET,HEAD,POST,OPTIONS,PUT\r\n";
  }
}
