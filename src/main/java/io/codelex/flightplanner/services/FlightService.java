package io.codelex.flightplanner.services;

import io.codelex.flightplanner.dto.FlightSearchDTO;
import io.codelex.flightplanner.entity.Airport;
import io.codelex.flightplanner.entity.Flight;
import io.codelex.flightplanner.response.FlightSearchResponse;

import java.util.List;

public interface FlightService {
    Flight addFlight(Flight dto);

    FlightSearchResponse searchForFlight(FlightSearchDTO searchDTO);

    void validateAirportsAndDates(Flight flight);

    void validateAirports(Object airportFrom, Object airportTo);

    void deleteFlightById(Long id);

    Flight getFlightById(Long id);

    List<Airport> getFilteredMatchList(String match);

    void clearRepository();
}
