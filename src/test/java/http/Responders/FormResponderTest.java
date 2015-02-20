package http.responders;

import http.Request;
import http.filesystem.FileReader;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static http.responders.StatusCodes.OK;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FormResponderTest {

  @Rule
  public TemporaryFolder directory = new TemporaryFolder();
  private FormResponder formResponder;

  @Before
  public void setUp() throws Exception {
    http.filesystem.FileReader reader = new FileReader(directory.getRoot());
    formResponder = new FormResponder(reader);
  }

  @Test
  public void getReturnsEmptyBodyWhenNoFormFile() {
    ServerResponse response = formResponder.response(Request.withMethod("GET").build());
    assertThat(response.getStatus(), is(OK));
    assertThat(response.getBody(), is(""));
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
