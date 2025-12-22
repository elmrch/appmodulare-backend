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
        List<Employee> employees;
        if (filter.getEmployeeId() != null) {
            Employee emp = employeeRepo.findById(filter.getEmployeeId())
                    .orElseThrow(() -> new Exception("Empleado no encontrado"));
            employees = List.of(emp);
        } else {
            employees = employeeRepo.findAll();
        }
        for (Employee employee : employees) {
            LocalDate currentDate = startDate;
            while (!currentDate.isAfter(endDate)) {
                if (currentDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
                    Attendance attendance = attendanceRepo.findByEmployeeAndDate(
                            employee.getId(),
                            currentDate
                    );
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