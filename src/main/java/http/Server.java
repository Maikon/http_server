package http;

import http.sockets.ClientSocket;
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
    System.out.println("Starting server at port: " + socket.getLocalPort());
    try {
      while (true) {
        ClientSocket clientSocket = new Socket(socket.accept());
        executor.submit(new Worker(router, clientSocket));
      }
    } catch (IOException e) {
      System.out.println("Closing server...");
    }
    stop();
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
