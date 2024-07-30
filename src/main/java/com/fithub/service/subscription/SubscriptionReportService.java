package com.fithub.service.subscription;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class SubscriptionReportService {

    public ByteArrayInputStream generateReport(List<Long> subscriptionIds) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Report");

            // Create Header Row
            String[] headers = {"ID", "Name", "Value"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Create Data Rows
            int rowIdx = 1;
            for (Long id : subscriptionIds) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue("Test");
                row.createCell(1).setCellValue("Test");
                row.createCell(2).setCellValue("Test");
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}

