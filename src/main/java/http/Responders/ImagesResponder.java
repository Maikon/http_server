package http.responders;

import http.Request;
import http.filesystem.FileIO;

import java.io.File;

import static http.responders.StatusCodes.NOT_FOUND;
import static http.responders.StatusCodes.OK;

public class ImagesResponder implements Responder {
    private final FileIO fileIO;

    public ImagesResponder(FileIO fileIO) {
        this.fileIO = fileIO;
    }

    @Override
    public ServerResponse response(Request request) {
        if (fileIO.fileExists(request)) {
            File file = fileIO.findFile(request).get();
            byte[] fileContents = fileIO.getFileBytes(file);
            return ServerResponse.status(OK)
              .addHeader("Content-Type", "image/" + fileExtension(request))
              .addBody(fileContents)
              .build();
        }
        return ServerResponse.status(NOT_FOUND).build();
    }

    private String fileExtension(Request request) {
        return request.getUri().split("\\.")[1];
    }
}
