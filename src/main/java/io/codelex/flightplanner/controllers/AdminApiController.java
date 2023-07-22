package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.responses.ValidResponse;
import io.codelex.flightplanner.services.FlightService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/admin-api/flights")
public class AdminApiController {

    private final FlightService flightService;

    public AdminApiController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/{id}")
    public Flight getFlightById(@PathVariable String id) {
        return flightService.getFlightById(id);
    }

    @DeleteMapping("/{id}")
    public ValidResponse deleteFlight(@PathVariable String id) {
        this.flightService.deleteFlightById(id);
        return new ValidResponse("Flight deleted successfully");
    }

    @PutMapping()
    public ResponseEntity<Flight> addFlight(@Valid @RequestBody Flight flight) {
        this.flightService.addFlight(flight);
        return ResponseEntity.status(HttpStatus.CREATED).body(flight);
    }
}