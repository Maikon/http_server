package http.Responders;

public class RootResponder implements Response {
  public String response() {
    return "HTTP/1.1 200 OK\r\n";
  }
}
