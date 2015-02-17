package http.responders;

public class FileContentsResponder implements Responder {
  public ServerResponse response() {
    ServerResponse response = ServerResponse.status(StatusCodes.OK)
                                            .addHeader("Content-Type", "text/html")
                                            .addBody("<html><head></head><body>" +
                                                     "<p>file1 contents</p>" +
                                                     "</body></html>")
                                            .build();
    return response;
  }
}
