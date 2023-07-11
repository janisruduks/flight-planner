package io.codelex.flightplanner.exceptions;

import io.codelex.flightplanner.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException reason) {
        String errorMessage = Objects.requireNonNull(reason.getBindingResult().getFieldError()).getDefaultMessage();
        return ResponseEntity.badRequest().body(new ErrorResponse(errorMessage));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException() {
        return ResponseEntity.badRequest().body(new ErrorResponse("Invalid JSON"));
    }

    @ExceptionHandler({EqualAirportsException.class, AirportDateMismatchException.class})
    public ResponseEntity<ErrorResponse> handleBadRequestExceptions(RuntimeException reason) {
        return ResponseEntity.badRequest().body(new ErrorResponse(reason.getMessage()));
    }

    @ExceptionHandler(FlightNotFoundByIdException.class)
    public ResponseEntity<ErrorResponse> handleFlightNotFoundByIdExceptions(FlightNotFoundByIdException reason) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(reason.getMessage()));
    }

    @ExceptionHandler(DuplicateEntryException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEntryExceptions(RuntimeException reason) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(reason.getMessage()));
    }
}