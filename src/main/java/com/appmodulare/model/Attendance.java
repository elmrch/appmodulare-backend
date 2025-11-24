// Attendance.java
package com.appmodulare.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Duration;

@Entity
@Table(name = "attendance")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "check_in")
    private LocalDateTime checkIn;

    @Column(name = "check_out")
    private LocalDateTime checkOut;

    @Column(name = "total_hours")
    private Double totalHours;
    @Column(name = "total_hours_formatted", length = 20)
    private String totalHoursFormatted;
    @Column(length = 20)
    private String status; // PUNTUAL / TARDE

    @Column(nullable = false)
    private LocalDate date;

    @PrePersist
    public void prePersist() {
        if (date == null) {
            date = LocalDate.now();
        }

        if (checkIn != null && status == null) {
            // Consideramos horario puntual antes de las 09:00
            status = checkIn.toLocalTime().isBefore(LocalTime.of(9, 0)) ? "PUNTUAL" : "TARDE";
        }
    }

}
