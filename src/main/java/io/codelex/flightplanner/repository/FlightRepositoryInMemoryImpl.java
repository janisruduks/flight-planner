package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.dtos.FlightDTO;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class FlightRepository {

    private final HashSet<FlightDTO> flights;

    public FlightRepository() {
        this.flights = new HashSet<>();
    }

    public HashSet<FlightDTO> getFlights() {
        return this.flights;
    }

    // could try to pass copy to certain methods
    public Set<FlightDTO> getFlightsCopy() {
        return new HashSet<>(flights);
    }

    public void deleteAllFlights() {
        this.flights.clear();
    }

    public boolean add(FlightDTO flight) {
        return this.flights.add(flight);
    }

    public synchronized boolean deleteById(String id) {
        Set<FlightDTO> flightsCopy = new HashSet<>(flights);
        FlightDTO flightDTO = flightsCopy.stream()
                .filter(flight -> flight.getId().equals(id))
                .findFirst()
                .orElse(null);
        return flights.remove(flightDTO);
    }
}
