package http.responders;

public class NotFoundResponder implements Response {
  public String response() {
    return "HTTP/1.1 404 Not Found\r\n";
  }
}
