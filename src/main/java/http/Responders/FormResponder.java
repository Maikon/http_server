package http.responders;

import http.Request;

import java.io.*;
import java.nio.file.Files;

public class FormResponder implements Responder {
  private final File fs;

  public FormResponder(File directory) {
    this.fs = directory;
  }

  @Override
  public ServerResponse response(Request request) {
    File f = getFile(request);
    String content = "";
    if (request.getMethod().equals("POST") || request.getMethod().equals("PUT")) {
      try {
        PrintWriter writer = new PrintWriter(f, "UTF-8");
        writer.write(request.getBody());
        writer.close();
      } catch (FileNotFoundException | UnsupportedEncodingException e) {
        e.printStackTrace();
      }
      return ServerResponse.status(StatusCodes.OK).build();
    } else if(request.getMethod().equals("DELETE")) {
      f.delete();
      return ServerResponse.status(StatusCodes.OK).build();
    }
    else {
      try {
        InputStream in = Files.newInputStream(f.toPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        content = reader.readLine();
      } catch (IOException e) {
        e.printStackTrace();
      }
      return ServerResponse.status(StatusCodes.OK).addBody(content).build();
    }
  }

  private File getFile(Request request) {
    File f = null;
    File[] files = fs.listFiles();
    for (File file : files) {
      String uri = request.getUri().substring(1);
      if (file.getName().equals(uri)) {
        f = file;
      }
    }
    if (f == null) {
      f = new File(fs.getAbsolutePath() + request.getUri());
    }
    return f;
  }
}
