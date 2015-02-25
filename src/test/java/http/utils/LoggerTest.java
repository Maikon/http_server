package http.utils;

import http.Request;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoggerTest {

  @Test
  public void returnsLoggedRequests() {
    Logger.clear();
    logRequest("GET", "/");
    logRequest("POST", "/file");
    assertThat(Logger.loggedRequests(), is(asList("GET / HTTP/1.1", "POST /file HTTP/1.1")));
  }

  @Test
  public void canClearLogs() {
    logRequest("GET", "/");
    logRequest("POST", "/file");
    Logger.clear();
    assertThat(Logger.loggedRequests(), is(asList()));
  }

  private void logRequest(String get, String uri) {
    Logger.log(Request.withMethod(get).addURI(uri).build());
  }
}
