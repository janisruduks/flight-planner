package io.codelex.flightplanner.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "flight")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "from_id")
    @NotNull(message = "Airport from cannot be null")
    @Valid
    private Airport from;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "to_id")
    @NotNull(message = "Airport to cannot be null")
    @Valid
    private Airport to;
    @Column(name = "carrier_name")
    @NotBlank(message = "Airline name is mandatory")
    private String carrier;
    @Column(name = "departure_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @NotNull(message = "Departure time cannot be null")
    private LocalDateTime departureTime;
    @Column(name = "arrival_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @NotNull(message = "Arrival time cannot be null")
    private LocalDateTime arrivalTime;

    public Flight() {
    }

    public Flight(
            Airport from,
            Airport to,
            String carrier,
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
            LocalDateTime departureTime,
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
            LocalDateTime arrivalTime
    ) {
        this.from = from;
        this.to = to;
        this.carrier = carrier;

        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Airport getTo() {
        return to;
    }

    public void setTo(Airport to) {
        this.to = to;
    }

    public Airport getFrom() {
        return from;
    }

    public void setFrom(Airport from) {
        this.from = from;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight that = (Flight) o;
        return Objects.equals(from, that.from) && Objects.equals(to, that.to) && Objects.equals(carrier, that.carrier) && Objects.equals(departureTime, that.departureTime) && Objects.equals(arrivalTime, that.arrivalTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, carrier, departureTime, arrivalTime);
    }
}