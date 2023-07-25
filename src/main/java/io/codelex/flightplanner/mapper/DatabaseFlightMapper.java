package io.codelex.flightplanner.mapper;

import io.codelex.flightplanner.entity.AirportEntity;
import io.codelex.flightplanner.entity.FlightEntity;
import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.dto.FlightDTO;

public class DatabaseFlightMapper {

    public static FlightEntity fromDTO(FlightDTO dto) {
        return new FlightEntity(
                new AirportEntity(
                        dto.from().getCountry(),
                        dto.from().getCity(),
                        dto.from().getAirport()
                ),
                new AirportEntity(
                        dto.to().getCountry(),
                        dto.to().getCity(),
                        dto.to().getAirport()
                ),
                dto.carrier(),
                dto.departureTime(),
                dto.arrivalTime()
        );
    }

    public static Flight toDomain(FlightEntity entity) {
        Flight flight = new Flight(
                new Airport(
                        entity.getFrom().getCountry(),
                        entity.getFrom().getCity(),
                        entity.getFrom().getAirport()
                ),
                new Airport(
                        entity.getTo().getCountry(),
                        entity.getTo().getCity(),
                        entity.getTo().getAirport()
                ),
                entity.getCarrier(),
                entity.getDepartureTime(),
                entity.getArrivalTime()
        );
        flight.setId(entity.getId());
        return flight;
    }
}