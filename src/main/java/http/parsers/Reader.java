package http.parsers;

import http.sockets.ClientSocket;

import java.io.*;

public class Reader {
  private InputStreamReader input;

  public Reader(ClientSocket client) {
    input = new InputStreamReader(client.getInputStream());
  }

  public BufferedReader invoke() {
    return new BufferedReader(input);
  }
}
