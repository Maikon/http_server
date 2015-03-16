package http.responders.ttt;

import http.Request;
import http.filesystem.RetrieveTemplate;
import http.responders.Responder;
import http.responders.ServerResponse;
import java.util.HashMap;
import java.util.Map;

import static http.responders.StatusCodes.OK;

public class GameResponder implements Responder {
    private final int MENU_TEMPLATE = 1;
    private final Map<Integer, String> templates;
    private RetrieveTemplate template;

    public GameResponder(Map<Integer, String> templates) {
        this.templates = templates;
    }

    @Override
    public ServerResponse response(Request request) {
        Responder responder = getResponders().get(request.getUri());
        return responder.response(request);
    }

    private Map<String, Responder> getResponders() {
        Map<String, Responder> responders = new HashMap<>();
        responders.put("/game/menu", responderForGameMenu());
        return responders;
    }
                             .addHeader("Content-Type", "text/html")
                             .addBody(template.inBytes())
                             .build();
    }

    private Responder responderForGameMenu() {
        return request -> {
            template = new RetrieveTemplate(templates.get(MENU_TEMPLATE));
            return ServerResponse.status(OK)
                                 .addHeader("Content-Type", "text/html")
                                 .addBody(template.inBytes())
                                 .build();
        };
    }

}
