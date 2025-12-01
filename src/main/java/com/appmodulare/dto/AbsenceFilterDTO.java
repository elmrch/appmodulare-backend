package com.appmodulare.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbsenceFilterDTO {
    private Integer employeeId;  // null = todos
    private String startDate;
    private String endDate;
}