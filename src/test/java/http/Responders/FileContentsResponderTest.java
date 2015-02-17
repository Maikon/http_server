package http.responders;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FileContentsResponderTest {

  @Test
  public void respondsWithTheContentsOfTheFile() {
    ServerResponse res = new FileContentsResponder().response();
    assertThat(res.toString(), is("HTTP/1.1 200 OK\r\n" +
                                  "Content-Type: text/html\r\n\r\n" +
                                  "<html><head></head><body>" +
                                  "<p>file1 contents</p>" +
                                  "</body></html>"));
  }
}