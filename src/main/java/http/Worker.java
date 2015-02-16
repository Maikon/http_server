package http;

import http.parsers.RequestParser;
import http.sockets.ClientSocket;

import java.io.PrintStream;

public class Worker implements Runnable {
  private final Router router;
  private final RequestParser reqParser;
  private final ClientSocket client;
  private final PrintStream output;

  public Worker(Router router, ClientSocket client) {
    this.client = client;
    this.router = router;
    this.reqParser = new RequestParser(client);
    this.output = new PrintStream(client.getOutputStream());
  }

  public void run() {
    router.dispatch(reqParser.buildRequest(), output);
    stop();
  }

  public void stop() {
    client.close();
  }
}
