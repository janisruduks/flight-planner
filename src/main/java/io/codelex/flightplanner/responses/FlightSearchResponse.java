package io.codelex.flightplanner.responses;

import io.codelex.flightplanner.dtos.FlightDTO;

import java.util.List;

public record FlightSearchResponse(List<FlightDTO> items, int page, int totalItems) {}