package http.responders;

public class StatusLine {
  private final int code;
  private final String phrase;

  public StatusLine(int code, String phrase) {
    this.code = code;
    this.phrase = phrase;
  }

  @Override
  public String toString() {
    return "HTTP/1.1 " + code + " " + phrase + "\r\n";
  }
}
