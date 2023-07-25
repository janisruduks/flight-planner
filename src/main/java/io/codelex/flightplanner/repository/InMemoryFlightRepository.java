package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.domain.Flight;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public class InMemoryFlightRepository {

    private final LinkedList<Flight> flights;

    public InMemoryFlightRepository() {
        this.flights = new LinkedList<>();
    }

    public synchronized List<Flight> getFlights() {
        return this.flights;
    }

    public void deleteAll() {
        this.flights.clear();
    }

    public synchronized boolean add(Flight flight) {
        return this.flights.add(flight);
    }

    public synchronized void deleteFlight(Flight flight) {
        flights.remove(flight);
    }
}