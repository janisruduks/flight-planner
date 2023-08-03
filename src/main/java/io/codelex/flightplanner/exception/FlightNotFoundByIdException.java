package io.codelex.flightplanner.exception;

public class FlightNotFoundByIdException extends RuntimeException {
    public FlightNotFoundByIdException() {
        super("Flight could not be found by ID");
    }
}