package http.responders.ttt;

import http.Request;
import http.filesystem.RetrieveTemplate;
import http.responders.ServerResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static http.responders.StatusCodes.OK;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GameResponderTest {
    private GameResponder responder;
    private Request request;

    @Before
    public void setUp() {
        Map<Integer, String> templates = new HashMap<>();
        templates.put(1, "/index.html");
        templates.put(3, "/three_by_three.html");
        responder = new GameResponder(templates);
        request = buildRequest("GET", "/game/menu", "", "");
    }

    @Test
    public void respondsToInitialRequestSuccessfully() {
        ServerResponse response = responder.response(request);
        assertThat(response.getStatus(), is(OK));
    }

    @Test
    public void initialResponseContainsMenuTemplate() {
        RetrieveTemplate template = new RetrieveTemplate("/index.html");
        ServerResponse response = responder.response(request);
        assertThat(response.getBody().getBytes(), is(template.inBytes()));
    }

    @Test
    public void responseHeadersIncludeContentType() {
        ServerResponse response = responder.response(request);
        assertThat(response.getHeader("Content-Type"), is("text/html"));
    }


    private Request buildRequest(String method, String uri, String params, String body) {
        return Request.withMethod(method)
                      .addURI(uri)
                      .addParams(params)
                      .addBody(body)
                      .build();
    }
}
