package com.appmodulare.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportFilterDTO {
    private Integer employeeId;  // null = todos los empleados
    private String startDate;    // fecha desde (formato: "2025-11-01")
    private String endDate;      // fecha hasta (formato: "2025-11-30")
    private String status;       // "all", "PUNTUAL", "TARDE"
}
