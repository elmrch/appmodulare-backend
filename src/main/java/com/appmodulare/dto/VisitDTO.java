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

    private Long id;
    private String fullName;
    private String dni;
    private String company;
    private String reason;

    private LocalDate date;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;

    private String status;
}
