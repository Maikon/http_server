package http.responders;

public enum StatusCodes {
    OK(200, "OK"),
    NO_CONTENT(204, "No Content"),
    PARTIAL_CONTENT(206, "Partial Content"),
    REDIRECT(301, "Moved Permanently"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    CONFLICT(409, "Conflict"),
    PRECONDITION_FAILED(412, "Precondition Failed"),
    REQUESTED_RANGE_NOT_SATISFIABLE(416, "Requested Range Not Satisfiable");

    private final int code;
    private final String phrase;

    StatusCodes(int code, String phrase) {
        this.code = code;
        this.phrase = phrase;
    }

    String getLine() {
        return "HTTP/1.1 " + code + " " + phrase + "\r\n";
    }
}
