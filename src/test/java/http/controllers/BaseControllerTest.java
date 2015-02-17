package http.controllers;

import http.Request;
import http.responders.ServerResponse;
import http.responders.StatusCodes;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BaseControllerTest {

  private BaseController controller = new BaseController();
  private ControllerSpy controllerSpy = new ControllerSpy();

  @Test
  public void defaultResponseToGetRequest() {
    checkResponseIs405(controller.doGet());
  }

  @Test
  public void defaultResponseToPostRequest() {
    checkResponseIs405(controller.doPost());
  }

  @Test
  public void defaultResponseToPutRequest() {
    checkResponseIs405(controller.doPut());
  }

  @Test
  public void defaultResponseToDeleteRequest() {
    checkResponseIs405(controller.doDelete());
  }

  @Test
  public void defaultResponseToOptionsRequest() {
    checkResponseIs405(controller.doOptions());
  }

  @Test
  public void mapsGetRequest() {
    controllerSpy.dispatch(Request.withMethod("GET").build());
    assertThat(controllerSpy.doGetCalled(), is(true));
  }

  @Test
  public void mapsPostRequest() {
    controllerSpy.dispatch(Request.withMethod("POST").build());
    assertThat(controllerSpy.doPostCalled(), is(true));
  }

  @Test
  public void mapsPutRequest() {
    controllerSpy.dispatch(Request.withMethod("PUT").build());
    assertThat(controllerSpy.doPutCalled(), is(true));
  }

  @Test
  public void mapsDeleteRequest() {
    controllerSpy.dispatch(Request.withMethod("DELETE").build());
    assertThat(controllerSpy.doDeleteCalled(), is(true));
  }

  @Test
  public void mapsOptionsRequest() {
    controllerSpy.dispatch(Request.withMethod("OPTIONS").build());
    assertThat(controllerSpy.doOptionsCalled(), is(true));
  }

  private void checkResponseIs405(ServerResponse response) {
    assertThat(response.getStatusCode(), is(StatusCodes.METHOD_NOT_ALLOWED));
  }

  private class ControllerSpy extends BaseController {
    private boolean called = false;

    public ServerResponse doGet() {
      called = true;
      return null;
    }

    public ServerResponse doPost() {
      called = true;
      return null;
    }

    public ServerResponse doPut() {
      called = true;
      return null;
    }

    public ServerResponse doDelete() {
      called = true;
      return null;
    }

    public ServerResponse doOptions() {
      called = true;
      return null;
    }

    public boolean doGetCalled() {
      return called;
    }

    public boolean doPostCalled() {
      return called;
    }

    public boolean doPutCalled() {
      return called;
    }

    public boolean doDeleteCalled() {
      return called;
    }

    public boolean doOptionsCalled() {
      return called;
    }
  }
}
