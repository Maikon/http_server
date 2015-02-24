package http.responders;

import http.Request;
import http.filesystem.FileIO;

import java.io.File;

public class RootResponder implements Responder {
  public final String HTML_START = "<html><head></head><body>";
  public final String HTML_END = "</body></html>";
  private FileIO fileIO;

  public RootResponder(FileIO fileIO) {
    this.fileIO = fileIO;
  }

  public ServerResponse response(Request request) {
    String body = getDirectoryLinks();
    return ServerResponse.status(StatusCodes.OK)
                         .addHeader("Content-Type", "text/html")
                         .addBody(HTML_START + body + HTML_END)
                         .build();
  }

  private String getDirectoryLinks() {
    File[] files = fileIO.getDirectoryFiles();
    String body = "";
    for (File file : files) {
      String fileName = file.getName();
      body += "<a href='" + fileName + "'>" + fileName + "</a>";
    }
    return body;
  }
}
