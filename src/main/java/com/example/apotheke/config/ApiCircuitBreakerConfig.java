package com.example.apotheke.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiCircuitBreakerConfig {

    @Bean
    public CircuitBreaker circuitBreaker() {
        CircuitBreakerConfig externalServiceFooConfig = CircuitBreakerConfig.custom()
            .slidingWindowSize(2)
            .slidingWindowType(SlidingWindowType.COUNT_BASED)
            .waitDurationInOpenState(Duration.ofSeconds(15))
            .minimumNumberOfCalls(1)
            .failureRateThreshold(50.0f)
            .build();
        return CircuitBreaker.of("topGitHubRepositories", externalServiceFooConfig);
    }
}
