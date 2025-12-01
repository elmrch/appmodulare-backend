package com.appmodulare.service.Impl;


import com.appmodulare.dto.AbsenceFilterDTO;
import com.appmodulare.dto.AbsenceReportDTO;
import com.appmodulare.model.Attendance;
import com.appmodulare.model.Employee;
import com.appmodulare.repositorie.IAttendanceRepo;
import com.appmodulare.repositorie.IEmployeeRepo;
import com.appmodulare.service.IAbsenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AbsenceServiceImpl implements IAbsenceService {

    private final IAttendanceRepo attendanceRepo;
    private final IEmployeeRepo employeeRepo;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public List<AbsenceReportDTO> findMissingAttendances(AbsenceFilterDTO filter) throws Exception {

        if (filter.getStartDate() == null || filter.getEndDate() == null) {
            throw new Exception("Las fechas son obligatorias");
        }

        LocalDate startDate;
        LocalDate endDate;

        try {
            startDate = LocalDate.parse(filter.getStartDate(), dateFormatter);
            endDate = LocalDate.parse(filter.getEndDate(), dateFormatter);
        } catch (Exception e) {
            throw new Exception("Formato de fecha inválido. Use: yyyy-MM-dd");
        }

        if (startDate.isAfter(endDate)) {
            throw new Exception("La fecha inicial no puede ser mayor que la fecha final");
        }

        List<AbsenceReportDTO> absences = new ArrayList<>();

        // Obtener empleados (uno o todos)
        List<Employee> employees;
        if (filter.getEmployeeId() != null) {
            Employee emp = employeeRepo.findById(filter.getEmployeeId())
                    .orElseThrow(() -> new Exception("Empleado no encontrado"));
            employees = List.of(emp);
        } else {
            employees = employeeRepo.findAll();
        }

        // Por cada empleado
        for (Employee employee : employees) {

            // Recorrer todos los días del rango
            LocalDate currentDate = startDate;

            while (!currentDate.isAfter(endDate)) {

                // Saltar domingos (trabajamos Lunes-Sábado)
                if (currentDate.getDayOfWeek() != DayOfWeek.SUNDAY) {

                    // Verificar si tiene asistencia ese día
                    Attendance attendance = attendanceRepo.findByEmployeeAndDate(
                            employee.getId(),
                            currentDate
                    );

                    // Si NO tiene registro, es una ausencia
                    if (attendance == null) {
                        AbsenceReportDTO absence = new AbsenceReportDTO();
                        absence.setEmployeeId(employee.getId());
                        absence.setEmployeeName(employee.getFirstName() + " " + employee.getLastName());
                        absence.setEmployeeDni(employee.getDni());
                        absence.setMissingDate(currentDate);
                        absence.setDayOfWeek(
                                currentDate.getDayOfWeek()
                                        .getDisplayName(TextStyle.FULL, new Locale("es", "ES"))
                        );

                        absences.add(absence);
                    }
                }

                currentDate = currentDate.plusDays(1);
            }
        }

        return absences;
    }
}