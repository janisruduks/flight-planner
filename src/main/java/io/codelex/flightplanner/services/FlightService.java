package io.codelex.flightplanner.services;

import io.codelex.flightplanner.dtos.FlightDTO;
import io.codelex.flightplanner.dtos.FlightSearchDTO;
import io.codelex.flightplanner.responses.FlightSearchResponse;

public interface FlightService {
    FlightSearchResponse searchForFlight(FlightSearchDTO searchDTO);

    void addFlight(FlightDTO flightDTO);

    <T> void validateAirports(T from, T to);

    void deleteFlightById(String id);

    FlightDTO getFlightById(String id);

    void clearRepository();
}
