package http.controllers;

import http.Request;
import http.responders.ServerResponse;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DefaultControllerTest {

  @Rule
  public TemporaryFolder directory = new TemporaryFolder();

  @Test
  public void returnsSuccessResponseIfFileExists() throws IOException {
    directory.newFile("file.txt");
    Controller controller = new DefaultController(directory.getRoot());
    ServerResponse response = controller.respond(Request.withMethod("GET")
                                                        .addURI("/file.txt")
                                                        .build());
    assertThat(response.statusLine(), is("HTTP/1.1 200 OK\r\n"));
  }

  @Test
  public void returnsNotFoundIfFileDoesNotExist() {
    Controller controller = new DefaultController(directory.getRoot());
    ServerResponse response = controller.respond(Request.withMethod("GET")
                                                        .addURI("/foobar")
                                                        .build());
    assertThat(response.statusLine(), is("HTTP/1.1 404 Not Found\r\n"));
  }
}
