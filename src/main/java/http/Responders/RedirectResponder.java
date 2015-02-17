package http.responders;

public class RedirectResponder implements Response {
  public String response() {
    ServerResponse response = ServerResponse.status(StatusCodes.REDIRECT)
                                            .addHeader("Location", "http://localhost:5000/")
                                            .addBody("")
                                            .build();
    return response.toString();
  }
}
