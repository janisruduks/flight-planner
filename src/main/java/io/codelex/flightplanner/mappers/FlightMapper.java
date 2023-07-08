package io.codelex.flightplanner.mappers;

import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.dtos.FlightDTO;
import io.codelex.flightplanner.exceptions.AirportTimeMismatchException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

@Component
public class FlightMapper {

    public FlightDTO toDTO(Flight flight) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            FlightDTO flightDTO = new FlightDTO(
                    generateUniqueId(),
                    flight.getFrom(),
                    flight.getTo(),
                    flight.getCarrier(),
                    LocalDateTime.parse(flight.getDepartureTime(), formatter),
                    LocalDateTime.parse(flight.getArrivalTime(), formatter)
            );
            validDateCheck(flightDTO.getDepartureTime(), flightDTO.getArrivalTime());
            return flightDTO;
        } catch (DateTimeParseException e) {
            throw new AirportTimeMismatchException();
        }
    }

    public String generateUniqueId() {
        return UUID.randomUUID().toString();
    }

    private void validDateCheck(LocalDateTime departure, LocalDateTime arrival) {
        if (departure.isAfter(arrival) || departure.isEqual(arrival)) {
            throw new AirportTimeMismatchException();
        }
    }
}
