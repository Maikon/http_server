package http.responders;

import http.Request;
import http.TestHelper;
import http.filesystem.FileIO;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static http.responders.StatusCodes.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RangeResponderTest extends TestHelper {

  private FileIO fileIO;
  private Responder responder;
  private File file;

  @Before
  public void setUp() throws Exception {
    fileIO = new FileIO(directory.getRoot());
    responder = new RangeResponder(fileIO);
    file = directory.newFile("file");
  }

  @Test
  public void respondsWithBadRequestIfRangeHeaderNotInclude() throws IOException {
    Request request = Request.withMethod("GET").addURI("/file").build();
    ServerResponse response = getResponse(request);
    assertThat(response.getStatus(), is(BAD_REQUEST));
  }

  @Test
  public void respondsWithNotFoundIfFileDoesNotExist() {
    Request request = Request.withMethod("GET").addURI("/random-file").build();
    ServerResponse response = getResponse(request);
    assertThat(response.getStatus(), is(NOT_FOUND));
  }

  @Test
  public void respondsWithPartialContentIfRequestIsCorrect() throws IOException {
    createFileWithContent();
    Request request = createRequestWithRange("0-3");
    ServerResponse response = getResponse(request);
    assertThat(response.getStatus(), is(PARTIAL_CONTENT));
  }

  @Test
  public void bodyContainsSpecifiedRequest() throws IOException {
    createFileWithContent();
    Request request = createRequestWithRange("0-3");
    ServerResponse response = getResponse(request);
    assertThat(response.getBody(), is("cont"));
  }

  @Test
  public void respondsToHeaderRangeWithoutStartPoint() {
    createFileWithContent();
    Request request = createRequestWithRange("-4");
    ServerResponse response = getResponse(request);
    assertThat(response.getBody(), is("tent"));
  }

  @Test
  public void respondsToHeaderRangeWithoutEndPoint() {
    createFileWithContent();
    Request request =  createRequestWithRange("4-");
    ServerResponse response = getResponse(request);
    assertThat(response.getBody(), is("ent"));
  }

  @Test
  public void responseIncludesContentRangeHeader() {
    createFileWithContent();
    Request request = createRequestWithRange("0-3");
    ServerResponse response = getResponse(request);
    assertThat(response.getHeader("Content-Range"), is("bytes 0-3/7"));
  }

  @Test
  public void responseIncludesContentLengthHeader() {
    createFileWithContent();
    Request request = createRequestWithRange("0-3");
    ServerResponse response = getResponse(request);
    assertThat(response.getHeader("Content-Length"), is("4"));
  }

  @Test
  public void responseIncludesDateHeader() {
    createFileWithContent();
    Request request = createRequestWithRange("0-3");
    ServerResponse response = getResponse(request);
    String date = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now());
    assertThat(response.getHeader("Date"), is(date));
  }

  @Test
  public void respondsToRangeThatIsOutOfBounds() {
    createFileWithContent();
    Request request = createRequestWithRange("20-");
    ServerResponse response = getResponse(request);
    assertThat(response.getStatus(), is(REQUESTED_RANGE_NOT_SATISFIABLE));
    assertThat(response.getHeader("Content-Range"), is("bytes 0/7"));
  }

  private Request createRequestWithRange(String range) {
    return Request.withMethod("GET")
                  .addURI("/file")
                  .addHeader("Range", "bytes=" + range)
                  .build();
  }

  private void createFileWithContent() {
    fileIO.writeToFile(file, "content");
  }

  private ServerResponse getResponse(Request request) {
    return responder.response(request);
  }
}
