package io.codelex.flightplanner.services;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.dto.FlightDTO;
import io.codelex.flightplanner.dto.FlightSearchDTO;
import io.codelex.flightplanner.exceptions.AirportDateMismatchException;
import io.codelex.flightplanner.exceptions.DuplicateEntryException;
import io.codelex.flightplanner.exceptions.EqualAirportsException;
import io.codelex.flightplanner.exceptions.FlightNotFoundByIdException;
import io.codelex.flightplanner.mapper.InMemoryFlightMapper;
import io.codelex.flightplanner.repository.InMemoryFlightRepository;
import io.codelex.flightplanner.responses.FlightSearchResponse;

import java.time.LocalDate;
import java.util.List;

public class InMemoryFlightServiceImpl implements FlightService {

    private final InMemoryFlightRepository repository;

    public InMemoryFlightServiceImpl(InMemoryFlightRepository flightRepository) {
        this.repository = flightRepository;
    }

    @Override
    public Flight addFlight(FlightDTO dto) {
        Flight flight = InMemoryFlightMapper.fromDTO(dto);
        validateAirportsAndDates(flight);
        flight.setId(generateId(1L));
        if (repository.getFlights().contains(flight)) {
            throw new DuplicateEntryException();
        }
        repository.add(flight);
        return flight;
    }

    private synchronized Long generateId(Long base) {
        List<Flight> flights = repository.getFlights();
        Long id = flights.size() + base;
        if (flights.stream().anyMatch(flight -> flight.getId().equals(id))) {
            return generateId(++base);
        }
        return id;
    }

    @Override
    public void validateAirportsAndDates(Flight flight) {
        validateAirports(flight.getFrom(), flight.getTo());
        if (flight.getDepartureTime().isAfter(flight.getArrivalTime())
                || flight.getDepartureTime().isEqual(flight.getArrivalTime())) {
            throw new AirportDateMismatchException();
        }
    }

    @Override
    public FlightSearchResponse searchForFlight(FlightSearchDTO searchDTO) {
        validateAirports(searchDTO.from(), searchDTO.to());
        List<Flight> flights = searchForAirports(searchDTO.from(), searchDTO.to(), searchDTO.departureDate());
        return new FlightSearchResponse(flights, 0, flights.size());
    }


    @Override
    public void validateAirports(Object airportFrom, Object airportTo) {
        if (airportTo.equals(airportFrom)) {
            throw new EqualAirportsException();
        }
    }

    public List<Flight> searchForAirports(String from, String to, LocalDate departureDate) {
        return repository.getFlights().stream()
                .filter(flight -> flight.getDepartureTime().toLocalDate().equals(departureDate))
                .filter(flight -> flight.getFrom().getAirport().equals(from))
                .filter(flight -> flight.getTo().getAirport().equals(to))
                .toList();
    }

    @Override
    public synchronized void deleteFlightById(Long id) {
        try {
            repository.deleteFlight(getFlightById(id));
        } catch (FlightNotFoundByIdException ignored) { }
    }

    @Override
    public Flight getFlightById(Long id) {
        return repository.getFlights().stream()
                .filter(flight -> flight.getId().equals(id))
                .findFirst()
                .orElseThrow(FlightNotFoundByIdException::new);
    }

    @Override
    public List<Airport> getFilteredMatchList(String match) {
        String formattedMatch = match.toLowerCase().trim();
        return repository.getFlights().stream()
                .map(Flight::getFrom)
                .filter(from -> from.getAirport().toLowerCase().contains(formattedMatch)
                        || from.getCity().toLowerCase().contains(formattedMatch)
                        || from.getCountry().toLowerCase().contains(formattedMatch)
                )
                .toList();
    }

    @Override
    public void clearRepository() {
        this.repository.deleteAll();
    }
}