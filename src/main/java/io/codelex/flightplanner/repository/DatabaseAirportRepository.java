package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.entity.AirportEntity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;

@ConditionalOnProperty(prefix = "flight-planner", name = "store-type", havingValue = "database")
public interface DatabaseAirportRepository extends JpaRepository<AirportEntity, Long> {
    AirportEntity findByAirport(String airport);
}