package http.responders;

public enum StatusCodes {
  OK(200, "OK"),
  REDIRECT(301, "Moved Permanently"),
  NOT_FOUND(404, "Not Found"),
  METHOD_NOT_ALLOWED(405, "Method Not Allowed");

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
