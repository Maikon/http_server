package http;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RouterTest {

  @Rule
  public TemporaryFolder directory = new TemporaryFolder();

  @Test
  public void respondsToKnownURI() throws IOException {
    Router router = new Router(directory.getRoot());
    FakeOutput output = new FakeOutput();
    Request request = Request.withMethod("GET").addURI("/").build();
    router.dispatch(request, new PrintStream(output));
    assertThat(output.wasWritten, is(true));
  }

  @Test
  public void respondsToUnknownURI() throws IOException {
    Router router = new Router();
    Request request = Request.withMethod("GET").addURI("/unknown-path").build();
    router.dispatch(request, new PrintStream(new FakeOutput()));
    assertThat(router.lastResponse(), is("404"));
  }

  private class FakeOutput extends OutputStream {
    private boolean wasWritten;

    public FakeOutput() {
      this.wasWritten = false;
    }

    @Override
    public void write(int response) throws IOException {
      wasWritten = true;
    }
  }
}