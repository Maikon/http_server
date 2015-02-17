package http.responders;

public class NotFoundResponder implements Response {
  public String response() {
    ServerResponse response = ServerResponse.status(StatusCodes.NOT_FOUND).build();
    return response.statusLine();
  }
}
