package http.responders;

public class RedirectResponder implements Response {
  public String response() {
    return "HTTP/1.1 301 Moved Permanently\r\nLocation: http://localhost:5000/\r\n";
  }
}
