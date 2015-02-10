package http;

import http.Parsers.RequestParser;
import http.Sockets.ClientSocket;

import java.io.IOException;
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
    try {
      router.dispatch(reqParser.getUriAndMethod(), output);
    } catch (IOException e) {
      // nothing to be done here
    }
    stop();
  }

  public void stop() {
    client.close();
  }
}
