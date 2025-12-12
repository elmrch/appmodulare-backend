package com.appmodulare.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
        private Integer id;
        private String firstName;
        private String lastName;
        private String dni;
        private String email;
        private String phone;
        private String address;
        private LocalDate hireDate;
        private String position;
        private Double salary;
        private String photoUrl;
        private Integer areaId;
        private Integer scheduleId;
        private AreaDTO area;
        private ScheduleDTO schedule;

}
