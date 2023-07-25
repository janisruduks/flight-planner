package io.codelex.flightplanner.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record FlightSearchDTO(
        @NotBlank
        String from,
        @NotBlank
        String to,
        @JsonFormat(pattern = "yyyy-MM-dd")
        @NotNull
        LocalDate departureDate
) { }