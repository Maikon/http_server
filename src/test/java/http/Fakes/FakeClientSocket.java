package http.Fakes;

import http.Sockets.ClientSocket;

import java.io.*;

public class FakeClientSocket implements ClientSocket {
  private ByteArrayInputStream input;
  private ByteArrayOutputStream output;

  public FakeClientSocket(ByteArrayInputStream input, ByteArrayOutputStream output) {
    this.input = input;
    this.output = output;
  }

  @Override
  public OutputStream getOutputStream() {
    return output;
  }

  @Override
  public InputStream getInputStream() {
    return input;
  }
}
