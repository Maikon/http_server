package http.responders;

import http.Request;
import http.filesystem.FileIO;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static http.responders.StatusCodes.*;

public class RangeResponder implements Responder {
  private final FileIO fileIO;

  public RangeResponder(FileIO fileIO) {
    this.fileIO = fileIO;
  }

  @Override
  public ServerResponse response(Request request) {
    if (fileExists(request)) {
      if (requestHasRange(request)) {
        File file = getFile(request);
        String[] requestRange = rangeFromHeader(request);
        int from = parseString(requestRange[0]);
        int to = parseString(requestRange[1]);
        if (rangeInvalid(file, from)) {
          return rangeNotSatisfiable(file);
        }
        byte[] contents = getRange(file, from, to);
        return partialContent(file, from, to, contents);
      }
      return ServerResponse.status(BAD_REQUEST).build();
    }
    return ServerResponse.status(NOT_FOUND).build();
  }

  private byte[] getRange(File file, int from, int to) {
    return fileIO.getRange(from, to, file);
  }

  private File getFile(Request request) {
    return fileIO.findFile(request).get();
  }

  private boolean fileExists(Request request) {
    return fileIO.fileExists(request);
  }

  private ServerResponse partialContent(File file, int from, int to, byte[] contents) {
    return ServerResponse.status(PARTIAL_CONTENT)
                         .addHeader("Content-Length", lengthOf(contents))
                         .addHeader("Content-Range", rangeFormat(file, from, to))
                         .addHeader("Content-Type", "text/plain")
                         .addHeader("Date", getCurrentDate())
                         .addBody(contents)
                         .build();
  }

  private String lengthOf(byte[] contents) {
    return String.valueOf(contents.length);
  }

  private ServerResponse rangeNotSatisfiable(File file) {
    return ServerResponse.status(REQUESTED_RANGE_NOT_SATISFIABLE)
                         .addHeader("Content-Range", rangeFormat(file, -1, -1))
                         .build();
  }

  private boolean rangeInvalid(File file, int to) {
    return to > file.length();
  }

  private String rangeFormat(File file, int from, int to) {
    if (from == -1 & to == -1) {
      return "bytes " + "0/" + file.length();
    }
    return "bytes " + from + "-" + to + "/" + file.length();
  }

  private String getCurrentDate() {
    return DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now());
  }

  private String[] rangeFromHeader(Request request) {
    String[] values = request.getHeaders().get("Range").split("=");
    String range = values[1];
    return range.split("-", 2);
  }

  private int parseString(String s) {
    int number;
    try {
      number = Integer.parseInt(s);
    } catch (NumberFormatException e) {
      number = -1;
    }
    return number;
  }

  private boolean requestHasRange(Request request) {
    return request.getHeaders().containsKey("Range");
  }
}
