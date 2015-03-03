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
import static org.hamcrest.core.Is.isA;

public class ServerTest {

  private FakeExecutor executor;
  private FakeRouter router;
  private Server server;
  private http.sockets.Socket clientSocket;

  @Before
  public void setUp() throws Exception {
    executor = new FakeExecutor();
    clientSocket = new http.sockets.Socket(new FakeServerSocket());
    router = new FakeRouter();
    server = new Server(executor, clientSocket, router);
  }

  @Test
  public void processRequestThroughTheExecutor() throws IOException {
    server.start();
    assertThat(executor.calledWith(), isA(Runnable.class));
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
