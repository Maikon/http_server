package http.responders;

import http.Request;
import org.junit.Test;

import static http.responders.StatusCodes.REDIRECT;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RedirectResponderTest {

    @Test
    public void respondsWithARedirect() {
        ServerResponse res = new RedirectResponder().response(Request.withMethod("GET").build());
        assertThat(res.getStatus(), is(REDIRECT));
    }

    @Test
    public void hasTheCorrectLocation() {
        ServerResponse res = new RedirectResponder().response(Request.withMethod("GET").build());
        assertThat(res.getHeader("Location"), is("http://localhost:5000/"));
    }
}
