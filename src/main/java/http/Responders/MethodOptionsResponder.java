package http.responders;

public class MethodOptionsResponder implements Responder {
  public String response() {
    ServerResponse response = ServerResponse.status(StatusCodes.OK)
                                            .addHeader("Allow", "GET,HEAD,POST,OPTIONS,PUT")
                                            .addBody("")
                                            .build();
    return response.toString();
  }
}
