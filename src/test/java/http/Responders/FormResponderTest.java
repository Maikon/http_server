package http.responders;

import http.Request;
import http.TestHelper;
import http.filesystem.FileIO;
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
        FileIO fileIO = new FileIO(directory.getRoot());
        formResponder = new FormResponder(fileIO);
    }

    @Test
    public void writesToAFormOnAPost() throws IOException {
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
    public void overridesFileContentsOnAPut() throws IOException {
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
    public void deletesFileOnADelete() throws IOException {
        directory.newFile("/form");
        formResponder.response(Request.withMethod("DELETE")
          .addURI("/form")
          .build());
        boolean fileExists = new File(directory.getRoot(), "form").exists();
        assertThat(fileExists, is(false));
    }
}
