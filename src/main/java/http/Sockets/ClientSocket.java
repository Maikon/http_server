package http.sockets;

import java.io.InputStream;
import java.io.OutputStream;

public interface ClientSocket {
  public OutputStream getOutputStream();
  public InputStream getInputStream();

  void close();
}
