package http;

import http.fakes.FakeClientSocket;
import http.fakes.FakeServerSocket;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

public class ServerTest {

    private Worker workerSpy;
    private FakeServerSocket serverSocket;
    private ExecutorService executorMock;
    private Server serverSpy;

    @Before
    public void setUp() throws Exception {
        workerSpy = Mockito.spy(new Worker(new Router()));
        serverSocket = new FakeServerSocket(new FakeClientSocket("Some Data"));
        executorMock = Mockito.mock(ExecutorService.class);
        Server server = new Server(executorMock, serverSocket, workerSpy);
        serverSpy = Mockito.spy(server);
        Mockito.when(serverSpy.isServerRunning()).thenReturn(true, false);
    }

    @Test
    public void setsUpClientSocketWithIO() throws IOException {
        serverSpy.start();
        Mockito.verify(workerSpy).setupClientWithIO(serverSocket);
    }

    @Test
    public void executesTheWorker() throws IOException {
        serverSpy.start();
        Mockito.verify(executorMock).execute(workerSpy);
    }

}
