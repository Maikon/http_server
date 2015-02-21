package http.responders;

import http.Request;
import http.TestHelper;
import http.filesystem.FileReader;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FormResponderTest extends TestHelper {

  private FormResponder formResponder;

  @Before
  public void setUp() throws Exception {
    http.filesystem.FileReader reader = new FileReader(directory.getRoot());
    formResponder = new FormResponder(reader);
  }

  @Test
  public void writesToAFormIfPostContainsData() throws IOException {
    formResponder.response(Request.withMethod("POST")
                                  .addURI("/form")
                                  .addBody("Body")
                                  .build());
    ServerResponse getResponse = formResponder.response(Request.withMethod("GET")
                                                               .addURI("/form")
                                                               .build());
    assertThat(getResponse.getBody(), is("Body"));
  }

  @Test
  public void overridesFileContentsOnPUT() throws IOException {
    File form = directory.newFile("/form");
    FileWriter writer = new FileWriter(form);
    writer.write("Old Body");
    formResponder.response(Request.withMethod("PUT")
                                  .addURI("/form")
                                  .addBody("New Body")
                                  .build());
    ServerResponse response = formResponder.response(Request.withMethod("GET")
                                                            .addURI("/form")
                                                            .build());
    assertThat(response.getBody(), is("New Body"));
  }

  @Test
  public void deletesFileOnDELETERequest() throws IOException {
    directory.newFile("/form");
    formResponder.response(Request.withMethod("DELETE")
                                  .addURI("/form")
                                  .build());
    boolean fileExists = new File(directory.getRoot(), "form").exists();
    assertThat(fileExists, is(false));
  }
}
