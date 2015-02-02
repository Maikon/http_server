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
      String request;
      RequestParser parser = new RequestParser();
      byte[] res = "HTTP/1.1 200 OK\r\n\r\n".getBytes();
      output.write(res);
    } catch (IOException e) {
      System.out.println("Closing server...");
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
