package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.repository.InMemoryFlightRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;


import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TestingApiControllerTest {

    @Autowired
    TestingApiController testingController;
    @Autowired
    InMemoryFlightRepository flightRepository;
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Should clean repository fully")
    void clear() throws Exception {
        Airport airportFrom = new Airport("LV", "Riga", "Rix");
        Airport airportTo = new Airport("IT", "Milan", "BGY");
        LocalDateTime departureTime = LocalDateTime.of("2023-07-13 06:07");
        String arrivalTime = "2023-07-16 12:04";
        Flight flight = new Flight(airportFrom, airportTo, "AirBaltic", departureTime, arrivalTime);
        Integer flightCountBefore = flightRepository.getFlights().size();
        flightRepository.add(flight);

        MockHttpServletRequestBuilder requestBuilder = post("/testing-api/clear")
                .contentType(MediaType.APPLICATION_JSON);
        mvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andReturn();
        Integer flightCountAfter = flightRepository.getFlights().size();

        assertEquals(flightCountBefore, flightCountAfter);
    }
}