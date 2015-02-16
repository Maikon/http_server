package http.controllers;

import http.Request;
import http.responders.ServerResponse;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DefaultControllerTest {

  @Rule
  public TemporaryFolder directory = new TemporaryFolder();

  @Test
  public void returnsSuccessResponseIfFileExists() throws IOException {
    directory.newFile("file.txt");
    ServerResponse response = getController().respond(buildRequest("GET", "/file.txt"));
    assertThat(response.statusLine(), is("HTTP/1.1 200 OK\r\n"));
  }

  @Test
  public void respondsToTheOPTIONSMethod() {
    ServerResponse response = getController().respond(buildRequest("OPTIONS", "/method_options"));
    assertThat(response.stringifyHeaders(), is("Allow: GET,HEAD,POST,OPTIONS,PUT\r\n"));
  }

  @Test
  public void returnsContentsOfARequestResource() throws IOException {
    File file = directory.newFile("file.txt");
    FileWriter writer = new FileWriter(file);
    writer.write("Some content");
    writer.close();
    ServerResponse response = getController().respond(buildRequest("GET", "/file.txt"));
    assertThat(response.stringifyHeaders(), is("Content-Length: 12\r\n"));
    assertThat(response.getBody(), is("Some content"));
  }

  @Test
  public void doesNotReturnContentsOfARequestForWrongMethod() throws IOException {
    File file = directory.newFile("file.txt");
    FileWriter writer = new FileWriter(file);
    writer.write("Some content");
    writer.close();
    ServerResponse response = getController().respond(buildRequest("POST", "/file.txt"));
    assertThat(response.statusLine(), is("HTTP/1.1 200 OK\r\n"));
    assertThat(response.getBody(), is(""));
  }

  private Controller getController() {
    return new DefaultController(directory.getRoot());
  }

  private Request buildRequest(String method, String uri) {
    return Request.withMethod(method).addURI(uri).build();
  }
}
