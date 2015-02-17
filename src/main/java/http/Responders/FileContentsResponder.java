package http.responders;

public class FileContentsResponder implements Response {
  public String response() {
    ServerResponse response = ServerResponse.status(StatusCodes.OK)
                                            .addHeader("Content-Type", "text/html")
                                            .addBody("<html><head></head><body>" +
                                                     "<p>file1 contents</p>" +
                                                     "</body></html>")
                                            .build();
    return response.toString();
  }
}
