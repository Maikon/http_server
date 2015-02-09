package http;

import http.Parsers.Reader;
import http.Parsers.RequestParser;
import http.Sockets.ClientSocket;

import java.io.IOException;

public class Worker implements Runnable {
  private final Router router;
  private final Reader reader;
  private final RequestParser reqParser;
  private final ClientSocket client;

  public Worker(Router router, ClientSocket client) {
    this.client = client;
    this.router = router;
    this.reader = new Reader(client);
    this.reqParser = new RequestParser();
  }

  public void run() {
    String methodUri = reqParser.getUriAndMethod(reader.getInput());
    try {
      router.dispatch(methodUri, reader.getOutput());
    } catch (IOException e) {
      stop();
    }
    stop();
  }

  public void stop() {
    client.close();
  }
}
