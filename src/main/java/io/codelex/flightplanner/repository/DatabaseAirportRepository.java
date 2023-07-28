package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.entity.Airport;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;

@ConditionalOnProperty(prefix = "flight-planner", name = "store-type", havingValue = "database")
public interface DatabaseAirportRepository extends JpaRepository<Airport, Long> {
    Airport findByAirport(String airport);
}