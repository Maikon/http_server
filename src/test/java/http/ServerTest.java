package http;

import http.fakes.FakeClientSocket;
import http.fakes.FakeExecutor;
import http.fakes.FakeRouter;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ServerTest {

  private FakeExecutor executor;
  private FakeRouter router;
  private Server server;
  private http.sockets.Socket clientSocket;
  private Worker worker;

  @Before
  public void setUp() throws Exception {
    executor = new FakeExecutor();
    clientSocket = new http.sockets.Socket(new FakeServerSocket());
    router = new FakeRouter();
    worker = new Worker(router, clientSocket);
    server = new Server(executor, worker);
  }

  @Test
  public void processRequestThroughTheExecutor() throws IOException {
    server.start();
    assertThat(executor.calledWith(worker), is(true));
  }

  @Test
  public void closesTheExecutorWhenDoneWithRequestProcessing() throws IOException {
    server.start();
    assertThat(executor.isShutdown(), is(true));
  }

  private class FakeServerSocket extends ServerSocket {
    public FakeServerSocket() throws IOException {
    }

    @Override
    public Socket accept() {
      return new FakeClientSocket("Some data");
    }
  }
}
