package http.responders;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ServerResponderTest {

    private final StatusCodes OK = StatusCodes.OK;
    private final String VALUE_1 = "Value1";
    private final String HEADER_2 = "Header2";
    private final String VALUE_2 = "Value2";
    private final String BODY = "Body";
    private final String HEADER_1 = "Header1";
    private final String STATUS_200_OK = "HTTP/1.1 200 OK\r\n";

    @Test
    public void buildsSuccessResponse() {
        ServerResponse response = ServerResponse.status(OK).build();
        assertThat(response.statusLine(), is(STATUS_200_OK));
    }

    @Test
    public void addsMultipleHeadersToTheResponse() {
        Map<String, String> headers = new HashMap<>();
        headers.put(HEADER_1, VALUE_1);
        headers.put(HEADER_2, VALUE_2);
        ServerResponse response = ServerResponse.status(OK)
          .addHeader(HEADER_1, VALUE_1)
          .addHeader(HEADER_2, VALUE_2)
          .build();
        assertThat(response.getHeaders(), is(headers));
    }

    @Test
    public void returnsHeadersInAStringFormat() {
        String headersString = "Header1: Value1\r\nHeader2: Value2\r\n";
        ServerResponse response = ServerResponse.status(OK)
          .addHeader(HEADER_1, VALUE_1)
          .addHeader(HEADER_2, VALUE_2)
          .build();
        assertThat(response.stringifyHeaders(), is(headersString));
    }

    @Test
    public void addsBodyToTheRequest() {
        ServerResponse response = ServerResponse.status(OK)
          .addBody(BODY)
          .build();
        assertThat(response.getBody(), is(BODY));
    }

    @Test
    public void getsTheContentLengthOfTheBody() {
        ServerResponse response = ServerResponse.status(OK)
          .build();
        assertThat(response.getContentLength("Some Body"), is(9));
    }

    @Test
    public void buildsFullResponse() {
        Map<String, String> headers = new HashMap<>();
        headers.put(HEADER_1, VALUE_1);
        ServerResponse response = ServerResponse.status(OK)
          .addHeader(HEADER_1, VALUE_1)
          .addBody(BODY)
          .build();
        assertThat(response.statusLine(), is(STATUS_200_OK));
        assertThat(response.getHeaders(), is(headers));
        assertThat(response.getBody(), is(BODY));
    }

    @Test
    public void returnsFullResponseInStringFormat() {
        String fullResponse = "HTTP/1.1 200 OK\r\n" +
          "Header1: Value1\r\n" +
          "\r\n" +
          "Body";
        ServerResponse response = ServerResponse.status(OK)
          .addHeader(HEADER_1, VALUE_1)
          .addBody(BODY)
          .build();
        assertThat(response.toString(), is(fullResponse));
    }

    @Test
    public void returnsFullResponseInBytes() {
        byte[] fullResponse = ("HTTP/1.1 200 OK\r\n" +
          "Header1: Value1\r\n" +
          "\r\n" +
          "Body").getBytes();
        ServerResponse response = ServerResponse.status(OK)
          .addHeader(HEADER_1, VALUE_1)
          .addBody(BODY)
          .build();
        assertThat(response.toBytes(), is(fullResponse));
    }
}
