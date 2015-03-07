package http;

import http.parsers.RequestParser;
import http.sockets.ClientSocket;
import http.sockets.Socket;
import http.utils.Logger;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;

public class Worker implements Runnable {
    private final Logger logger = new Logger(org.apache.log4j.Logger.getLogger(Worker.class));
    private final Router router;
    private RequestParser reqParser;
    private PrintStream output;
    private ClientSocket client;

    public Worker(Router router) {
        this.router = router;
    }

    public void run() {
        try {
            router.dispatch(reqParser.buildRequest(), output);
        } catch (IOException e) {
            logger.logError(e);
        }
        client.close();
    }

    public void setupClientWithIO(ServerSocket serverSocket) {
        try {
            client = new Socket(serverSocket.accept());
        } catch (IOException e) {
            logger.logError(e);
        }
        reqParser = new RequestParser(client);
        output = new PrintStream(client.getOutputStream());
    }
}
