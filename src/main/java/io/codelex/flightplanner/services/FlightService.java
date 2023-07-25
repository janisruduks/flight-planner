package io.codelex.flightplanner.services;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.dto.FlightDTO;
import io.codelex.flightplanner.dto.FlightSearchDTO;
import io.codelex.flightplanner.responses.FlightSearchResponse;

import java.util.List;

public interface FlightService {
    Flight addFlight(FlightDTO dto);
    FlightSearchResponse searchForFlight(FlightSearchDTO searchDTO);
    void validateAirportsAndDates(Flight flight);
    void validateAirports(Object airportFrom, Object airportTo);
    void deleteFlightById(Long id);
    Flight getFlightById(Long id);
    List<Airport> getFilteredMatchList(String match);
    void clearRepository();
}
