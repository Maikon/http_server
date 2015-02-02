package http;

import org.junit.Ignore;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ServerTest {

  @Ignore
  public void hasAPortAndDirectory() throws IOException {
    Server server = new Server(5000, "/directory");
    assertThat(server.getPort(), is(5000));
    assertThat(server.getDirectory(), is("/directory"));
  }

  @Ignore
  public void canBeStarted() throws IOException {
    Server server = new Server(5000, "/directory");
    server.start();
    assertThat(server.isRunning(), is(true));
    server.stop();
  }

  @Ignore
  public void canBeStopped() throws IOException {
    Server server = new Server(5000, "/directory");
    server.start();
    server.stop();
    assertThat(server.isRunning(), is(false));
  }
}