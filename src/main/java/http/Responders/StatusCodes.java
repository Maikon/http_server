package http.responders;

public enum StatusCodes {
  OK(200, "OK"),
  NOT_FOUND(404, "Not Found");

  private final int code;
  private final String phrase;

  StatusCodes(int code, String phrase) {
    this.code = code;
    this.phrase = phrase;
  }

  public int getCode() {
    return code;
  }

  public String getPhrase() {
    return phrase;
  }

  String getLine() {
    return "HTTP/1.1 " + getCode() + " " + getPhrase() + "\r\n";
  }
}
