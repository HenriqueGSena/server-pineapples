package com.server.pineapples.config;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ApiServiceConfig {

    private final WebClient webClient;

    public ApiServiceConfig(ExternalApiConfig config) {
        this.webClient = WebClient.builder()
                .baseUrl(config.getBaseUrl())
                .defaultHeader("Authorization", "Bearer " + config.getKey())
                .build();
    }

//    public String getDadosExternos() {
//        return webClient.get()
//                .uri("/dados")
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();
//    }
}
