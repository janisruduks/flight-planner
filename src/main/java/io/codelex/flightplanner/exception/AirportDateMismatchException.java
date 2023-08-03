package io.codelex.flightplanner.exception;

public class AirportDateMismatchException extends RuntimeException {
    public AirportDateMismatchException() {
        super("Airport arrival or departure time mismatch");
    }
}