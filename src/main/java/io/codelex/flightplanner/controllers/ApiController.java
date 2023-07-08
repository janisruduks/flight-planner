package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.dtos.AirportDTO;
import io.codelex.flightplanner.dtos.FlightDTO;
import io.codelex.flightplanner.dtos.FlightSearchDTO;
import io.codelex.flightplanner.responses.FlightSearchResponse;
import io.codelex.flightplanner.services.FlightServiceMemory;
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

    private final FlightServiceMemory flightService;

    public ApiController(FlightServiceMemory flightService) {
        this.flightService = flightService;
    }

    @PostMapping("/flights/search")
    public ResponseEntity<FlightSearchResponse> searchForFlight(@Valid @RequestBody FlightSearchDTO flightSearchDTO) {
        FlightSearchResponse response = flightService.searchForFlight(flightSearchDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/flights/{id}")
    public ResponseEntity<FlightDTO> getFlightById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(flightService.getFlightById(id));
    }

    @GetMapping("/airports")
    @ResponseStatus(HttpStatus.OK)
    public List<AirportDTO> searchByParameters(@RequestParam String search) {
        return flightService.getFilteredMatchList(search.trim().toLowerCase());
    }
}
