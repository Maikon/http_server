package http.controllers;

import http.Request;
import http.responders.ServerResponse;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MethodNotAllowedControllerTest {

  @Test
  public void respondsWithAMethodNotAllowed() {
    Controller controller = new MethodNotAllowedController();
    ServerResponse response = controller.respond(Request.withMethod("GET")
                                                        .addURI("/non-existing-resource")
                                                        .build());
    assertThat(response.statusLine(), is("HTTP/1.1 405 Method Not Allowed\r\n"));
  }
}
