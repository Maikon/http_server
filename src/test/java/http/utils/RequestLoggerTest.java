package http.utils;

import http.Request;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RequestLoggerTest {

    @Test
    public void returnsLoggedRequests() {
        RequestLogger.clear();
        logRequest("GET", "/");
        logRequest("POST", "/file");
        assertThat(RequestLogger.loggedRequests(), is(asList("GET / HTTP/1.1", "POST /file HTTP/1.1")));
    }

    @Test
    public void canClearLogs() {
        logRequest("GET", "/");
        logRequest("POST", "/file");
        RequestLogger.clear();
        assertThat(RequestLogger.loggedRequests(), is(asList()));
    }

    private void logRequest(String get, String uri) {
        RequestLogger.log(Request.withMethod(get).addURI(uri).build());
    }
}
