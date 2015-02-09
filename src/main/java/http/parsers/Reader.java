package http.Parsers;

import http.Sockets.ClientSocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Reader {
  private ClientSocket client;

  public Reader(ClientSocket client) {
    this.client = client;
  }

  public BufferedReader getInput() {
    InputStreamReader input = new InputStreamReader(client.getInputStream());
    return new BufferedReader(input);
  }
}
