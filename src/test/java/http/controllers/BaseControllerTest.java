package http.controllers;

import http.responders.ServerResponse;
import http.responders.StatusCodes;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BaseControllerTest {

  private BaseController controller = new BaseController();

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

  private void checkResponseIs405(ServerResponse response) {
    assertThat(response.getStatusCode(), is(StatusCodes.METHOD_NOT_ALLOWED));
  }
}
