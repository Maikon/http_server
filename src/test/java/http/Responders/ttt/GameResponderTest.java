package http.responders.ttt;

import http.Request;
import http.responders.ServerResponse;
import org.junit.Before;
import org.junit.Test;
import ttt.Display;
import ttt.Game;

import static http.responders.StatusCodes.OK;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;

public class GameResponderTest {
    private GameResponder responder;
    private Request request;

    @Before
    public void setUp() {
        Display display = new WebDisplay();
        Game game = new Game(display);
        responder = new GameResponder(game);
        request = buildRequest("GET", "/game");
    }

    @Test
    public void respondsToInitialRequestSuccessfully() {
        ServerResponse response = responder.response(request);
        assertThat(response.getStatus(), is(OK));
    }

    @Test
    public void responseContainsGameOptions() {
        ServerResponse response = responder.response(request);
        assertThat(response.getBody(), allOf(containsString("Human Vs Human"),
                                             containsString("Human Vs Computer"),
                                             containsString("Computer Vs Human"),
                                             containsString("Computer Vs Computer")));
    }

    @Test
    public void responseHeadersIncludeContentType() {
        ServerResponse response = responder.response(request);
        assertThat(response.getHeader("Content-Type"), is("text/html"));
    }

    private Request buildRequest(String method, String uri) {
        return Request.withMethod(method)
          .addURI(uri)
          .build();
    }
}
