package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.dtos.FlightDTO;

import java.util.HashSet;

// yeah I know, won't work later.
public interface FlightRepository {

    HashSet<FlightDTO> getFlights();

    void deleteAll();

    boolean add(FlightDTO flightDTO);

    void deleteById(String id);

}
