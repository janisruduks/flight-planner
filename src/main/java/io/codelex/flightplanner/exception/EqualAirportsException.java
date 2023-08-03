package io.codelex.flightplanner.exception;

public class EqualAirportsException extends RuntimeException {
    public EqualAirportsException() {
        super("Airports from and to cannot be equal");
    }
}