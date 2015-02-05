package http;

import http.Sockets.ClientSocket;

import java.io.*;

public class WorkerRedux {
  private ClientSocket client;

  public WorkerRedux(ClientSocket client) {
    this.client = client;
  }

  public String read() throws IOException {
    InputStreamReader input = new InputStreamReader(client.getInputStream());
    BufferedReader reader = new BufferedReader(input);
    String result = "";
    String line;
    while ((line = reader.readLine()) != null) {
      result += line + "\n";
    }
    return result;
  }

  public void write(String response) {
    OutputStream output = client.getOutputStream();
    PrintStream writer = new PrintStream(output);
    try {
      writer.write(response.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
