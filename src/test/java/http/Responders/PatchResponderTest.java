package http.responders;

import http.Request;
import http.TestHelper;
import http.filesystem.FileReader;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static http.responders.StatusCodes.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PatchResponderTest extends TestHelper {

  private File file;
  private FileWriter writer;
  private FileReader reader;
  private PatchResponder responder;

  @Before
  public void setUp() throws Exception {
    file = directory.newFile("file");
    writer = new FileWriter(file);
    reader = new FileReader(directory.getRoot());
    responder = new PatchResponder(reader);
  }

  @Test
  public void returnsContentsOfRequestedResource() throws IOException {
    writeDefaultContentToFile();
    ServerResponse response = responder.response(Request.withMethod("GET")
                                                        .addURI("/file")
                                                        .build());
    assertThat(response.getStatus(), is(OK));
    assertThat(response.getBody(), is("default content"));
  }

  @Test
  public void performsPatchIfEtagMatches() throws IOException {
    writeDefaultContentToFile();
    ServerResponse response = responder.response(Request.withMethod("PATCH")
                                                        .addURI("/file")
                                                        .addHeader("If-Match", "dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec")
                                                        .addBody("patched content")
                                                        .build());
    assertThat(response.getStatus(), is(NO_CONTENT));
    assertThat(reader.getFileContents(file), is("patched content"));
  }

  @Test
  public void doesNotApplyPatchIfNoEtagExists() throws IOException {
    writeDefaultContentToFile();
    ServerResponse response = responder.response(Request.withMethod("PATCH")
                                                        .addURI("/file")
                                                        .addBody("patched content")
                                                        .build());
    assertThat(response.getStatus(), is(PRECONDITION_FAILED));
    assertThat(reader.getFileContents(file), is("default content"));
  }

  @Test
  public void returnsANotFoundIfFileDoesNotExist() {
    ServerResponse response = responder.response(Request.withMethod("PATCH")
                                                        .addURI("/non-existent-file")
                                                        .build());
    assertThat(response.getStatus(), is(NOT_FOUND));
  }

  private void writeDefaultContentToFile() throws IOException {
    writer.write("default content");
    writer.close();
  }
}
