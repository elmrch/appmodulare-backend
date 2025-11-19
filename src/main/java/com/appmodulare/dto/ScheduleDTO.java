package com.appmodulare.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class ScheduleDTO {

    private Integer id;

    @NotNull(message = "La hora de inicio (startTime) no puede ser nula")
    private LocalTime startTime;

    @NotNull(message = "La hora de fin (endTime) no puede ser nula")
    private LocalTime endTime;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Validación cruzada: startTime < endTime
    @AssertTrue(message = "La hora de inicio debe ser menor que la hora de fin")
    public boolean isStartBeforeEnd() {
        if (startTime == null || endTime == null) {
            return true; // @NotNull manejará estos casos
        }
        return startTime.isBefore(endTime);
    }
}
