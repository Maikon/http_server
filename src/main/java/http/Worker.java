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
      InputStreamReader input = new InputStreamReader(socket.getInputStream());
      BufferedReader reader = new BufferedReader(input);
      RequestParser parser = new RequestParser();
      String request = reader.readLine();
      String path = parser.requestURI(request);
      Router router = new Router();
      router.dispatch(path, output);
    } catch (IOException | RuntimeException e) {
      stop();
    }
    stop();
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
