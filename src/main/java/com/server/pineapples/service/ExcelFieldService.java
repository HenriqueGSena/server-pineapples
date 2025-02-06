package com.server.pineapples.service;

import com.server.pineapples.repository.FileRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ExcelFieldService {

    private final FileRepository fileRepository;
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.###");

    public ExcelFieldService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public List<Map<String, String>> extractFileXlsx(MultipartFile file) throws IOException {
        List<Map<String, String>> data = new ArrayList<>();
        Set<String> bookingIdsSet = new HashSet<>();
        Map<String, Map<String, Object>> cachedResults = new HashMap<>();

        try (InputStream inputStream = file.getInputStream(); Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IllegalArgumentException("O arquivo está vazio ou não contém cabeçalhos.");
            }

            List<String> headers = new ArrayList<>();
            for (Cell cell : headerRow) {
                headers.add(getCellValue(cell));
            }

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                Map<String, String> rowData = new LinkedHashMap<>();
                boolean isEmptyRow = true;
                for (int j = 0; j < headers.size(); j++) {
                    Cell cell = row.getCell(j);
                    String cellValue = (cell != null) ? getCellValue(cell) : "";
                    rowData.put(headers.get(j), cellValue);

                    if (!cellValue.isEmpty()) {
                        isEmptyRow = false;
                    }
                }

                if (isEmptyRow) {
                    continue;
                }

                String descricao = rowData.get("Descrição");
                if (descricao == null || descricao.isEmpty()) {
                    rowData.put("Resultado", "Descrição vazia");
                    rowData.put("Booking_Id", "Não encontrado");
                    rowData.put("Valores Iguais", "false");
                    data.add(rowData);
                    continue;
                }

                Map<String, Object> dbData = cachedResults.computeIfAbsent(descricao, desc -> {
                    List<Map<String, Object>> results = fileRepository.findByResult(desc);
                    return results.isEmpty() ? null : results.get(0);
                });

                String idBooking = (dbData != null && dbData.get("id_booking") != null)
                        ? String.valueOf(dbData.get("id_booking"))
                        : "Não encontrado";

                if (bookingIdsSet.contains(idBooking)) continue;
                bookingIdsSet.add(idBooking);


                Double totalPayment = dbData != null && dbData.get("total_payment") != null
                        ? ((Number) dbData.get("total_payment")).doubleValue()
                        : 0.0;
                Double portalCommission = dbData != null && dbData.get("portal_comission") != null
                        ? ((Number) dbData.get("portal_comission")).doubleValue()
                        : 0.0;
                String result = decimalFormat.format(totalPayment - portalCommission);

                boolean valuesIguais = compareValuesAndResult(rowData.get("Valor"), result);

                rowData.put("Resultado", result);
                rowData.put("Valores Iguais", String.valueOf(valuesIguais));
                rowData.put("Booking_Id", idBooking);

                data.add(rowData);
            }
        }
        return data;
    }

    private boolean compareValuesAndResult(String value, String result) {
        try {
            double valor = Double.parseDouble(value.replace(",", "."));
            double resultado = Double.parseDouble(result.replace(",", "."));
            return Double.compare(valor, resultado) == 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    return dateFormat.format(cell.getDateCellValue());
                }
                return formatNumericValue(cell.getNumericCellValue());
            case STRING:
                return cell.getStringCellValue().trim();
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return evaluateFormula(cell);
            default:
                return "";
        }
    }

    private String formatNumericValue(double value) {
        return (value % 1 == 0) ? String.valueOf((long) value) : String.valueOf(value).replace(",", ".");
    }

    private String evaluateFormula(Cell cell) {
        try {
            FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
            CellValue cellValue = evaluator.evaluate(cell);
            switch (cellValue.getCellType()) {
                case NUMERIC:
                    return formatNumericValue(cellValue.getNumberValue());
                case STRING:
                    return cellValue.getStringValue();
                case BOOLEAN:
                    return String.valueOf(cellValue.getBooleanValue());
                default:
                    return "";
            }
        } catch (Exception e) {
            return cell.getCellFormula();
        }
    }


}
