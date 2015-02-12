package http.responders;

public class SuccessResponder implements Response {
  public String response() {
    return "HTTP/1.1 200 OK\r\n";
  }
}
