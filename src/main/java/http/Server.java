package http;

import http.sockets.Socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
  private final Router router;
  private final ServerSocket socket;
  private ExecutorService executor;

  public Server(ServerSocket socket, Router router) {
    this.router = router;
    this.socket = socket;
    executor = Executors.newFixedThreadPool(20);
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
    try {
      socket.close();
      executor.shutdown();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
