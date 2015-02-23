package http.responders;

import http.Request;
import http.filesystem.FileReader;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import static http.responders.StatusCodes.*;

public class PatchResponder implements Responder {
  private final FileReader reader;

  public PatchResponder(FileReader reader) {
    this.reader = reader;
  }

  @Override
  public ServerResponse response(Request request) {
    if (reader.fileExists(request)) {
      String fileContents = reader.getFileContents(request);
      if (request.getMethod().equals("GET")) {
        return ServerResponse.status(OK)
                             .addBody(fileContents)
                             .build();
      } else if (request.getMethod().equals("PATCH")) {
        String fileSha = DigestUtils.sha1Hex(fileContents);
        String requestSha = request.getHeaders().get("If-Match");
        if (fileSha.equals(requestSha)) {
          writeContentToFile(request, reader.findFile(request));
          return ServerResponse.status(NO_CONTENT).build();
        } else {
          return ServerResponse.status(PRECONDITION_FAILED).build();
        }
      }
    }
    return ServerResponse.status(NOT_FOUND).build();
  }

  private void writeContentToFile(Request request, File file) {
    try {
      PrintWriter writer = new PrintWriter(file, "UTF-8");
      writer.write(request.getBody());
      writer.close();
    } catch (FileNotFoundException | UnsupportedEncodingException e) {
      System.out.println("Print message: " + e.getMessage());
    }
  }
}
