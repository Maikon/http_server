package http.Responders;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ServerResponseTest {

  @Test
  public void buildsSuccessResponse() {
    ServerResponse response = new ServerResponse.Builder(200).build();
    assertThat(response.statusLine(), is("HTTP/1.1 200 OK\r\n"));
  }

  @Test
  public void addsMultipleHeadersToTheResponse() {
    Map<String, String> headers = new HashMap<>();
    headers.put("Header1", "Value1");
    headers.put("Header2", "Value2");
    ServerResponse response = new ServerResponse.Builder(200)
                                                .addHeader("Header1", "Value1")
                                                .addHeader("Header2", "Value2")
                                                .build();
    assertThat(response.getHeaders(), is(headers));
  }

  @Test
  public void addsBodyToTheRequest() {
    ServerResponse response = new ServerResponse.Builder(200)
                                                .addBody("Body")
                                                .build();
    assertThat(response.getBody(), is("Body"));
  }

  @Test
  public void buildsFullResponse() {
    Map<String, String> headers = new HashMap<>();
    headers.put("Header1", "Value1");
    ServerResponse response = new ServerResponse.Builder(200)
                                                .addHeader("Header1", "Value1")
                                                .addBody("Body")
                                                .build();
    assertThat(response.statusLine(), is("HTTP/1.1 200 OK\r\n"));
    assertThat(response.getHeaders(), is(headers));
    assertThat(response.getBody(), is("Body"));
  }
}
