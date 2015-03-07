package http.responders;

import http.Request;
import http.fakes.FakeClientSocket;
import http.parsers.RequestParser;
import org.junit.Test;

import static http.responders.StatusCodes.OK;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ParamsResponderTest {

    @Test
    public void responseBodyContainsDecodedParams() {
        String statusLine = "GET /params?%3Cexample-decoding%3E HTTP/1.1\r\n";
        RequestParser parser = new RequestParser(new FakeClientSocket(statusLine));
        Request request = parser.buildRequest();
        Responder responder = new ParamsResponder();
        ServerResponse response = responder.response(request);

        assertThat(response.getStatus(), is(OK));
        assertThat(response.getBody(), containsString("<example-decoding>"));
    }
}
