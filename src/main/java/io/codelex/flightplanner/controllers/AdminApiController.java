package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.dtos.FlightDTO;
import io.codelex.flightplanner.mappers.FlightMapper;
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

    //TODO fix dates
    private final FlightService flightService;
    private final FlightMapper mapper;

    public AdminApiController(FlightService flightService, FlightMapper mapper) {
        this.flightService = flightService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightDTO> getFlightById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(flightService.getFlightById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ValidResponse> deleteFlight(@PathVariable String id) {
            this.flightService.deleteFlightById(id);
            return ResponseEntity.ok(new ValidResponse("Flight deleted successfully"));
    }

    @PutMapping()
    public ResponseEntity<FlightDTO> addFlight(@Valid @RequestBody Flight flight) {
            FlightDTO flightDTO = mapper.toDTO(flight);
            this.flightService.addFlight(flightDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(flightDTO);
    }
}
