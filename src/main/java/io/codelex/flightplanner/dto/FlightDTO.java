package io.codelex.flightplanner.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.codelex.flightplanner.domain.Airport;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record FlightDTO(
        @NotNull(message = "Airport from cannot be null")
        @Valid
        Airport from,
        @NotNull(message = "Airport to cannot be null")
        @Valid
        Airport to,
        @NotBlank(message = "Airline name is mandatory")
        String carrier,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        @NotNull(message = "Departure time cannot be null")
        LocalDateTime departureTime,
        @NotNull(message = "Arrival time cannot be null")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime arrivalTime
        ) { }