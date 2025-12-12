package com.appmodulare.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecurityIncidentDTO {

    private Long id;
    private LocalDateTime dateTime;
    private String severity;
    private String incident;
    private String reportedBy;
    private String title;
    private String fullName;


    /* private String providerName;*/
}