package http.responders;

public class FileContentsResponder implements Response {
  public String response() {
    return "HTTP/1.1 200 OK\r\n" +
           "Content-Type: text/html\r\n\r\n\r\n" +
           "<html><head></head><body>" +
           "<p>file1 contents</p>" +
           "</body></html>";
  }
}
