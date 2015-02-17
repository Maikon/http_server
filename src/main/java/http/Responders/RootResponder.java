package http.responders;

public class RootResponder implements Responder {
  public String response() {
    ServerResponse response = ServerResponse.status(StatusCodes.OK)
                                            .addHeader("Content-Type", "text/html")
                                            .addBody("<html><head></head><body>" +
                                                     "<a href='file1'>file1</a>"+
                                                     "<a href='file2'>file2</a>"+
                                                     "<a href='image.gif'>image.gif</a>"+
                                                     "<a href='image.jpeg'>image.jpeg</a>"+
                                                     "<a href='image.png'>image.png</a>"+
                                                     "<a href='text-file.txt'>text-file.txt</a>" +
                                                     "<p>partial_content.txt</p>" +
                                                     "<p>patch-content.txt</p>" +
                                                     "</body></html>")
                                            .build();
    return response.toString();
  }
}
