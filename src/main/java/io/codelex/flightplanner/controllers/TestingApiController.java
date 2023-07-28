package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.services.FlightService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testing-api")
public class TestingApiController {

    private final FlightService flightService;

    public TestingApiController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping("/clear")
    public void clear() {
        flightService.clearRepository();
    }
}