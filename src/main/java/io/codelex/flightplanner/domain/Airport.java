package io.codelex.flightplanner.domain;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class Airport {

    @NotBlank(message = "Country is mandatory")
    private String country;
    @NotBlank(message = "City is mandatory")
    private String city;
    @NotBlank(message = "Airport location identifier is mandatory")
    private String airport;

    public Airport(String country, String city, String airport) {
        this.country = country;
        this.city = city;
        this.airport = airport;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(@NotBlank String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(@NotBlank String city) {
        this.city = city;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(@NotBlank String airport) {
        this.airport = airport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport1 = (Airport) o;
        return country.equalsIgnoreCase(airport1.country)
                && city.equalsIgnoreCase(airport1.city)
                && airport.strip().equalsIgnoreCase(airport1.airport.strip());
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, airport);
    }
}