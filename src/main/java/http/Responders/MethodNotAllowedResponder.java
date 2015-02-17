package http.responders;

public class MethodNotAllowedResponder implements Response {
  public String response() {
    ServerResponse response = ServerResponse.status(StatusCodes.METHOD_NOT_ALLOWED)
                                            .build();
    return response.statusLine();
  }
}
