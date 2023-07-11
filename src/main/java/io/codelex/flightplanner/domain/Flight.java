package io.codelex.flightplanner.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Flight {

    private String id;
    @NotNull(message = "Airport from cannot be null")
    @Valid
    private Airport from;
    @NotNull(message = "Airport to cannot be null")
    @Valid
    private Airport to;
    @NotBlank(message = "Airline name is mandatory")
    private String carrier;
    @NotBlank(message = "Departure time is mandatory")
    private String departureTime;
    @NotBlank(message = "Arrival time is mandatory")
    private String arrivalTime;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Flight(Airport from, Airport to, String carrier, String departureTime, String arrivalTime) {
        this.from = from;
        this.to = to;
        this.carrier = carrier;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Airport getFrom() {
        return from;
    }

    public void setFrom(Airport from) {
        this.from = from;
    }

    public Airport getTo() {
        return to;
    }

    public void setTo(Airport to) {
        this.to = to;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    public LocalDateTime getDepartureTime() {
        return LocalDateTime.parse(departureTime, formatter);
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    public LocalDateTime getArrivalTime() {
        return LocalDateTime.parse(arrivalTime, formatter);
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(from, flight.from)
                && Objects.equals(to, flight.to)
                && Objects.equals(carrier, flight.carrier)
                && Objects.equals(departureTime, flight.departureTime)
                && Objects.equals(arrivalTime, flight.arrivalTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(to, carrier, departureTime, arrivalTime);
    }
}