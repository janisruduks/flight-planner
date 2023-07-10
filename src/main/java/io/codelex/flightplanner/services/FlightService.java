package io.codelex.flightplanner.services;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.dtos.FlightSearchDTO;
import io.codelex.flightplanner.exceptions.AirportTimeMismatchException;
import io.codelex.flightplanner.exceptions.DuplicateEntryException;
import io.codelex.flightplanner.exceptions.EqualAirportsException;
import io.codelex.flightplanner.exceptions.FlightNotFoundByIdException;
import io.codelex.flightplanner.repository.FlightRepository;
import io.codelex.flightplanner.responses.FlightSearchResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public FlightSearchResponse searchForFlight(FlightSearchDTO searchDTO) {
        validateAirports(searchDTO.getFrom(), searchDTO.getTo());
        List<Flight> flights = searchForAirports(searchDTO.getFrom(), searchDTO.getTo(), searchDTO.getBaseDate());
        return new FlightSearchResponse(flights, 0, flights.size());
    }

    public List<Flight> searchForAirports(String from, String to, LocalDate departureDate) {
        return flightRepository.getFlights().stream()
                .filter(flightDTO -> flightDTO.getDepartureTime().toLocalDate().equals(departureDate))
                .filter(flightDTO -> flightDTO.getFrom().getAirport().equals(from))
                .filter(flightDTO -> flightDTO.getTo().getAirport().equals(to))
                .toList();
    }

    public void addFlight(Flight flight) {
        validDateCheck(flight.getDepartureTime(), flight.getArrivalTime());
        validateAirports(flight.getFrom(), flight.getTo());
        flight.setId(generateUniqueId());
        if (flightRepository.getFlights().contains(flight)) {
            throw new DuplicateEntryException();
        }
        flightRepository.add(flight);
    }

    private void validDateCheck(LocalDateTime departure, LocalDateTime arrival) {
        if (departure.isAfter(arrival) || departure.isEqual(arrival)) {
            throw new AirportTimeMismatchException();
        }
    }

    public <T> void validateAirports(T airportFrom, T airportTo) {
        if (airportTo.equals(airportFrom)) {
            throw new EqualAirportsException();
        }
    }

    public String generateUniqueId() {
        return UUID.randomUUID().toString();
    }

    public void deleteFlightById(String id) {
        Flight flightToRemove;
        synchronized (flightRepository.getFlights()) {
            flightToRemove = flightRepository.getFlights().stream()
                    .filter(flight -> flight.getId().equals(id))
                    .findFirst()
                    .orElse(null);
            flightRepository.deleteFlight(flightToRemove);
        }
    }

    public Flight getFlightById(String id) {
        return flightRepository.getFlights().stream()
                .filter(flight -> flight.getId().equals(id))
                .findFirst()
                .orElseThrow(FlightNotFoundByIdException::new);
    }

    public List<Airport> getFilteredMatchList(String match) {
        return flightRepository.getFlights().stream()
                .map(Flight::getFrom)
                .filter(from ->
                        from.getAirport().toLowerCase().contains(match)
                                || from.getCity().toLowerCase().contains(match)
                                || from.getCountry().toLowerCase().contains(match)
                )
                .map(from -> new Airport(from.getCountry(), from.getCity(), from.getAirport()))
                .toList();
    }

    public void clearRepository() {
        this.flightRepository.deleteAll();
    }
}
