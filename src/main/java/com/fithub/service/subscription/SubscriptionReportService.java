package com.fithub.service.subscription;

import com.fithub.model.subscription.Subscription;
import com.fithub.repository.subscription.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionReportService {
    private final SubscriptionRepository subscriptionRepository;

    public byte[] generateReport(List<Long> subscriptionIds) throws IOException {
        List<Subscription> subscriptions = subscriptionRepository.findAllById(subscriptionIds);
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Report");

            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle dataStyle = createDataStyle(workbook);

            // Create Header Row
            String[] headers = {"Reference", "Identification", "Member", "Start Date", "End Date",
                    "Total", "Tax", "Discount", "Net", "Status"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Create Data Rows
            int rowIdx = 1;
            for (Subscription subscription : subscriptions) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(subscription.getReference());
                row.getCell(0).setCellStyle(dataStyle);
                sheet.setColumnWidth(0, 12 * 256);

                row.createCell(1).setCellValue(subscription.getMember().getIdentificationNumber());
                row.getCell(1).setCellStyle(dataStyle);
                sheet.setColumnWidth(1, 13 * 256);

                row.createCell(2).setCellValue(subscription.getMember().getFirstName() + " " + subscription.getMember().getLastName());
                row.getCell(2).setCellStyle(dataStyle);
                sheet.setColumnWidth(2, 16 * 256);

                row.createCell(3).setCellValue(subscription.getStartDate().toString());
                row.getCell(3).setCellStyle(dataStyle);
                sheet.setColumnWidth(3, 12 * 256);

                row.createCell(4).setCellValue(subscription.getEndDate().toString());
                row.getCell(4).setCellStyle(dataStyle);
                sheet.setColumnWidth(4, 12 * 256);

                row.createCell(5).setCellValue(subscription.getTotalAmount());
                row.getCell(5).setCellStyle(dataStyle);
                sheet.setColumnWidth(5, 8 * 256);

                row.createCell(6).setCellValue(subscription.getTaxAmount());
                row.getCell(6).setCellStyle(dataStyle);
                sheet.setColumnWidth(6, 8 * 256);

                row.createCell(7).setCellValue(subscription.getDiscountAmount());
                row.getCell(7).setCellStyle(dataStyle);
                sheet.setColumnWidth(7, 9 * 256);

                row.createCell(8).setCellValue(subscription.getNetAmount());
                row.getCell(8).setCellStyle(dataStyle);
                sheet.setColumnWidth(8, 8 * 256);

                row.createCell(9).setCellValue(subscription.getStatus().name());
                row.getCell(9).setCellStyle(dataStyle);
                sheet.setColumnWidth(9, 8 * 256);

            }

            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new IOException("Failed to generate report", e);
        }
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 11);
        font.setColor(IndexedColors.WHITE1.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    private CellStyle createDataStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);
        style.setFont(font);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }
}

