package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.entity.Flight;
import io.codelex.flightplanner.response.ValidResponse;
import io.codelex.flightplanner.services.FlightService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    public Flight getFlightById(@PathVariable Long id) {
        return flightService.getFlightById(id);
    }

    @DeleteMapping("/{id}")
    public ValidResponse deleteFlight(@PathVariable Long id) {
        this.flightService.deleteFlightById(id);
        return new ValidResponse("Flight deleted successfully");
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Flight addFlight(@Valid @RequestBody Flight flight) {
        return this.flightService.addFlight(flight);
    }
}