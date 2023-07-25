package io.codelex.flightplanner.services;

import io.codelex.flightplanner.entity.AirportEntity;
import io.codelex.flightplanner.entity.FlightEntity;
import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.dto.FlightDTO;
import io.codelex.flightplanner.dto.FlightSearchDTO;
import io.codelex.flightplanner.exceptions.AirportDateMismatchException;
import io.codelex.flightplanner.exceptions.DuplicateEntryException;
import io.codelex.flightplanner.exceptions.EqualAirportsException;
import io.codelex.flightplanner.exceptions.FlightNotFoundByIdException;
import io.codelex.flightplanner.mapper.DatabaseAirportMapper;
import io.codelex.flightplanner.mapper.DatabaseFlightMapper;
import io.codelex.flightplanner.mapper.InMemoryFlightMapper;
import io.codelex.flightplanner.repository.DatabaseAirportRepository;
import io.codelex.flightplanner.repository.DatabaseFlightRepository;
import io.codelex.flightplanner.responses.FlightSearchResponse;

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
    public synchronized Flight addFlight(FlightDTO dto) {
        validateAirportsAndDates(InMemoryFlightMapper.fromDTO(dto));
        FlightEntity entity = DatabaseFlightMapper.fromDTO(dto);
        AirportEntity existingAirportFrom = getExistingAirport(dto.from());
        AirportEntity existingAirportTo = getExistingAirport(dto.to());
        entity.setFrom(existingAirportFrom);
        entity.setTo(existingAirportTo);
        checkForDuplication(entity);
        return DatabaseFlightMapper.toDomain(flightRepository.saveAndFlush(entity));
    }

    @Override
    public void validateAirportsAndDates(Flight flight) {
        validateAirports(flight.getFrom(), flight.getTo());
        if (flight.getDepartureTime().isAfter(flight.getArrivalTime())
                || flight.getDepartureTime().isEqual(flight.getArrivalTime())) {
            throw new AirportDateMismatchException();
        }
    }

    private AirportEntity getExistingAirport(Airport airport) {
        AirportEntity existingAirport = airportRepository.findByAirport(airport.getAirport());
        if (existingAirport == null) {
            return airportRepository.save(
                    new AirportEntity(
                            airport.getCountry(),
                            airport.getCity(),
                            airport.getAirport()
                    )
            );
        }
        return existingAirport;
    }

    private void checkForDuplication(FlightEntity entity) {
        FlightEntity duplicate = flightRepository.findByFromAndToAndDepartureTimeAndArrivalTimeAndCarrier(
                entity.getFrom(),
                entity.getTo(),
                entity.getDepartureTime(),
                entity.getArrivalTime(),
                entity.getCarrier()
        );
        if (duplicate != null) {
            throw new DuplicateEntryException();
        }
    }

    @Override
    public FlightSearchResponse searchForFlight(FlightSearchDTO searchDTO) {
        validateAirports(searchDTO.from(), searchDTO.to());
        List<FlightEntity> matchingFlights = flightRepository.searchForFlight(
                searchDTO.from(), searchDTO.to(), searchDTO.departureDate()
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
        return DatabaseFlightMapper.toDomain(flightRepository.findById(id)
                .orElseThrow(FlightNotFoundByIdException::new)
        );
    }

    @Override
    public List<Airport> getFilteredMatchList(String match) {
        String formattedMatch = match.toLowerCase().trim();
        return flightRepository.findAllByFrom_AirportIgnoreCaseContainingOrFrom_CityIgnoreCaseContainingOrFrom_CountryIgnoreCaseContaining(
                        formattedMatch, formattedMatch, formattedMatch
                ).stream()
                .map(FlightEntity::getFrom)
                .map(DatabaseAirportMapper::fromEntity)
                .toList();
    }

    @Override
    public void clearRepository() {
        flightRepository.deleteAll();
        airportRepository.deleteAll();
    }
}