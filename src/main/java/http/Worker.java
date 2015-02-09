package http;

import http.Parsers.RequestParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Worker implements Runnable {

  private final Socket socket;
  private PrintStream output;

  public Worker(Socket socket) {
    this.socket = socket;
  }

  @Override
  public void run() {
    try {
      output = new PrintStream(socket.getOutputStream());
      BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      String identifier = methodWithURI(reader);
      Router router = new Router();
      router.dispatch(identifier, output);
    } catch (IOException | RuntimeException e) {
      stop();
    }
    stop();
  }

  private String methodWithURI(BufferedReader reader) {
    RequestParser parser = new RequestParser();
    return parser.getUriAndMethod(reader);
  }

  private void stop() {
    try {
      output.close();
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
