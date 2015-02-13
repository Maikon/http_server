package http.controllers;

import http.responders.ServerResponse;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class NotFoundControllerTest {

  @Test
  public void returnsANotFoundResponse() {
    Controller controller = new NotFoundController();
    ServerResponse res = controller.respond();

    assertThat(res.statusLine(), is("HTTP/1.1 404 Not Found\r\n"));
  }
}
