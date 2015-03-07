package http.responders;

import http.Request;
import org.junit.Test;

import static http.responders.StatusCodes.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MethodNotAllowedResponderTest {

    @Test
    public void respondsToAProhibitedRequestMethod() {
        ServerResponse res = new MethodNotAllowedResponder().response(Request.withMethod("GET").build());
        assertThat(res.getStatus(), is(METHOD_NOT_ALLOWED));
    }
}
