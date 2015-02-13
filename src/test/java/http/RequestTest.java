package http;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RequestTest {

  @Test
  public void buildsRequestWithMethod() {
    Request request = Request.withMethod("GET").build();
    assertThat(request.getMethod(), is("GET"));
  }

  @Test
  public void buildsRequestWithURI() {
    Request request = Request.withMethod("GET")
                             .addURI("/")
                             .build();
    assertThat(request.getUri(), is("/"));
  }

  @Test
  public void buildsRequestWithHeaders() {
    Map<String, String> headers = new HashMap<>();
    headers.put("Header", "Value");
    Request request = Request.withMethod("GET")
                             .addHeader("Header", "Value")
                             .build();
    assertThat(request.getHeaders(), is(headers));
  }

  @Test
  public void buildsRequestWithBody() {
    Request request = Request.withMethod("POST")
                             .addBody("Body")
                             .build();
    assertThat(request.getBody(), is("Body"));
  }
}
