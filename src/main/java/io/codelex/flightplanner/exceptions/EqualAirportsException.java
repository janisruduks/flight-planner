package io.codelex.flightplanner.exceptions;

public class EqualAirportsException extends RuntimeException {
    public EqualAirportsException() {
        super("Airports from and to cannot be equal");
    }
}