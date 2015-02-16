package http.fakes;

import http.Request;
import http.Router;

import java.io.PrintStream;

public class FakeRouter extends Router {

  private Request request;

  public void dispatch(Request request, PrintStream output) {
    this.request = request;
  }

  public boolean calledWith(String method) {
    return request.getMethod().equals(method);
  }
}
