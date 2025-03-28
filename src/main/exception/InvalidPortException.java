package main.exception;

public class InvalidPortException extends RuntimeException {
    public InvalidPortException() {
        super("Invalid port number!");
    }

    public InvalidPortException(String message) {
        super(message);
    }
}
