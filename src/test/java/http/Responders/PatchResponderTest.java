package http.responders;

import http.Request;
import http.TestHelper;
import http.filesystem.FileReader;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static http.responders.StatusCodes.OK;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PatchResponderTest extends TestHelper {

  @Test
  public void returnsContentsOfRequestedResource() throws IOException {
    File file = directory.newFile("file");
    FileWriter writer = new FileWriter(file);
    writer.write("contents");
    writer.close();
    FileReader reader = new FileReader(directory.getRoot());
    Responder responder = new PatchResponder(reader);
    ServerResponse response = responder.response(Request.withMethod("GET")
                                                        .addURI("/file")
                                                        .build());
    assertThat(response.getStatus(), is(OK));
    assertThat(response.getBody(), is("contents"));
  }
}
