package http.Responders;

public class RootResponder implements Response {
  public String response() {
    return "HTTP/1.1 200 OK\r\n" +
           "Content-Type: text/html\r\n\r\n\r\n" +
           "<html><head></head><body>" +
           "<a href='file1'>file1</a>"+
           "<a href='file2'>file2</a>"+
           "<a href='image.gif'>image.gif</a>"+
           "<a href='image.jpeg'>image.jpeg</a>"+
           "<a href='image.png'>image.png</a>"+
           "<a href='text-file.txt'>text-file.txt</a>" +
           "<p>partial_content.txt</p>" +
           "<p>patch-content.txt</p>" +
           "</body></html>";
  }
}
