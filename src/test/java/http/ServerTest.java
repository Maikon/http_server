package http;

import http.fakes.FakeClientSocket;
import http.fakes.FakeServerSocket;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ServerTest {

    private FakeServerSocket serverSocket;
    private Server serverSpy;
    private ExecutorService executorMock;

    @Before
    public void setUp() throws Exception {
        serverSocket = new FakeServerSocket(new FakeClientSocket("Some Data"));
        Router router = new Router();
        executorMock = Mockito.mock(ExecutorService.class);
        Server server = new Server(executorMock, serverSocket, router);
        serverSpy = Mockito.spy(server);
        Mockito.when(serverSpy.isServerRunning()).thenReturn(true, false);
    }

    @Test
    public void opensClientConnection() throws IOException {
        serverSpy.start();
        assertThat(serverSocket.wasOpened(), is(true));
    }

    @Test
    public void executesAWorker() throws Exception {
        ArgumentCaptor<Worker> captor = ArgumentCaptor.forClass(Worker.class);
        serverSpy.start();
        Mockito.verify(executorMock).execute(captor.capture());
    }
}
