package http.responders;

import http.Request;

public class ParamsResponder implements Responder {

    @Override
    public ServerResponse response(Request request) {
        return ServerResponse.status(StatusCodes.OK)
          .addBody(request.getParams())
          .build();
    }
}
