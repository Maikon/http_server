package http.responders;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ServerResponseTest {

  @Test
  public void buildsSuccessResponse() {
    ServerResponse response = ServerResponse.status(200).build();
    assertThat(response.statusLine(), is("HTTP/1.1 200 OK\r\n"));
  }

  @Test
  public void addsMultipleHeadersToTheResponse() {
    Map<String, String> headers = new HashMap<>();
    headers.put("Header1", "Value1");
    headers.put("Header2", "Value2");
    ServerResponse response = ServerResponse.status(200)
                                            .addHeader("Header1", "Value1")
                                            .addHeader("Header2", "Value2")
                                            .build();
    assertThat(response.getHeaders(), is(headers));
  }

  @Test
  public void returnsHeadersInAStringFormat() {
    Map<String, String> headers = new HashMap<>();
    headers.put("Header1", "Value1");
    headers.put("Header2", "Value2");
    String headersString = "Header1: Value1\r\nHeader2: Value2\r\n";
    ServerResponse response = ServerResponse.status(200)
                                            .addHeader("Header1", "Value1")
                                            .addHeader("Header2", "Value2")
                                            .build();
    assertThat(response.stringifyHeaders(), is(headersString));
  }

  @Test
  public void addsBodyToTheRequest() {
    ServerResponse response = ServerResponse.status(200)
                                            .addBody("Body")
                                            .build();
    assertThat(response.getBody(), is("Body"));
  }

  @Test
  public void buildsFullResponse() {
    Map<String, String> headers = new HashMap<>();
    headers.put("Header1", "Value1");
    ServerResponse response = ServerResponse.status(200)
                                            .addHeader("Header1", "Value1")
                                            .addBody("Body")
                                            .build();
    assertThat(response.statusLine(), is("HTTP/1.1 200 OK\r\n"));
    assertThat(response.getHeaders(), is(headers));
    assertThat(response.getBody(), is("Body"));
  }

  @Test
  public void returnsFullResponseInStringFormat() {
    Map<String, String> headers = new HashMap<>();
    headers.put("Header1", "Value1");
    String fullResponse = "HTTP/1.1 200 OK\r\n" +
                          "Header1: Value1\r\n" +
                          "\r\n" +
                          "Body";
    ServerResponse response = ServerResponse.status(200)
                                            .addHeader("Header1", "Value1")
                                            .addBody("Body")
                                            .build();
    assertThat(response.toString(), is(fullResponse));
  }
}
