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
  private FakeServerSocket socket;
  private FakeRouter router;
  private Server server;

  @Before
  public void setUp() throws Exception {
    executor = new FakeExecutor();
    socket = new FakeServerSocket();
    router = new FakeRouter();
    server = new Server(executor, socket, router);
    server.start();
  }

  @Test
  public void processRequestThroughTheExecutor() throws IOException {
    assertThat(executor.calledWith(), isA(Runnable.class));
  }

  @Test
  public void closesTheExecutorWhenDoneWithRequestProcessing() throws IOException {
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
