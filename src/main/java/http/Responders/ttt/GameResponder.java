package http.responders.ttt;

import http.Request;
import http.filesystem.FileIO;
import http.responders.Responder;
import http.responders.ServerResponse;

import java.io.File;

import static http.responders.StatusCodes.OK;

public class GameResponder implements Responder {

    private FileIO fileIO;

    public GameResponder(FileIO fileIO) {
        this.fileIO = fileIO;
    }

    @Override
    public ServerResponse response(Request request) {
        File index = new File(getIndexPagePath());
        return ServerResponse.status(OK)
                             .addHeader("Content-Type", "text/html")
                             .addBody(fileIO.getFileContents(index))
                             .build();
    }

    private String getIndexPagePath() {
        return getClass().getResource("/index.html").getPath();
    }
}
