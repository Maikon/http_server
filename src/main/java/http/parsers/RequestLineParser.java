package http.parsers;

import http.utils.Logger;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestLineParser {
    private final Logger logger = new Logger(org.apache.log4j.Logger.getLogger(RequestLineParser.class));

    public String read(BufferedReader reader) {
        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            logger.logError(e);
        }
        return line;
    }
}
