package http;

import http.fakes.FakeClientSocket;
import http.fakes.FakeRouter;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class WorkerTest {

    private FakeClientSocket socket;
    private FakeRouter router;
    private Worker worker;

    @Before
    public void setUp() {
        socket = new FakeClientSocket("GET / HTTP/1.1\r\n");
        router = new FakeRouter();
        worker = new Worker(router, socket);
    }

    @Test
    public void dispatchesRequestToRouter() throws IOException {
        worker.run();
        assertThat(router.calledWith("GET"), is(true));
    }

    @Test
    public void itClosesTheConnection() throws IOException {
        worker.run();
        assertThat(socket.wasClosed(), is(true));
    }
}
