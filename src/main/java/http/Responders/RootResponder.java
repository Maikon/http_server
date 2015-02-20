package http.responders;

import http.Request;
import http.filesystem.FileReader;

import java.io.File;

public class RootResponder implements Responder {
  public final String HTML_START = "<html><head></head><body>";
  public final String HTML_END = "</body></html>";
  private FileReader reader;

  public RootResponder(FileReader reader) {
    this.reader = reader;
  }

  public ServerResponse response(Request request) {
    String body = getDirectoryLinks();
    return ServerResponse.status(StatusCodes.OK)
                         .addHeader("Content-Type", "text/html")
                         .addBody(HTML_START + body + HTML_END)
                         .build();
  }

  private String getDirectoryLinks() {
    File[] files = reader.getDirectoryFiles();
    String body = "";
    for (File file : files) {
      String fileName = file.getName();
      body += "<a href='" + fileName + "'>" + fileName + "</a>";
    }
    return body;
  }
}
