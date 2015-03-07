package http.utils;

public class Logger {

    private final org.apache.log4j.Logger logger;

    public Logger(org.apache.log4j.Logger logger) {
        this.logger = logger;
    }

    public void logError(Exception message) {
        logger.error(message);
    }

    public void logInfo(String message) {
        logger.info(message);
    }
}
