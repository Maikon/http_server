package http.responders;

public class SuccessResponder implements Responder {
  public String response() {
    ServerResponse response = ServerResponse.status(StatusCodes.OK).build();
    return response.statusLine();
  }
}
