package com.server.pineapples.service;

import com.server.pineapples.config.ApiServiceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProcessJsonService {

    private final ApiServiceConfig apiService;
    private final ExcelFieldService excelFieldService;

    public ProcessJsonService(ApiServiceConfig apiService, ExcelFieldService excelFieldService) {
        this.apiService = apiService;
        this.excelFieldService = excelFieldService;
    }

    public void processData(String bookingId, MultipartFile file) {
        findPaymentById(bookingId, file)
                .doOnTerminate(() -> System.out.println("Processamento finalizado"))
                .subscribe(response -> {
                    // Aqui você pode trabalhar com a resposta ou logar qualquer informação adicional
                    System.out.println("Resposta: " + response);
                });
    }

    public Mono<Map<String, Object>> findPaymentById(String bookingId, MultipartFile file) {
        return Mono.defer(() -> {
            try {
                List<Map<String, String>> extractedData = excelFieldService.extractFileXlsx(file);

                Optional<Map<String, String>> matchingRecord = extractedData.stream()
                        .filter(row -> bookingId.equals(row.get("Booking_Id")))
                        .findFirst();

                if (matchingRecord.isPresent()) {
                    Map<String, String> rowData = matchingRecord.get();
                    return apiService.getBookingById(bookingId)
                            .doOnNext(apiResponse -> {
                                // Exibe a resposta da API no console
                                System.out.println("API Response: " + apiResponse);
                            })
                            .doOnTerminate(() -> {
                                // Exibe uma mensagem após o processo ser concluído
                                System.out.println("API Request Completed");
                            })
                            .map(apiResponse -> {
                                Map<String, Object> response = new HashMap<>();
                                response.put("ApiData", apiResponse);
                                return response;
                            })
                            .defaultIfEmpty(new HashMap<>());
                }
                return Mono.just(new HashMap<String, Object>());
            } catch (IOException e) {
                e.printStackTrace();
                return Mono.just(new HashMap<String, Object>());
            }
        }).doOnTerminate(() -> {
            // Esta parte será chamada assim que o Mono terminar (mesmo que tenha dado erro)
            System.out.println("Process completed!");
        });
    }
}
