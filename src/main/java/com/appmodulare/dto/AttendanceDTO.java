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
    private String date;        // LocalDate -> String (yyyy-MM-dd)
    private String checkIn;     // LocalDateTime -> String
    private String checkOut;    // LocalDateTime -> String

    private Double totalHours;
    private String totalHoursFormatted;

    private String status;      // PUNTUAL / TARDE / FUERA_DE_UBICACION

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
    private Double distanceFromOffice;  // Distancia en metros
}