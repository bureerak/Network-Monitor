package main.exception;

public class InvalidIPException extends RuntimeException {
    public InvalidIPException() {
        super("Invalid IP Address and/or Subnet Mask!");
    }

    public InvalidIPException(String message) {
        super(message);
    }
}
