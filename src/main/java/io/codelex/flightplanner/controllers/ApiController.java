package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.dto.FlightSearchDTO;
import io.codelex.flightplanner.entity.Airport;
import io.codelex.flightplanner.entity.Flight;
import io.codelex.flightplanner.response.FlightSearchResponse;
import io.codelex.flightplanner.services.FlightService;
import jakarta.validation.Valid;
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
    public FlightSearchResponse searchForFlight(@Valid @RequestBody FlightSearchDTO flightSearchDTO) {
        return flightService.searchForFlight(flightSearchDTO);
    }

    @GetMapping("/flights/{id}")
    public Flight getFlightById(@PathVariable Long id) {
        return flightService.getFlightById(id);
    }

    @GetMapping("/airports")
    public List<Airport> searchByParameters(@RequestParam String search) {
        return flightService.getFilteredMatchList(search);
    }
}