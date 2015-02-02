package http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
  private final int port;
  private final String directory;
  private boolean running;
  private ServerSocket socket;
  private Socket clientSocket;
  private ExecutorService executor;

  public Server(int port, String directory) throws IOException {
    this.port = port;
    this.directory = directory;
    socket = new ServerSocket(port);
    running = false;
    executor = Executors.newFixedThreadPool(20);
    System.out.println("Starting server at port: " + port);
    start();
  }

  public void start() {
    running = true;
    try {
      while (true) {
        clientSocket = socket.accept();
        executor.submit(new Worker(clientSocket));
      }
    } catch (IOException e) {
      System.out.println("Closing server...");
    }
    stop();
  }

  public int getPort() {
    return port;
  }

  public String getDirectory() {
    return directory;
  }

  public boolean isRunning() {
    return running;
  }

  public void stop() {
    running = false;
    try {
      socket.close();
      executor.shutdown();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
