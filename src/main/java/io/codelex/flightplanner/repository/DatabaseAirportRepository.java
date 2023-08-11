package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DatabaseAirportRepository extends JpaRepository<Airport, Long> {

    Airport findByAirport(String airport);

    @Query(value =
            """
                                select a
                                from Airport a
                                where lower(a.country) like %:match%
                                or lower(a.airport) like %:match%
                                or lower(a.city) like %:match%
                    """
    )
    List<Airport> searchForMatchingAirports(String match);
}