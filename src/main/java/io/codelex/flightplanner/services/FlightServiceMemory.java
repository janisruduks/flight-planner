package io.codelex.flightplanner.services;

import io.codelex.flightplanner.dtos.AirportDTO;
import io.codelex.flightplanner.dtos.FlightDTO;
import io.codelex.flightplanner.dtos.FlightSearchDTO;
import io.codelex.flightplanner.exceptions.DuplicateEntryException;
import io.codelex.flightplanner.exceptions.EqualAirportsException;
import io.codelex.flightplanner.exceptions.FlightNotFoundByIdException;
import io.codelex.flightplanner.repository.FlightRepositoryInMemoryImpl;
import io.codelex.flightplanner.responses.FlightSearchResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FlightServiceMemory {

    private final FlightRepositoryInMemoryImpl flightRepository;

    public FlightServiceMemory(FlightRepositoryInMemoryImpl flightRepository) {
        this.flightRepository = flightRepository;
    }

    public FlightSearchResponse searchForFlight(FlightSearchDTO searchDTO) {
        validateAirports(searchDTO.getFrom(), searchDTO.getTo());
        List<FlightDTO> flights = searchForAirports(searchDTO.getFrom(), searchDTO.getTo(), searchDTO.getBaseDate());
        return new FlightSearchResponse(flights, 0, flights.size());
    }

    public List<FlightDTO> searchForAirports(String from, String to, LocalDate departureDate) {
        return flightRepository.getFlights().stream()
                .filter(flightDTO -> flightDTO.getDepartureTime().toLocalDate().equals(departureDate))
                .filter(flightDTO -> flightDTO.getFrom().getAirport().equals(from))
                .filter(flightDTO -> flightDTO.getTo().getAirport().equals(to))
                .toList();
    }

    public void addFlight(FlightDTO flight) {
        validateAirports(flight.getFrom(), flight.getTo());
        if (flightRepository.getFlights().contains(flight)) {
            throw new DuplicateEntryException();
        }
        flightRepository.add(flight);
    }

    public <T> void validateAirports(T airportFrom, T airportTo) {
        if (airportTo.equals(airportFrom)) {
            throw new EqualAirportsException();
        }
    }

    public void deleteFlightById(String id) {
        FlightDTO flightToRemove;
        synchronized (flightRepository.getFlights()) {
            flightToRemove = flightRepository.getFlights().stream()
                    .filter(flight -> flight.getId().equals(id))
                    .findFirst()
                    .orElse(null);
            flightRepository.deleteFlight(flightToRemove);
        }
    }

    public FlightDTO getFlightById(String id) {
        return flightRepository.getFlights().stream()
                .filter(flight -> flight.getId().equals(id))
                .findFirst()
                .orElseThrow(FlightNotFoundByIdException::new);
    }

    public List<AirportDTO> getFilteredMatchList(String match) {
        return flightRepository.getFlights().stream()
                .map(FlightDTO::getFrom)
                .filter(from ->
                        from.getAirport().toLowerCase().contains(match)
                                || from.getCity().toLowerCase().contains(match)
                                || from.getCountry().toLowerCase().contains(match)
                )
                .map(from -> new AirportDTO(from.getCountry(), from.getCity(), from.getAirport()))
                .toList();
    }

    public void clearRepository() {
        this.flightRepository.deleteAll();
    }
}
