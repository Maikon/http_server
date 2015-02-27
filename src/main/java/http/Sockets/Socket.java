package http.sockets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Socket implements ClientSocket {

  private final java.net.Socket client;

  public Socket(java.net.Socket client) {
    this.client = client;
  }

  @Override
  public OutputStream getOutputStream() {
    OutputStream out = null;
    try {
      out = client.getOutputStream();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return out;
  }

  @Override
  public InputStream getInputStream() {
    InputStream in = null;
    try {
      in = client.getInputStream();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return in;
  }

  @Override
  public void close() {
    try {
      client.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
