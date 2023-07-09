package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.dtos.FlightDTO;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public class FlightRepositoryInMemoryImpl {

    private final LinkedList<FlightDTO> flights;

    public FlightRepositoryInMemoryImpl() {
        this.flights = new LinkedList<>();
    }

    public List<FlightDTO> getFlights() {
        return this.flights;
    }

    public void deleteAll() {
        this.flights.clear();
    }

    public boolean add(FlightDTO flight) {
        synchronized (flights) {
            return this.flights.add(flight);
        }
    }

    public void deleteFlight(FlightDTO flight) {
        if (flight != null) {
            synchronized (flights) {
                flights.remove(flight);
            }
        }
    }
}
