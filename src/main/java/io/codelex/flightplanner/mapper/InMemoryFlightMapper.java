package io.codelex.flightplanner.mapper;

import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.dto.FlightDTO;

public class InMemoryFlightMapper {

    public static Flight fromDTO(FlightDTO dto) {
        return new Flight(
                dto.from(),
                dto.to(),
                dto.carrier(),
                dto.departureTime(),
                dto.arrivalTime()
        );
    }
}