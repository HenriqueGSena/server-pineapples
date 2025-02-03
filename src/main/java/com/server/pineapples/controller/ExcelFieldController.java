package com.server.pineapples.controller;

import com.server.pineapples.service.ExcelFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/field")
public class ExcelFieldController {

    @Autowired
    private final ExcelFieldService excelFieldService;

    public ExcelFieldController(ExcelFieldService excelFieldService) {
        this.excelFieldService = excelFieldService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFileXlx(@RequestParam("file")MultipartFile file) {
        try {
            List<String[]> data = excelFieldService.extractFileXlsx(file);
            return ResponseEntity.ok(data);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Erro ao processar o arquivo: " + e.getMessage());
        }
    }
}
