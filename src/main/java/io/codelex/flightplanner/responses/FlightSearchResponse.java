package io.codelex.flightplanner.responses;

import io.codelex.flightplanner.domain.Flight;

import java.util.List;

public record FlightSearchResponse(List<Flight> items, int page, int totalItems) {
}