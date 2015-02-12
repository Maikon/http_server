package http.Fakes;

import http.sockets.ClientSocket;

import java.io.*;

public class FakeClientSocket implements ClientSocket {
  private ByteArrayInputStream input;
  private ByteArrayOutputStream output;
  private boolean closed = false;

  public FakeClientSocket(String input) {
    this.input = new ByteArrayInputStream(input.getBytes());
    this.output = new ByteArrayOutputStream();
  }

  @Override
  public OutputStream getOutputStream() {
    return output;
  }

  @Override
  public InputStream getInputStream() {
    return input;
  }

  @Override
  public void close() {
    closed = true;
  }

  public boolean wasClosed() {
    return closed;
  }
}
