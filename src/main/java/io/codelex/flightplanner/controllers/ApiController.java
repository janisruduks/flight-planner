package io.codelex.flightplanner.controllers;


import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.repository.FlightRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping({"/api/flights", "/admin-api/flights"})
public class ApiUserController {

    private final FlightRepository flightRepository;

    public ApiUserController(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @GetMapping()
    public HashMap<Long, Flight> getFlights() {
        return this.flightRepository.getFlightsList();
    }

    @GetMapping()
    public List<Map.Entry<Long, Flight>> searchByParameters(@RequestParam String search) {
        HashMap<Long, Flight> x = flightRepository.getFlightsList();
        return x.entrySet().stream()
                .filter(a -> a.getValue().getFrom().getAirport().equals(search))
                .toList();
    }
}
