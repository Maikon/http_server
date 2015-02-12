package http;

import http.fakes.FakeClientSocket;
import http.fakes.FakeRouter;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class WorkerTest {

  private final String FULL_REQUEST = "GET / HTTP/1.1\r\n" +
                                      "Accept-Language: en-us\r\n" +
                                      "Host: localhost\r\n\r\n" +
                                      "Body of the Request";

  @Test
  public void dispatchesRequestToRouter() {
    FakeClientSocket socket = new FakeClientSocket(FULL_REQUEST);
    FakeRouter router = new FakeRouter();
    Worker worker = new Worker(router, socket);

    worker.run();
    assertThat(router.calledWith("GET /"), is(true));
  }

  @Test
  public void itClosesTheConnection() {
    FakeClientSocket socket = new FakeClientSocket(FULL_REQUEST);
    FakeRouter router = new FakeRouter();
    Worker worker = new Worker(router, socket);

    worker.run();
    worker.stop();
    assertThat(socket.wasClosed(), is(true));
  }
}
