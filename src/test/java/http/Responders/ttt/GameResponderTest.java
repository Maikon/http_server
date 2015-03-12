package http.responders.ttt;

import http.Request;
import http.filesystem.RetrieveTemplate;
import http.responders.ServerResponse;
import org.junit.Before;
import org.junit.Test;

import static http.responders.StatusCodes.OK;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GameResponderTest {
    private GameResponder responder;
    private Request request;
    private RetrieveTemplate template;

    @Before
    public void setUp() {
        template = new RetrieveTemplate("/index.html");
        responder = new GameResponder(template);
        request = buildRequest("GET", "/game");
    }

    @Test
    public void respondsToInitialRequestSuccessfully() {
        ServerResponse response = responder.response(request);
        assertThat(response.getStatus(), is(OK));
    }

    @Test
    public void responseContainsTemplateContents() {
        ServerResponse response = responder.response(request);
        assertThat(response.getBody().getBytes(), is(template.inBytes()));
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
