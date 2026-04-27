package com.campus.service;

import com.campus.mapper.ReportMapper;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private ReportMapper reportMapper;

    public void exportMonthlyReport(OutputStream outputStream) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("月度运营报表");

            org.apache.poi.ss.usermodel.CellStyle headerStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            org.apache.poi.ss.usermodel.CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setAlignment(HorizontalAlignment.CENTER);

            org.apache.poi.ss.usermodel.CellStyle numberStyle = workbook.createCellStyle();
            numberStyle.setDataFormat(workbook.createDataFormat().getFormat("#,##0.00"));

            Row headerRow = sheet.createRow(0);
            String[] headers = {"商家名称", "订单量", "营收金额", "退款量", "退款率(%)"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            List<Map<String, Object>> data = reportMapper.getMonthlyReportData();

            int rowNum = 1;
            for (Map<String, Object> row : data) {
                Row dataRow = sheet.createRow(rowNum++);

                Cell cell0 = dataRow.createCell(0);
                cell0.setCellValue(row.get("seller_name") != null ? row.get("seller_name").toString() : "");
                cell0.setCellStyle(dataStyle);

                Cell cell1 = dataRow.createCell(1);
                cell1.setCellValue(row.get("order_count") != null ? Integer.parseInt(row.get("order_count").toString()) : 0);
                cell1.setCellStyle(dataStyle);

                Cell cell2 = dataRow.createCell(2);
                cell2.setCellValue(row.get("total_amount") != null ? Double.parseDouble(row.get("total_amount").toString()) : 0.0);
                cell2.setCellStyle(numberStyle);

                Cell cell3 = dataRow.createCell(3);
                cell3.setCellValue(row.get("refund_count") != null ? Integer.parseInt(row.get("refund_count").toString()) : 0);
                cell3.setCellStyle(dataStyle);

                Cell cell4 = dataRow.createCell(4);
                double refundRate = row.get("refund_rate") != null ? Double.parseDouble(row.get("refund_rate").toString()) : 0.0;
                cell4.setCellValue(String.format("%.2f", refundRate));
                cell4.setCellStyle(dataStyle);
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(outputStream);
        }
    }

    public void exportActivityReport(OutputStream outputStream) throws IOException {
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            com.lowagie.text.Font titleFont = new com.lowagie.text.Font(bfChinese, 18, com.lowagie.text.Font.BOLD);
            com.lowagie.text.Font headerFont = new com.lowagie.text.Font(bfChinese, 12, com.lowagie.text.Font.BOLD);
            com.lowagie.text.Font contentFont = new com.lowagie.text.Font(bfChinese, 10, com.lowagie.text.Font.NORMAL);

            Paragraph title = new Paragraph("活动报名统计报表", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            List<Map<String, Object>> data = reportMapper.getActivityReportData();

            if (data != null && !data.isEmpty()) {
                PdfPTable table = new PdfPTable(4);
                table.setWidthPercentage(100);
                table.setSpacingBefore(10);

                String[] headers = {"活动名称", "报名人数", "开始时间", "地点"};
                for (String header : headers) {
                    PdfPCell cell = new PdfPCell(new Paragraph(header, headerFont));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBackgroundColor(new java.awt.Color(200, 220, 255));
                    table.addCell(cell);
                }

                for (Map<String, Object> row : data) {
                    table.addCell(new Paragraph(row.get("title") != null ? row.get("title").toString() : "", contentFont));
                    table.addCell(new Paragraph(row.get("participant_count") != null ? row.get("participant_count").toString() : "0", contentFont));
                    table.addCell(new Paragraph(row.get("start_time") != null ? row.get("start_time").toString() : "", contentFont));
                    table.addCell(new Paragraph(row.get("location") != null ? row.get("location").toString() : "", contentFont));
                }

                document.add(table);
            } else {
                document.add(new Paragraph("暂无数据", contentFont));
            }

            document.close();
        } catch (Exception e) {
            throw new IOException("导出PDF失败: " + e.getMessage(), e);
        }
    }
}
