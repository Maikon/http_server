package http.responders;

import http.Request;
import http.TestHelper;
import http.filesystem.FileIO;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static http.responders.StatusCodes.NOT_FOUND;
import static http.responders.StatusCodes.OK;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ImagesResponderTest extends TestHelper {

    private FileIO fileIO;
    private Responder responder;

    @Before
    public void setUp() throws Exception {
        fileIO = new FileIO(directory.getRoot());
        responder = new ImagesResponder(fileIO);
    }

    @Test
    public void responseIsSuccessful() throws IOException {
        directory.newFile("image.jpeg");
        Request request = createGetRequest();
        assertThat(getResponse(request).getStatus(), is(OK));
    }

    @Test
    public void responseHeaderContainsCorrectExtension() throws IOException {
        directory.newFile("image.jpeg");
        Request request = createGetRequest();
        assertThat(getResponse(request).getHeader("Content-Type"), is("image/jpeg"));
    }

    @Test
    public void responseBodyContainsFileContents() throws IOException {
        File file = directory.newFile("file.txt");
        fileIO.writeToFile(file, "some content");
        Request request = Request.withMethod("GET").addURI("/file.txt").build();
        assertThat(getResponse(request).getBody(), is("some content"));
    }

    @Test
    public void returnsNotFoundIfFileDoesNotExist() {
        Request request = createGetRequest();
        assertThat(getResponse(request).getStatus(), is(NOT_FOUND));
    }

    private Request createGetRequest() {
        return Request.withMethod("GET").addURI("/image.jpeg").build();
    }

    private ServerResponse getResponse(Request request) {
        return responder.response(request);
    }
}
