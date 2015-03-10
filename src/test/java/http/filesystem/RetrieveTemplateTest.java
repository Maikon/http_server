package http.filesystem;

import http.exceptions.TemplateNotFoundException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RetrieveTemplateTest {

    @Test
    public void retrievesATemplateInBytes() {
        RetrieveTemplate template = new RetrieveTemplate("/file_1.txt");
        assertThat(template.inBytes(), is("Some data".getBytes()));
    }

    @Test(expected = TemplateNotFoundException.class)
    public void throwsExceptionIfTemplateDoesNotExist() {
        RetrieveTemplate template = new RetrieveTemplate("/non_existent_file.txt");
        template.inBytes();
    }
}
