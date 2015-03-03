package http;

import http.sockets.Socket;

import java.util.concurrent.ExecutorService;

public class Server {
  private final Router router;
  private final Socket clientSocket;
  private ExecutorService executor;

  public Server(ExecutorService executor, Socket clientSocket, Router router) {
    this.router = router;
    this.clientSocket = clientSocket;
    this.executor = executor;
  }

  public void start() {
    while (clientSocket.hasData()) {
      executor.execute(new Worker(router, clientSocket));
    }
    executor.shutdown();
  }
}
