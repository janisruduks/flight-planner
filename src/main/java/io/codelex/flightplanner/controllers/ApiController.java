package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.dtos.FlightSearchDTO;
import io.codelex.flightplanner.responses.FlightSearchResponse;
import io.codelex.flightplanner.services.FlightService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class ApiController {

    private final FlightService flightService;

    public ApiController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping("/flights/search")
    public ResponseEntity<FlightSearchResponse> searchForFlight(@Valid @RequestBody FlightSearchDTO flightSearchDTO) {
        return ResponseEntity.ok(flightService.searchForFlight(flightSearchDTO));
    }

    @GetMapping("/flights/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable String id) {
        return ResponseEntity.ok(flightService.getFlightById(id));
    }

    @GetMapping("/airports")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Airport>> searchByParameters(@RequestParam String search) {
        return ResponseEntity.ok(flightService.getFilteredMatchList(search.trim().toLowerCase()));
    }
}