package http.responders;

import static http.responders.StatusCodes.*;

public class RedirectResponder implements Responder {
  public ServerResponse response() {
    ServerResponse response = ServerResponse.status(REDIRECT)
                                            .addHeader("Location", "http://localhost:5000/")
                                            .addBody("")
                                            .build();
    return response;
  }
}
