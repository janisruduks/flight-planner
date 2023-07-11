package io.codelex.flightplanner.exceptions;

public class DuplicateEntryException extends RuntimeException {
    public DuplicateEntryException() {
        super("Duplicate entry");
    }
}