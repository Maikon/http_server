package http.responders.ttt;

import http.Request;
import http.filesystem.RetrieveTemplate;
import http.responders.ServerResponse;
import org.junit.Before;
import org.junit.Test;

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
        responder = new GameResponder(new RetrieveTemplate("/index.html"));
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
        assertThat(response.getBody(), allOf(containsString("Human vs Human"),
                                             containsString("Human vs Computer"),
                                             containsString("Computer vs Human"),
                                             containsString("Computer vs Computer")));
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
