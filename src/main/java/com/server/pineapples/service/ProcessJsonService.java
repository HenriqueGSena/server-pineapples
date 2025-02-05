package com.server.pineapples.service;

import com.server.pineapples.config.ApiServiceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessJsonService {

    @Autowired
    private final ExcelFieldService excelFieldService;

    @Autowired
    private final ApiServiceConfig apiServiceConfig;

    public ProcessJsonService(ExcelFieldService excelFieldService, ApiServiceConfig apiServiceConfig) {
        this.excelFieldService = excelFieldService;
        this.apiServiceConfig = apiServiceConfig;
    }
}
