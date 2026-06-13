package com.feedup.feedup.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feedup.feedup.entity.Feedback;
import com.feedup.feedup.entity.FeedbackSession;
import com.feedup.feedup.repository.FeedbackRepository;
import com.feedup.feedup.service.FeedbackSessionService;

@RestController
@RequestMapping("/api/export")
@CrossOrigin("*")
public class ExportController {

    private final FeedbackSessionService sessionService;
    private final FeedbackRepository feedbackRepository;

    public ExportController(
            FeedbackSessionService sessionService,
            FeedbackRepository feedbackRepository) {
        this.sessionService = sessionService;
        this.feedbackRepository = feedbackRepository;
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<byte[]> exportSession(@PathVariable String sessionId) throws IOException {
        FeedbackSession session = sessionService.getSession(sessionId);
        List<Feedback> feedbacks = feedbackRepository.findBySessionIdOrderBySubmittedAtDesc(sessionId);

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Feedback");
            String[] headers = {
                    "Email", "Date", "Hygiene", "Taste", "Ingredients", "Service", "Quantity",
                    "Safety", "Availability", "Driver", "Cab", "Cleanliness", "Humidity",
                    "Lighting", "Response", "Comments"
            };

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            for (int i = 0; i < feedbacks.size(); i++) {
                Feedback feedback = feedbacks.get(i);
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(feedback.getEmail());
                row.createCell(1).setCellValue(feedback.getSubmittedAt().format(formatter));
                row.createCell(2).setCellValue(feedback.getHygiene());
                row.createCell(3).setCellValue(feedback.getTaste());
                row.createCell(4).setCellValue(feedback.getIngredients());
                row.createCell(5).setCellValue(feedback.getService());
                row.createCell(6).setCellValue(feedback.getQuantity());
                row.createCell(7).setCellValue(feedback.getSafety());
                row.createCell(8).setCellValue(feedback.getAvailability());
                row.createCell(9).setCellValue(feedback.getDriver());
                row.createCell(10).setCellValue(feedback.getCab());
                row.createCell(11).setCellValue(feedback.getCleanliness());
                row.createCell(12).setCellValue(feedback.getHumidity());
                row.createCell(13).setCellValue(feedback.getLighting());
                row.createCell(14).setCellValue(feedback.getResponse());
                row.createCell(15).setCellValue(feedback.getComments() == null ? "" : feedback.getComments());
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(output);
            String fileName = session.getSessionName().replaceAll("[^a-zA-Z0-9-_]", "_") + "_feedback.xlsx";
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            ContentDisposition.attachment().filename(fileName).build().toString())
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(output.toByteArray());
        }
    }
}
