package io.codelex.flightplanner.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public class FlightSearchDTO {

    @NotBlank
    private String from;
    @NotBlank
    private String to;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate departureDate;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public LocalDate getBaseDate() {
        return departureDate;
    }

    public void setBaseDate(LocalDate baseDate) {
        this.departureDate = baseDate;
    }
}