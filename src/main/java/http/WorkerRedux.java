package http;

import http.Sockets.ClientSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WorkerRedux {
  private ClientSocket client;

  public WorkerRedux(ClientSocket client) {
    this.client = client;
  }

  public String readInput() throws IOException {
    InputStreamReader input = new InputStreamReader(client.getInputStream());
    BufferedReader reader = new BufferedReader(input);
    String result = "";
    String line;
    while ((line = reader.readLine()) != null) {
      result += line + "\n";
    }
    return result;
  }
}
