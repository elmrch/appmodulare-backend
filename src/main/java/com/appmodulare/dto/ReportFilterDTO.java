package com.appmodulare.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportFilterDTO {
    private Integer employeeId;
    private String startDate;
    private String endDate;
    private String status;
}
