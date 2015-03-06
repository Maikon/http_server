package http;

import http.fakes.FakeClientSocket;
import http.fakes.FakeRouter;
import http.fakes.FakeServerSocket;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class WorkerTest {

  private final String REQUEST_STATUS_LINE = "GET / HTTP/1.1\r\n";

  @Test
  public void dispatchesRequestToRouter() throws IOException {
    FakeClientSocket socket = new FakeClientSocket(REQUEST_STATUS_LINE);
    FakeRouter router = new FakeRouter();
    Worker worker = new Worker(router);
    worker.setupClientWithIO(new FakeServerSocket(socket));
    worker.run();
    assertThat(router.calledWith("GET"), is(true));
  }

  @Test
  public void itClosesTheConnection() throws IOException {
    FakeClientSocket socket = new FakeClientSocket(REQUEST_STATUS_LINE);
    FakeRouter router = new FakeRouter();
    Worker worker = new Worker(router);
    worker.setupClientWithIO(new FakeServerSocket(socket));
    worker.run();
    assertThat(socket.wasClosed(), is(true));
  }
}
