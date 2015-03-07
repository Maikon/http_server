package http.responders;

import http.utils.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServerResponse {
    public final Logger logger = new Logger(org.apache.log4j.Logger.getLogger(ServerResponse.class));
    public final String CRLF = "\r\n";
    private final StatusCodes status;
    private byte[] body;
    private Map<String, String> headers;

    private ServerResponse(Builder builder) {
        this.status = builder.status;
        this.headers = builder.headers;
        this.body = builder.body;
    }

    public static Builder status(StatusCodes code) {
        return new Builder(code);
    }

    public String statusLine() {
        return status.getLine();
    }

    public String stringifyHeaders() {
        String result = "";
        for (Map.Entry<String, String> header : headers.entrySet()) {
            result += header.getKey() + ": " + header.getValue() + CRLF;
        }
        return result;
    }

    @Override
    public String toString() {
        return statusLine() + stringifyHeaders() + CRLF + getBody();
    }

    public byte[] toBytes() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            addToArray(output, getBytes(statusLine()));
            addToArray(output, getBytes(stringifyHeaders()));
            addToArray(output, getBytes(CRLF));
            addToArray(output, body);
        } catch (IOException e) {
            logger.logError(e);
        }
        return output.toByteArray();
    }

    private void addToArray(ByteArrayOutputStream out, byte[] content) throws IOException {
        out.write(content);
    }

    public String getBody() {
        return new String(body);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public int getContentLength(String body) {
        return body.length();
    }

    public StatusCodes getStatus() {
        return status;
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    private byte[] getBytes(String content) {
        return content.getBytes();
    }

    public static class Builder {
        private StatusCodes status;
        private Map<String, String> headers = new HashMap<>();
        public byte[] body = new byte[0];

        public Builder(StatusCodes code) {
            this.status = code;
        }

        public ServerResponse build() {
            return new ServerResponse(this);
        }

        public Builder addHeader(String field, String value) {
            headers.put(field, value);
            return this;
        }

        public Builder addBody(String body) {
            this.body = body.getBytes();
            return this;
        }

        public Builder addBody(byte[] fileContents) {
            this.body = fileContents;
            return this;
        }
    }
}
