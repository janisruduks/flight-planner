package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.entity.Airport;
import io.codelex.flightplanner.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DatabaseFlightRepository extends JpaRepository<Flight, Long> {

    @Query("select f from Flight f where f.from.airport = ?1 and f.to.airport = ?2 and DATE(f.departureTime) = ?3")
    List<Flight> searchForFlight(
            String from, String to, LocalDate departure
    );

    Flight findByFromAndToAndDepartureTimeAndArrivalTimeAndCarrier(
            Airport from,
            Airport to,
            LocalDateTime departure,
            LocalDateTime arrival,
            String carrier
    );

    @Query(value =
            """
                        select f
                        from Flight f
                        where lower(f.from.airport) like %:fromAirport%
                        or lower(f.from.city) like %:fromCity%
                        or lower(f.from.country) like %:fromCountry%
            """
    )
    List<Flight> queryForFlights(String fromAirport, String fromCity, String fromCountry);
}