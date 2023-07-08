package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.dtos.FlightDTO;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class FlightRepositoryInMemoryImpl {

    private final HashSet<FlightDTO> flights;

    public FlightRepositoryInMemoryImpl() {
        this.flights = new HashSet<>();
    }

    public HashSet<FlightDTO> getFlights() {
        return this.flights;
    }

    public void deleteAll() {
        this.flights.clear();
    }

    public boolean add(FlightDTO flight) {
        return this.flights.add(flight);
    }

    public synchronized void deleteById(String id) {
        Set<FlightDTO> flightsCopy = new HashSet<>(flights);
        FlightDTO flightDTO = flightsCopy.stream()
                .filter(flight -> flight.getId().equals(id))
                .findFirst()
                .orElse(null);
        flights.remove(flightDTO);
    }
}
