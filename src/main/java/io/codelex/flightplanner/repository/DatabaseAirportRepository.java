package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatabaseAirportRepository extends JpaRepository<Airport, Long> {
    Airport findByAirport(String airport);
}