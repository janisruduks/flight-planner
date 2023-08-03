package io.codelex.flightplanner.exception;

public class DuplicateEntryException extends RuntimeException {
    public DuplicateEntryException() {
        super("Duplicate entry");
    }
}