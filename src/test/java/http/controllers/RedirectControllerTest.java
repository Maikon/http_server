package http.controllers;

import http.Request;
import http.responders.ServerResponse;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RedirectControllerTest {

  @Test
  public void respondsWithARedirect() {
    Controller controller = new RedirectController();
    ServerResponse response = controller.respond(Request.withMethod("GET")
                                                        .addURI("/redirect")
                                                        .build());
    assertThat(response.statusLine(), is("HTTP/1.1 301 Moved Permanently\r\n"));
  }
}
