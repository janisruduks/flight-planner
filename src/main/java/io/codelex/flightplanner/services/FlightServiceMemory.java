package io.codelex.flightplanner.services;

import io.codelex.flightplanner.dtos.FlightDTO;
import io.codelex.flightplanner.dtos.FlightSearchDTO;
import io.codelex.flightplanner.repository.FlightRepository;
import io.codelex.flightplanner.responses.FlightSearchResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public FlightSearchResponse searchForFlight(FlightSearchDTO searchDTO) {
        validateToAndFrom(searchDTO.getFrom(), searchDTO.getTo());
        List<FlightDTO> flights = flightRepository.searchForAirports(searchDTO.getFrom(), searchDTO.getTo(), searchDTO.getBaseDate());
        return new FlightSearchResponse(flights, 0, flights.size());
    }

    private void validateToAndFrom(String from, String to) {
        if (to.equals(from)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public void addFlight(FlightDTO flight) {
        if (flight.getFrom().equals(flight.getTo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        flightRepository.add(flight);
    }

    public void deleteFlightById(String id) {
        flightRepository.deleteById(id);
    }

    public FlightDTO getFlightById(String id) {
        return flightRepository.getFlightById(id);
    }

    public Set<FlightDTO> getAllFlights() {
        return flightRepository.getFlights();
    }

    public void clearRepository() {
        this.flightRepository.deleteAllFlights();
    }
}
