package com.server.pineapples.config;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class ApiServiceConfig {

    private final WebClient webClient;

    public ApiServiceConfig(ExternalApiConfig config) {
        this.webClient = WebClient.builder()
                .baseUrl(config.getBaseUrl())
                .defaultHeader("Authorization", "Bearer " + config.getKey())
                .build();
    }

    public Mono<Map<String, Object>> getBookingById(String bookingId) {
        return webClient.get()
                .uri("/bookings/{id}", bookingId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .onErrorResume(e -> {
                    e.printStackTrace();
                    return Mono.just(new HashMap<String, Object>());
                });
    }
}
