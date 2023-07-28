package io.codelex.flightplanner.services;

import io.codelex.flightplanner.entity.Airport;
import io.codelex.flightplanner.entity.Flight;
import io.codelex.flightplanner.dto.FlightSearchDTO;
import io.codelex.flightplanner.exceptions.AirportDateMismatchException;
import io.codelex.flightplanner.exceptions.DuplicateEntryException;
import io.codelex.flightplanner.exceptions.EqualAirportsException;
import io.codelex.flightplanner.exceptions.FlightNotFoundByIdException;
import io.codelex.flightplanner.repository.DatabaseAirportRepository;
import io.codelex.flightplanner.repository.DatabaseFlightRepository;
import io.codelex.flightplanner.response.FlightSearchResponse;

import java.util.List;

public class DatabaseFlightServiceImpl implements FlightService {

    private final DatabaseFlightRepository flightRepository;
    private final DatabaseAirportRepository airportRepository;

    public DatabaseFlightServiceImpl(
            DatabaseFlightRepository flightRepository,
            DatabaseAirportRepository airportRepository
    ) {
        this.flightRepository = flightRepository;
        this.airportRepository = airportRepository;
    }

    @Override
    public synchronized Flight addFlight(Flight flight) {
        validateAirportsAndDates(flight);
        flight.setFrom(getExistingAirport(flight.getFrom()));
        flight.setTo(getExistingAirport(flight.getTo()));
        checkForDuplication(flight);
        return flightRepository.saveAndFlush(flight);
    }

    @Override
    public void validateAirportsAndDates(Flight flight) {
        validateAirports(flight.getFrom(), flight.getTo());
        if (flight.getDepartureTime().isAfter(flight.getArrivalTime())
                || flight.getDepartureTime().isEqual(flight.getArrivalTime())) {
            throw new AirportDateMismatchException();
        }
    }

    private Airport getExistingAirport(Airport airport) {
        Airport existingAirport = airportRepository.findByAirport(airport.getAirport());
        if (existingAirport == null) {
            return airportRepository.save(
                    new Airport(airport.getCountry(), airport.getCity(), airport.getAirport())
            );
        }
        return existingAirport;
    }

    private void checkForDuplication(Flight flight) {
        Flight duplicate = flightRepository.findByFromAndToAndDepartureTimeAndArrivalTimeAndCarrier(
                flight.getFrom(),
                flight.getTo(),
                flight.getDepartureTime(),
                flight.getArrivalTime(),
                flight.getCarrier()
        );
        if (duplicate != null) {
            throw new DuplicateEntryException();
        }
    }

    @Override
    public FlightSearchResponse searchForFlight(FlightSearchDTO dto) {
        validateAirports(dto.from(), dto.to());
        List<Flight> matchingFlights = flightRepository.searchForFlight(
                dto.from(), dto.to(), dto.departureDate()
        );
        return new FlightSearchResponse(matchingFlights, 0, matchingFlights.size());
    }

    @Override
    public void validateAirports(Object airportFrom, Object airportTo) {
        if (airportFrom.equals(airportTo)) {
            throw new EqualAirportsException();
        }
    }

    @Override
    public void deleteFlightById(Long id) {
        flightRepository.deleteById(id);
    }

    @Override
    public Flight getFlightById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(FlightNotFoundByIdException::new);
    }

    @Override
    public List<Airport> getFilteredMatchList(String match) {
        String formattedMatch = match.toLowerCase().trim();
        return flightRepository.findAllByFrom_AirportIgnoreCaseContainingOrFrom_CityIgnoreCaseContainingOrFrom_CountryIgnoreCaseContaining(
                        formattedMatch, formattedMatch, formattedMatch
                ).stream()
                .map(Flight::getFrom)
                .toList();
    }

    @Override
    public void clearRepository() {
        flightRepository.deleteAll();
        airportRepository.deleteAll();
    }
}