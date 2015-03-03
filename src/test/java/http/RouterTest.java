package http;

import http.responders.Responder;
import http.responders.ServerResponse;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import static http.responders.StatusCodes.NOT_FOUND;
import static http.responders.StatusCodes.OK;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.MatcherAssert.assertThat;

public class RouterTest {
  private Router router;
  private FakeOutput output;
  private PrintStream printStream;

  @Rule
  public TemporaryFolder directory = new TemporaryFolder();

  @Before
  public void setUp() throws Exception {
    router = new Router();
    output = new FakeOutput();
    printStream = new PrintStream(output);
  }

  @Test
  public void respondsToKnownURI() throws IOException {
    router.registerRoute("GET /", new SuccessResponder());
    Request request = buildRequest("GET", "/");
    router.dispatch(request, printStream);
    assertThat(output.wasWritten(), is(true));
    assertThat(router.lastResponse(), is(OK));
  }

  @Test
  public void respondsToUnknownURI() throws IOException {
    Request request = buildRequest("GET", "/unknown");
    router.dispatch(request, printStream);
    assertThat(router.lastResponse(), is(NOT_FOUND));
  }

  @Test
  public void registersARouteWithAResponder() {
    Router router = new Router();
    router.registerRoute("GET /", new SuccessResponder());
    assertThat(router.getResponderFor("GET /"), isA(Responder.class));
  }

  private Request buildRequest(String method, String uri) {
    return Request.withMethod(method).addURI(uri).build();
  }

  private class FakeOutput extends OutputStream {
    private boolean written = false;

    @Override
    public void write(int response) throws IOException {
      written = true;
    }

    public boolean wasWritten() {
      return written;
    }
  }

  private class SuccessResponder implements Responder {

    @Override
    public ServerResponse response(Request request) {
      return ServerResponse.status(OK).build();
    }
  }
}
