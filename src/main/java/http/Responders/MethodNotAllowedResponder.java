package http.responders;

public class MethodNotAllowedResponder implements Responder {
  public ServerResponse response() {
    ServerResponse response = ServerResponse.status(StatusCodes.METHOD_NOT_ALLOWED)
                                            .build();
    return response;
  }
}
