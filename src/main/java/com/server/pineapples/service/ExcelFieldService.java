package com.server.pineapples.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelFieldService {

    public List<String[]> extractFileXlsx(MultipartFile file) throws IOException {
        List<String[]> data = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream(); Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                List<String> rowData = new ArrayList<>();
                for (Cell cell : row) {
                    rowData.add(getCellValue(cell));
                }
                data.add(rowData.toArray(new String[0]));
            }
        }
        return data;
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
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
        if (value % 1 == 0) {
            return String.valueOf((long) value);
        }
        return String.valueOf(value).replace(".", ",");
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
