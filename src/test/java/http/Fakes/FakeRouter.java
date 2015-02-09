package http.Fakes;

import http.Router;

import java.io.PrintStream;

public class FakeRouter extends Router {
  private String dispatchValue;

  public boolean calledWith(String request) {
    return request.equals(dispatchValue);
  }

  public void dispatch(String identifier, PrintStream output) {
    dispatchValue = identifier;
  }
}
