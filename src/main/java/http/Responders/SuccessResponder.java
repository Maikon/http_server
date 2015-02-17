package http.responders;

public class SuccessResponder implements Responder {
  public ServerResponse response() {
    ServerResponse response = ServerResponse.status(StatusCodes.OK).build();
    return response;
  }
}
