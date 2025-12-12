package com.appmodulare.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "security_incident")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecurityIncident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private Severity severity; // ✅ private String severity;

    @Column(nullable = false)
    private String incident;

    @Column(name = "reported_by", nullable = false)
    private String reportedBy;


    @PrePersist
    protected void onCreate() {
        dateTime = LocalDateTime.now();
    }
}