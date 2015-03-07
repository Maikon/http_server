package http.parsers;

import http.utils.Logger;

import java.io.BufferedReader;
import java.io.IOException;

public class BodyParser {
    private final Logger logger = new Logger(org.apache.log4j.Logger.getLogger(BodyParser.class));

    public String read(BufferedReader reader, int contentLength) {
        String body = "";
        char[] buffer = new char[contentLength];
        try {
            reader.read(buffer);
            for (char s : buffer) {
                body += s;
            }
        } catch (IOException e) {
            logger.logError(e);
        }
        return body.trim();
    }
}

