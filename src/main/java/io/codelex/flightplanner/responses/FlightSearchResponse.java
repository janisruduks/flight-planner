package io.codelex.flightplanner.responses;

import java.util.List;

public record FlightSearchResponse(List<?> items, int page, int totalItems) { }