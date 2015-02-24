package http.responders;

import http.Request;
import http.filesystem.FileReader;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static http.responders.StatusCodes.*;

public class PatchResponder implements Responder {
  private final FileReader reader;

  public PatchResponder(FileReader reader) {
    this.reader = reader;
  }

  @Override
  public ServerResponse response(Request request) {
    if (reader.fileExists(request)) {
      String method = request.getMethod();
      Responder responder = responders().get(method);
      return responder.response(request);
    }
    return ServerResponse.status(NOT_FOUND).build();
  }

  private Map<String, Responder> responders() {
    Map<String, Responder> responders = new HashMap<>();
    responders.put("GET", getResponder());
    responders.put("PATCH", patchResponder());
    return responders;
  }

  private Responder patchResponder() {
    return request -> {
      String fileSha = DigestUtils.sha1Hex(reader.getFileContents(request));
      String requestSha = request.getHeaders().get("If-Match");
      if (fileSha.equals(requestSha)) {
        writeContentToFile(request, reader.findFile(request));
        return ServerResponse.status(NO_CONTENT).build();
      } else {
        return ServerResponse.status(PRECONDITION_FAILED).build();
      }
    };
  }

  private Responder getResponder() {
    return request -> ServerResponse.status(OK).addBody(reader.getFileContents(request)).build();
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
