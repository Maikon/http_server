package http;

import java.util.concurrent.ExecutorService;

public class Server {
  private Worker worker;
  private ExecutorService executor;

  public Server(ExecutorService executor, Worker worker) {
    this.executor = executor;
    this.worker = worker;
  }

  public void start() {
    while (worker.clientHasData()) {
      executor.execute(worker);
    }
    executor.shutdown();
  }
}
