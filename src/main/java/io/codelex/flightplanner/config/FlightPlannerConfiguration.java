package io.codelex.flightplanner.config;

import io.codelex.flightplanner.repository.DatabaseAirportRepository;
import io.codelex.flightplanner.repository.DatabaseFlightRepository;
import io.codelex.flightplanner.repository.InMemoryFlightRepository;
import io.codelex.flightplanner.services.DatabaseFlightServiceImpl;
import io.codelex.flightplanner.services.FlightService;
import io.codelex.flightplanner.services.InMemoryFlightServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlightPlannerConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "flight-planner", name = "store-type", havingValue = "in-memory")
    public FlightService getInMemoryService(InMemoryFlightRepository repository) {
        return new InMemoryFlightServiceImpl(repository);
    }

    @Bean
    @ConditionalOnProperty(prefix = "flight-planner", name = "store-type", havingValue = "database")
    public FlightService getDatabaseService(
            DatabaseFlightRepository flightRepository,
            DatabaseAirportRepository airportRepository
    ) {
        return new DatabaseFlightServiceImpl(flightRepository, airportRepository);
    }
}
