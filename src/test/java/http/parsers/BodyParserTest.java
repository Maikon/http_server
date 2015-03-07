package http.parsers;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BodyParserTest {

    @Test
    public void parsesBodyWithOneLine() {
        BodyParser parser = new BodyParser();
        BufferedReader reader = createReader("Body of the Request\r\n");
        assertThat(parser.read(reader, 19), is("Body of the Request"));
    }

    @Test
    public void readsUpToSpecifiedAmountOfCharacters() {
        BodyParser parser = new BodyParser();
        BufferedReader reader = createReader("First Line\nSecond Line\nThird Line");
        assertThat(parser.read(reader, 22), is("First Line\nSecond Line"));
    }

    @Test
    public void ignoresBodyIfContentLengthIsNotPresent() {
        BodyParser parser = new BodyParser();
        BufferedReader reader = createReader("First Line\nSecond Line\nThird Line");
        assertThat(parser.read(reader, 0), is(""));
    }

    private BufferedReader createReader(String body) {
        StringReader request = new StringReader(body);
        return new BufferedReader(request);
    }
}
