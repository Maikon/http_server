package http;

import http.sockets.Socket;
import http.utils.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;

public class Server {
  private final Logger logger = new Logger(org.apache.log4j.Logger.getLogger(Server.class));
  private final Router router;
  private final ServerSocket socket;
  private ExecutorService executor;

  public Server(ExecutorService executor, ServerSocket socket, Router router) {
    this.router = router;
    this.socket = socket;
    this.executor = executor;
  }

  public void start() {
    try {
      while (true) {
        executor.submit(new Worker(router, new Socket(socket.accept())));
      }
    } catch (IOException e) {
      logger.logError(e);
    }
    executor.shutdown();
  }
}
