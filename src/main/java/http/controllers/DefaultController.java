package http.controllers;

import http.Request;
import http.filesystem.FileSystem;
import http.responders.ServerResponse;
import http.responders.StatusCodes;

import java.io.File;

public class DefaultController implements Controller {

  public final String HTML_END = "<body></body></html>";
  public final String HTML_START = "<html><head></head>";
  private final FileSystem fs;
  private File directory;

  public DefaultController(File directory) {
    this.directory = directory;
    this.fs = new FileSystem(directory);
  }

  @Override
  public ServerResponse respond(Request request) {
    if(request.getMethod().equals("OPTIONS")) {
      return ServerResponse.status(StatusCodes.OK)
                           .addHeader("Allow", "GET,HEAD,POST,OPTIONS,PUT")
                           .build();
    }
    if (request.getUri().equals("/")) {
      String body = createDirectoryListing();
        return ServerResponse.status(StatusCodes.OK)
                             .addBody(HTML_START + body + HTML_END)
                             .build();
    }
    if (fs.fileExists(request.getUri().substring(1)) && request.getMethod().equals("GET")) {
      String file = request.getUri();
      return ServerResponse.status(StatusCodes.OK)
                           .addHeader("Content-Length", String.valueOf(fs.contentLength(file)))
                           .addBody(new String(fs.readFile(request.getUri())))
                           .build();
    } else {
      return ServerResponse.status(StatusCodes.OK)
                           .addBody("")
                           .build();
    }
  }

  private String createDirectoryListing() {
    File[] files = directory.listFiles();
    String body = "";
    for (File file : files) {
      body += "<a href='"  + file.getName() + "'>" + file.getName() + "</a>\n";
    }
    return body;
  }
}
