package io.codelex.flightplanner.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.codelex.flightplanner.entity.Airport;
import io.codelex.flightplanner.entity.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AdminApiControllerTest {

    static ObjectMapper jsonObjectMapper = new ObjectMapper();
    @Autowired
    AdminApiController adminController;
    @Autowired
    TestingApiController testingController;
    @Autowired
    private MockMvc mvc;
    @Value("${spring.security.user.name}")
    private String username;
    @Value("${spring.security.user.password}")
    private String password;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @BeforeEach
    public void clearFlights() {
        testingController.clear();
    }

    @Test
    @DisplayName("Should add and return flight with status code 201")
    public void shouldAddFlight() throws Exception {
        Flight mockFlight = createflight();
        jsonObjectMapper.findAndRegisterModules();

        MockHttpServletRequestBuilder requestBuilder = requestBuilder(mockFlight);
        MvcResult result = mvcResult(requestBuilder, 201);
        String responseContents = result.getResponse().getContentAsString();
        Flight responseFlight = jsonObjectMapper.readValue(responseContents, Flight.class);

        assertNotNull(responseFlight.getId());
        assertEquals(responseFlight.getArrivalTime(), mockFlight.getArrivalTime());
        assertEquals(responseFlight.getDepartureTime(), mockFlight.getDepartureTime());
        assertEquals(responseFlight.getFrom(), mockFlight.getFrom());
        assertEquals(responseFlight.getTo(), mockFlight.getTo());
        assertEquals(responseFlight.getCarrier(), mockFlight.getCarrier());
    }

    @Test
    @DisplayName("Should display 409 when adding the same person")
    public void shouldNotFail() throws Exception {
        Flight mockFlight = createflight();
        jsonObjectMapper.findAndRegisterModules();

        MockHttpServletRequestBuilder requestBuilder = requestBuilder(mockFlight);

        mvcResult(requestBuilder, 201);
        mvcResult(requestBuilder, 409);
    }

    @Test
    @DisplayName("Should display 400 when adding flight with empty/null fields")
    public void shouldNotAddFlightWithEmptyFields() throws Exception {
        Flight mockFlight1 = createflight();
        mockFlight1.setCarrier(null);
        Flight mockFlight2 = null;
        Flight mockFlight3 = createflight();
        mockFlight3.setCarrier("");
        jsonObjectMapper.findAndRegisterModules();

        MockHttpServletRequestBuilder requestBuilder1 = requestBuilder(mockFlight1);
        MockHttpServletRequestBuilder requestBuilder2 = requestBuilder(mockFlight2);
        MockHttpServletRequestBuilder requestBuilder3 = requestBuilder(mockFlight3);

        mvcResult(requestBuilder1, 400);
        mvcResult(requestBuilder2, 400);
        mvcResult(requestBuilder3, 400);
    }

    @Test
    @DisplayName("Should fail on strange dates")
    public void shouldFailOnStrangeDates() throws Exception {
        Flight mockFlight = createflight();
        mockFlight.setArrivalTime(LocalDateTime.parse("1900-01-01 00:00", formatter));
        jsonObjectMapper.findAndRegisterModules();

        MockHttpServletRequestBuilder requestBuilder = requestBuilder(mockFlight);

        mvcResult(requestBuilder, 400);
    }

    private Flight createflight() {
        Airport airportFrom = new Airport("LV", "Riga", "Rix");
        Airport airportTo = new Airport("IT", "Milan", "BGY");
        return new Flight(
                airportFrom,
                airportTo,
                "AirBaltic",
                LocalDateTime.parse("2023-07-13 06:07", formatter) ,
                LocalDateTime.parse("2023-07-16 12:04", formatter)
        );
    }

    private MockHttpServletRequestBuilder requestBuilder(Flight mockFlight) throws Exception {
        return put("/admin-api/flights", mockFlight)
                .with(user(username).password(password))
                .content(jsonObjectMapper.writeValueAsString(mockFlight))
                .contentType(MediaType.APPLICATION_JSON);
    }

    private MvcResult mvcResult(MockHttpServletRequestBuilder requestBuilder, Integer statusCode) throws Exception {
        return mvc.perform(requestBuilder)
                .andExpect(status().is(statusCode))
                .andReturn();
    }
}