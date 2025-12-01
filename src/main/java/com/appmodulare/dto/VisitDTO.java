package com.appmodulare.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitDTO {

    private Long id;                // null cuando se crea
    private String fullName;
    private String dni;
    private String company;
    private String reason;

    private LocalDate date;
    private LocalDateTime checkIn;  // lo genera el backend
    private LocalDateTime checkOut; // null al inicio

    private String status;          // "DENTRO" o "FUERA"
}
