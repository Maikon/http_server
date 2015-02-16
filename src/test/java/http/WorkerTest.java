package http;

import http.fakes.FakeClientSocket;
import http.fakes.FakeRouter;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class WorkerTest {

  private final String REQUEST_STATUS_LINE = "GET / HTTP/1.1\r\n";

  @Test
  public void dispatchesRequestToRouter() {
    FakeClientSocket socket = new FakeClientSocket(REQUEST_STATUS_LINE);
    FakeRouter router = new FakeRouter();
    Worker worker = new Worker(router, socket);

    worker.run();
    assertThat(router.calledWith("GET"), is(true));
  }

  @Test
  public void itClosesTheConnection() {
    FakeClientSocket socket = new FakeClientSocket(REQUEST_STATUS_LINE);
    FakeRouter router = new FakeRouter();
    Worker worker = new Worker(router, socket);

    worker.run();
    worker.stop();
    assertThat(socket.wasClosed(), is(true));
  }
}
