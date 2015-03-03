package http;

import http.sockets.Socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;

public class Server {
  private final Router router;
  private final ServerSocket socket;
  private ExecutorService executor;

  public Server(ExecutorService executor, ServerSocket socket, Router router) {
    this.router = router;
    this.socket = socket;
    this.executor = executor;
  }

  public void start() {
    while (true) {
      executeRequestInThread();
    }
  }

  private void executeRequestInThread() {
    try {
      executor.submit(new Worker(router, new Socket(socket.accept())));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void stop() {
    executor.shutdown();
  }
}
