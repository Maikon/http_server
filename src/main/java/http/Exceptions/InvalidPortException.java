package http.exceptions;

public class InvalidPortException extends RuntimeException {
    public InvalidPortException() {
        super("The port must be in a numerical format i.e 5000");
    }
}
