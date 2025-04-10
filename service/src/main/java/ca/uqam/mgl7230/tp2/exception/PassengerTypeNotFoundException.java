package ca.uqam.mgl7230.tp2.exception;

public class PassengerTypeNotFoundException extends RuntimeException {
    public PassengerTypeNotFoundException(String message) {
        super(message);
    }
}