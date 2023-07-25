package io.codelex.flightplanner.mapper;

import io.codelex.flightplanner.entity.AirportEntity;
import io.codelex.flightplanner.domain.Airport;

public class DatabaseAirportMapper {

    public static Airport fromEntity(AirportEntity entity) {
        return new Airport(entity.getCountry(), entity.getCity(), entity.getAirport());
    }
}