package io.codelex.flightplanner.config;

import io.codelex.flightplanner.repository.FlightRepositoryInMemoryImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "flight-planner", name = "storage-type", havingValue = "in-memory")
    public FlightRepositoryInMemoryImpl getInMemoryRepository() {
        return new FlightRepositoryInMemoryImpl();
    }

}
