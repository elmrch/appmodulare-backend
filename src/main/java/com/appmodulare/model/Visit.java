package com.appmodulare.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "visits")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fullName;
    private String dni;
    private String company;
    private String reason;

    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    @Column(nullable = false)
    private LocalDate date;
    private String status; // DENTRO / SALIÓ



}
