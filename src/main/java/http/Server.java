package http;

import http.sockets.Socket;
import http.utils.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;

public class Server {
    private final Logger logger = new Logger(org.apache.log4j.Logger.getLogger(Server.class));
    private final ServerSocket socket;
    private final Router router;
    private final ExecutorService executor;

    public Server(ExecutorService executor, ServerSocket serverSocket, Router router) {
        this.executor = executor;
        this.socket = serverSocket;
        this.router = router;
    }

    public void start() {
        while (isServerRunning()) {
            acceptRequest();
        }
        executor.shutdown();
    }

    public boolean isServerRunning() {
        return true;
    }

    private void acceptRequest() {
        try {
            java.net.Socket clientSocket = socket.accept();
            executor.execute(new Worker(router, new Socket(clientSocket)));
        } catch (IOException e) {
            logger.logError(e);
        }
    }
}
