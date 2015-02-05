package http;

import http.Fakes.FakeClientSocket;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class WorkerReduxTest {

  @Test
  public void retrievesInformationFromSocket() throws IOException {
    FakeClientSocket socket = new FakeClientSocket(new ByteArrayInputStream("request\n".getBytes()));
    WorkerRedux worker = new WorkerRedux(socket);
    assertThat(worker.readInput(), is("request\n"));
  }

  @Test
  public void retrievesMultipleInformationFromSocket() throws IOException {
    FakeClientSocket socket = new FakeClientSocket(new ByteArrayInputStream("multiline\nrequest\n".getBytes()));
    WorkerRedux worker = new WorkerRedux(socket);
    assertThat(worker.readInput(), is("multiline\nrequest\n"));
  }
}
