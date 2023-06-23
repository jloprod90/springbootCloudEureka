package com.formacionbanca.springbootserviceitem;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class AppConfig {

    @Bean(name = "RestClient")
    @LoadBalanced
    public RestTemplate registryRestTemplate() {
        return new RestTemplate();
    }


    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> {
            return new Resilience4JConfigBuilder(id)
                    .circuitBreakerConfig(CircuitBreakerConfig.custom()
                            .slidingWindowSize(10) // num de request
                            .failureRateThreshold(50) // num de fallos
                            .waitDurationInOpenState(Duration.ofSeconds(10L)) //tiempo de espera abierto
                            .permittedNumberOfCallsInHalfOpenState(5) // numero de llamadas permitidas en semiAbierto
                            .build())
                    .timeLimiterConfig(TimeLimiterConfig.ofDefaults())
                    .build();
        });
    }

}
