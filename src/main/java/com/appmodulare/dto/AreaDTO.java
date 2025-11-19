package com.appmodulare.dto;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AreaDTO {

    private Integer id;

    @NotBlank
    @Size(min = 3, max = 70, message = "Name must have between 3 and 70 characters")
    private String name;

    @NotBlank
    @Size(min = 3, max = 400, message = "Description must have between 3 and 400 characters")
    private String description;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
