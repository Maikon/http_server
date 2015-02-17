package http.responders;

public class NotFoundResponder implements Responder {
  public ServerResponse response() {
    ServerResponse response = ServerResponse.status(StatusCodes.NOT_FOUND).build();
    return response;
  }
}
