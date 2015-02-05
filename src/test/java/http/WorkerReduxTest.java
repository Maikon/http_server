package http;

import http.Fakes.FakeClientSocket;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class WorkerReduxTest {

  @Test
  public void readsInformationFromSocket() throws IOException {
    ByteArrayInputStream input = new ByteArrayInputStream("multiline\nrequest\n".getBytes());
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    FakeClientSocket socket = createSocket(input, output);
    WorkerRedux worker = createWorker(socket);

    assertThat(worker.read(), is("multiline\nrequest\n"));
  }

  @Test
  public void writesInformationToTheSocket() {
    ByteArrayInputStream input = new ByteArrayInputStream("".getBytes());
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    FakeClientSocket socket = createSocket(input, output);
    WorkerRedux worker = createWorker(socket);

    worker.write("Response 1\nResponse 2\n");
    assertThat(output.toString(), is("Response 1\nResponse 2\n"));
  }

  private WorkerRedux createWorker(FakeClientSocket socket) {
    return new WorkerRedux(socket);
  }

  private FakeClientSocket createSocket(ByteArrayInputStream input, ByteArrayOutputStream output) {
    return new FakeClientSocket(input, output);
  }
}
