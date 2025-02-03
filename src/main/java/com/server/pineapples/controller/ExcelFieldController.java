package com.server.pineapples.controller;

import com.server.pineapples.service.ExcelFieldService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @Operation(
            summary = "Faz o upload de um arquivo XLSX e retorna os dados extraídos",
            description = "Aceita um arquivo Excel (.xlsx) e retorna os dados em formato JSON"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arquivo processado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao processar o arquivo")
    })
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadFileXlsx(
            @RequestParam("file")
            @io.swagger.v3.oas.annotations.Parameter(
                    description = "Arquivo XLSX para upload",
                    content = @Content(mediaType = "application/octet-stream",
                            schema = @Schema(type = "string", format = "binary"))
            ) MultipartFile file
    ) {
        try {
            List<String[]> data = excelFieldService.extractFileXlsx(file);
            return ResponseEntity.ok(data);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Erro ao processar o arquivo: " + e.getMessage());
        }
    }
}
