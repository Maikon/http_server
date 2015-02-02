package http;

import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RouterTest {

  @Test
  public void respondsToKnownURI() throws IOException {
    Router router = new Router();
    FakeOutput output = new FakeOutput();
    PrintStream out = new PrintStream(output);
    router.dispatch("GET /", out);
    assertThat(output.wasWritten, is(true));
  }

  @Test
  public void respondsToUnknownURI() throws IOException {
    Router router = new Router();
    FakeOutput output = new FakeOutput();
    PrintStream out = new PrintStream(output);
    router.dispatch("GET /unknown-path", out);
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