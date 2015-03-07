package http;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;

public class Server {
    private final ServerSocket socket;
    private Worker worker;
    private ExecutorService executor;

    public Server(ExecutorService executor, ServerSocket serverSocket, Worker worker) {
        this.executor = executor;
        this.socket = serverSocket;
        this.worker = worker;
    }

    public void start() {
        while (isServerRunning()) {
            worker.setupClientWithIO(socket);
            executor.execute(worker);
        }
        executor.shutdown();
    }

    public boolean isServerRunning() {
        return true;
    }
}
