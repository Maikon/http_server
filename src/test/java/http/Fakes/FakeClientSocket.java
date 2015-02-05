package http.Fakes;

import http.Sockets.ClientSocket;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class FakeClientSocket implements ClientSocket {
  private final ByteArrayInputStream input;

  public FakeClientSocket(ByteArrayInputStream input) {
    this.input = input;
  }

  @Override
  public InputStream getInputStream() {
    return input;
  }
}
