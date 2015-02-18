package http.responders;

import http.Request;
import org.junit.Test;

import static http.responders.StatusCodes.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RootResponderTest {

  @Test
  public void rootResponseIsOK() {
    ServerResponse res = new RootResponder().response(Request.withMethod("GET").build());
    assertThat(res.getStatus(), is(OK));
  }

  @Test
  public void rootResponseIsHTML() {
    ServerResponse res = new RootResponder().response(Request.withMethod("GET").build());
    assertThat(res.getHeader("Content-Type"), is("text/html"));
  }

  @Test
  public void rootResponseContainsBody() {
    ServerResponse res = new RootResponder().response(Request.withMethod("GET").build());
    assertThat(res.getBody(), containsString("<a href='file1'>file1</a>" +
                                             "<a href='file2'>file2</a>" +
                                             "<a href='image.gif'>image.gif</a>" +
                                             "<a href='image.jpeg'>image.jpeg</a>" +
                                             "<a href='image.png'>image.png</a>" +
                                             "<a href='text-file.txt'>text-file.txt</a>" +
                                             "<p>partial_content.txt</p>" +
                                             "<p>patch-content.txt</p>"));
  }
}
