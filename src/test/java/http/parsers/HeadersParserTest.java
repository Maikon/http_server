package http.parsers;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HeadersParserTest {

    @Test
    public void parsesHeaders() {
        HeadersParser parser = new HeadersParser();
        Map<String, String> headers = new HashMap<>();
        headers.put("Header1", "content");
        headers.put("Header2", "more content");
        BufferedReader reader = createReader("Header1: content\r\n" +
          "Header2: more content");
        assertThat(parser.read(reader), is(headers));
    }

    @Test
    public void parsesHeadersThatContainMultipleColons() {
        HeadersParser parser = new HeadersParser();
        Map<String, String> headers = new HashMap<>();
        headers.put("Date", "Day, 00 Month Year 00:00:00 GMT");
        BufferedReader reader = createReader("Date: Day, 00 Month Year 00:00:00 GMT\r\n");
        assertThat(parser.read(reader), is(headers));
    }

    @Test
    public void ignoresIrrelevantPartsOfTheRequest() throws IOException {
        HeadersParser parser = new HeadersParser();
        BufferedReader reader = createReader("Request Line to be ignored\r\n" +
          "Header: content\r\n" +
          "\r\n" +
          "Should-Not-Read: here\r\n");
        parser.read(reader);
        assertThat(reader.readLine(), is("Should-Not-Read: here"));
    }

    private BufferedReader createReader(String input) {
        StringReader content = new StringReader(input);
        return new BufferedReader(content);
    }
}
