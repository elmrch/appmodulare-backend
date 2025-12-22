package com.appmodulare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDTO {

    private Integer id;

    // Datos del empleado
    private Integer employeeId;
    private String dni;
    private String firstName;
    private String lastName;

    // Asistencia
    private String date;
    private String checkIn;
    private String checkOut;

    private Double totalHours;
    private String totalHoursFormatted;

    private String status;

    // ========== GPS CHECK-IN ==========
    private Double checkInLatitude;
    private Double checkInLongitude;
    private String checkInLocation;

    // ========== GPS CHECK-OUT ==========
    private Double checkOutLatitude;
    private Double checkOutLongitude;
    private String checkOutLocation;

    // ========== VALIDACIÓN ==========
    private Boolean locationValid;
    private Double distanceFromOffice;
}