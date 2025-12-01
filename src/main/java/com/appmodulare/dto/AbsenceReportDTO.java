package com.appmodulare.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbsenceReportDTO {
    private Integer employeeId;
    private String employeeName;
    private String employeeDni;
    private LocalDate missingDate;       // Fecha que no marcó
    private String dayOfWeek;            // Ej: "Lunes", "Martes"
}