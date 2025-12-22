package com.appmodulare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceReportDTO {
    private Integer id;
    private Integer employeeId;
    private String employeeName;
    private String employeeDni;
    private LocalDate date;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Double totalHours;
    private String totalHoursFormatted;
    private String status;


}