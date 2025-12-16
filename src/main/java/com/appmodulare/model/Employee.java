package com.appmodulare.model;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name", length = 80)
    private String firstName;

    @Column(name = "last_name", length = 80)
    private String lastName;

    @Column(length = 20)
    private String dni;

    @Column(length = 150)
    private String email;

    @Column(length = 15)
    private String phone;

    @Column(length = 255)
    private String address;

    @Column(name = "hire_date")
    private LocalDate hireDate;

  /*  @Column(name = "area_id")
    private Integer areaId;*/

    @Column(length = 100)
    private String position;

    @Column(precision = 10, scale = 2)
    private BigDecimal salary;
    @Column(name = "qr_code", length = 255, unique = true)
    private String qrCode;

    @Column(name = "qr_enabled")
    private Boolean qrEnabled = false;
   /* @Column(name = "schedule_id")
    private Integer scheduleId;*/

    @Column(name = "photo_url", length = 255)
    private String photoUrl;

    @Column(name = "created_at" , updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}