package http;

import http.fakes.FakeClientSocket;
import http.fakes.FakeRouter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

public class ServerTest {

  private ExecutorService executor;
  private Server server;
  private Worker spyWorker;

  @Before
  public void setUp() throws Exception {
    http.sockets.Socket clientSocket = new http.sockets.Socket(new FakeServerSocket());
    FakeRouter router = new FakeRouter();
    Worker worker = new Worker(router, clientSocket);
    spyWorker = Mockito.spy(worker);
    executor = Executors.newFixedThreadPool(1);
    server = new Server(executor, spyWorker);
    doNothing().when(spyWorker).run();
  }

  @Test
  public void workerGetsExecuted() {
    server.start();
    verify(spyWorker).run();
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
