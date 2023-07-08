package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.dtos.FlightDTO;
import io.codelex.flightplanner.exceptions.DuplicateEntryException;
import io.codelex.flightplanner.exceptions.EqualAirportsException;
import io.codelex.flightplanner.exceptions.FlightNotFoundByIdException;
import io.codelex.flightplanner.mappers.FlightMapper;
import io.codelex.flightplanner.services.FlightService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@Validated
@RequestMapping("/admin-api/flights")
public class AdminController {

    //TODO fix dates
    private final FlightService flightService;
    private final FlightMapper mapper;

    public AdminController(FlightService flightService, FlightMapper mapper) {
        this.flightService = flightService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightDTO> getFlightById(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(flightService.getFlightById(id));
        } catch (FlightNotFoundByIdException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteFlight(@PathVariable String id) {
        try {
            this.flightService.deleteFlightById(id);
            return ResponseEntity.ok("Flight deleted successfully");
        } catch (FlightNotFoundByIdException e) {
            return ResponseEntity.ok("Flight not found");
        }
    }

    @PutMapping()
    public ResponseEntity<FlightDTO> addFlight(@Valid @RequestBody Flight flight) {
        FlightDTO flightDTO = mapper.toDTO(flight);
        try {
            this.flightService.addFlight(flightDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(flightDTO);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (EqualAirportsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public String handleException(MethodArgumentNotValidException reason) {
        return Objects.requireNonNull(reason.getBindingResult().getFieldError()).getDefaultMessage();
    }

}
