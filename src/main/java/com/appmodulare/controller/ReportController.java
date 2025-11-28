package com.appmodulare.controller;


import com.appmodulare.dto.AttendanceReportDTO;
import com.appmodulare.dto.ReportFilterDTO;
import com.appmodulare.service.IReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReportController {

    private final IReportService reportService;

    /**
     * POST /api/reports/attendance
     * Genera reporte de asistencias con filtros
     */
    @PostMapping("/attendance")
    public ResponseEntity<Map<String, Object>> getAttendanceReport(
            @RequestBody ReportFilterDTO filter) {

        try {
            // Obtener datos del reporte
            List<AttendanceReportDTO> reportData = reportService.generateAttendanceReport(filter);

            // Obtener estadísticas
            Map<String, Long> statistics = reportService.getReportStatistics(filter);

            // Respuesta completa
            Map<String, Object> response = new HashMap<>();
            response.put("data", reportData);
            response.put("statistics", statistics);
            response.put("success", true);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    /**
     * POST /api/reports/attendance/statistics
     * Solo obtiene las estadísticas (sin los datos completos)
     */
    @PostMapping("/attendance/statistics")
    public ResponseEntity<Map<String, Object>> getAttendanceStatistics(
            @RequestBody ReportFilterDTO filter) {

        try {
            Map<String, Long> statistics = reportService.getReportStatistics(filter);

            Map<String, Object> response = new HashMap<>();
            response.put("statistics", statistics);
            response.put("success", true);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}