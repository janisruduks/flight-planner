package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.entity.AirportEntity;
import io.codelex.flightplanner.entity.FlightEntity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@ConditionalOnProperty(prefix = "flight-planner", name = "store-type", havingValue = "database")
public interface DatabaseFlightRepository extends JpaRepository<FlightEntity, Long> {
    @Query("select f from FlightEntity f where f.from.airport = ?1 and f.to.airport = ?2 and DATE(f.departureTime) = ?3")
    List<FlightEntity> searchForFlight(
            String from, String to, LocalDate departure
    );
    FlightEntity findByFromAndToAndDepartureTimeAndArrivalTimeAndCarrier(AirportEntity from, AirportEntity to, LocalDateTime departure, LocalDateTime arrival, String carrier);
    List<FlightEntity> findAllByFrom_AirportIgnoreCaseContainingOrFrom_CityIgnoreCaseContainingOrFrom_CountryIgnoreCaseContaining(
            String fromAirport,
            String fromCity,
            String fromCountry
    );
}