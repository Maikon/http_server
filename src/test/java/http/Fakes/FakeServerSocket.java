package http.fakes;

import java.io.IOException;
import java.net.ServerSocket;

public class FakeServerSocket extends ServerSocket {
  private FakeClientSocket socket;

  public FakeServerSocket(FakeClientSocket socket) throws IOException {
    super();
    this.socket = socket;
  }

  @Override
  public java.net.Socket accept() {
    return socket;
  }
}
