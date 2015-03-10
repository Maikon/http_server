package http.responders.ttt;

import http.Request;
import http.filesystem.RetrieveTemplate;
import http.responders.Responder;
import http.responders.ServerResponse;

import static http.responders.StatusCodes.OK;

public class GameResponder implements Responder {
    private RetrieveTemplate template;

    public GameResponder(RetrieveTemplate template) {
        this.template = template;
    }

    @Override
    public ServerResponse response(Request request) {
        return ServerResponse.status(OK)
                             .addHeader("Content-Type", "text/html")
                             .addBody(template.inBytes())
                             .build();
    }
}
