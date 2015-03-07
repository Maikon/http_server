package http.utils;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class LoggerTest {

    private org.apache.log4j.Logger loggerSpy;
    private Logger loggerWrapper;

    @Before
    public void setUp() {
        BasicConfigurator.configure();
        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger("FakeClass");
        loggerSpy = spy(logger);
        loggerWrapper = new Logger(loggerSpy);
    }

    @Test
    public void logsExceptionMessages() {
        Exception exception = new Exception();
        loggerWrapper.logError(exception);
        verify(loggerSpy).error(exception);
    }

    @Test
    public void logsMessagesOnStdOut() {
        String message = "Some Information";
        loggerWrapper.logInfo(message);
        verify(loggerSpy).info(message);
    }
}
