package http.responders.ttt;

import http.Request;
import http.responders.Responder;
import http.responders.ServerResponse;
import ttt.Game;
import ttt.PlayerFactory;

import java.util.Map;

import static http.responders.StatusCodes.OK;

public class GameResponder implements Responder {

    public GameResponder(Game game) {
    }

    @Override
    public ServerResponse response(Request request) {
        return ServerResponse.status(OK)
                             .addHeader("Content-Type", "text/html")
                             .addBody(bodyWithGameOptions())
                             .build();
    }

    private String bodyWithGameOptions() {
        String body = "";
        Map<String, String> gameChoices = PlayerFactory.allGameChoices();
        for (Map.Entry<String, String> choice : gameChoices.entrySet()) {
            body += choice.getValue() + "\n";
        }
        return body;
    }
}
