package io.codelex.flightplanner.exceptions;

public class AirportDateMismatchException extends RuntimeException {
    public AirportDateMismatchException() {
        super("Airport arrival or departure time mismatch");
    }
}