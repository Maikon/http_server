package http.responders;

public class SuccessResponder implements Response {
  public String response() {
    ServerResponse response = ServerResponse.status(StatusCodes.OK).build();
    return response.statusLine();
  }
}
