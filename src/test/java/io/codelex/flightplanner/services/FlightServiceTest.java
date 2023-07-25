package io.codelex.flightplanner.services;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.dto.FlightSearchDTO;
import io.codelex.flightplanner.responses.FlightSearchResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlightServiceTest {

    @Mock
    FlightRepository flightRepository;

    @InjectMocks
    FlightService flightService;

    private List<Flight> createMockFlights() {
        Airport airportFrom1 = new Airport("Latvia", "Riga", "RIX");
        Airport airportTo1 = new Airport("Italy", "Milan", "BGY");
        Airport airportFrom2 = new Airport("England", "London", "LGW");
        Airport airportTo2 = new Airport("Romania", "Bucharest", "OTP");
        Flight flight1 = new Flight(airportFrom1, airportTo1, "AirBaltic", "2023-07-13 06:07", "2023-07-16 12:04");
        Flight flight2 = new Flight(airportFrom2, airportTo2, "AirBaltic", "2023-07-13 06:07", "2023-07-16 12:04");

        return List.of(flight1, flight2);
    }

    @Test
    @DisplayName("Should search for flights")
    void searchForFlights() {
        Flight mockFlightRIX = createMockFlights().get(0);
        Flight mockFlightLGW = createMockFlights().get(1);
        List<Flight> mockFlightList = createMockFlights();
        FlightSearchDTO mockSearchDTO1 = new FlightSearchDTO();
        FlightSearchDTO mockSearchDTO2 = new FlightSearchDTO();
        mockSearchDTO1.setFrom("RIX");
        mockSearchDTO1.setTo("BGY");
        mockSearchDTO1.setBaseDate(LocalDate.parse("2023-07-13"));
        mockSearchDTO2.setFrom("LGW");
        mockSearchDTO2.setTo("OTP");
        mockSearchDTO2.setBaseDate(LocalDate.parse("2023-07-13"));

        when(flightRepository.getFlights()).thenReturn(mockFlightList);
        FlightSearchResponse searchResponse1 = flightService.searchForFlight(mockSearchDTO1);
        FlightSearchResponse searchResponse2 = flightService.searchForFlight(mockSearchDTO2);

        Mockito.verify(flightRepository, times(2)).getFlights();
        assertNotNull(searchResponse1);
        assertEquals(searchResponse1.items(), List.of(mockFlightRIX));
        assertNotNull(searchResponse2);
        assertEquals(searchResponse2.items(), List.of(mockFlightLGW));
    }
}