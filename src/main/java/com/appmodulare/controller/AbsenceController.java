package com.appmodulare.controller;
import com.appmodulare.dto.AbsenceFilterDTO;
import com.appmodulare.dto.AbsenceReportDTO;
import com.appmodulare.service.IAbsenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/absences")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AbsenceController {

    private final IAbsenceService absenceService;

    /**
     * POST /api/absences
     * Obtiene lista de ausencias (días sin marcar)
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> getAbsences(
            @RequestBody AbsenceFilterDTO filter) {

        try {
            List<AbsenceReportDTO> absences = absenceService.findMissingAttendances(filter);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", absences);
            response.put("total", absences.size());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}
