package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.services.FlightServiceMemory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/testing-api")
public class TestingApiController {

    private final FlightServiceMemory flightService;

    public TestingApiController(FlightServiceMemory flightService) {
        this.flightService = flightService;
    }

    @PostMapping("/clear")
    public void clear() {
        flightService.clearRepository();
    }
}
