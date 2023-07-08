package io.codelex.flightplanner.exceptions;

public class AirportTimeMismatchException extends RuntimeException {
    public AirportTimeMismatchException() {
        super("Airport arrival or departure time mismatch");
    }
}
