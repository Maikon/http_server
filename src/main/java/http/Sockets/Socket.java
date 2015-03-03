package http.sockets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.util.Scanner;

public class Socket implements ClientSocket {

  private final java.net.Socket client;

  public Socket(ServerSocket serverSocket) {
    this.client = getClientSocket(serverSocket);
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

  @Override
  public boolean hasData() {
    Scanner reader = new Scanner(getInputStream());
    return reader.hasNext();
  }

  private java.net.Socket getClientSocket(ServerSocket serverSocket) {
    try {
      return serverSocket.accept();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
