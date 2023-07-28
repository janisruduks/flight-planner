package io.codelex.flightplanner.response;

import java.util.List;

public record FlightSearchResponse(List<?> items, int page, int totalItems) { }