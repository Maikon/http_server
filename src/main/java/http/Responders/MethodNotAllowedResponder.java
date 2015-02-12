package http.responders;

public class MethodNotAllowedResponder implements Response {
  public String response() {
    return "HTTP 405 Method Not Allowed\r\n";
  }
}
