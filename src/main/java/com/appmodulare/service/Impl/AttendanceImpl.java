package com.appmodulare.service.Impl;

import com.appmodulare.model.Attendance;
import com.appmodulare.model.Employee;
import com.appmodulare.model.Schedule;
import com.appmodulare.repositorie.IAttendanceRepo;
import com.appmodulare.repositorie.IEmployeeRepo;
import com.appmodulare.repositorie.IGenericRepo;
import com.appmodulare.service.IAttendanceService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceImpl extends CRUDImpl<Attendance, Integer> implements IAttendanceService {

    private final IAttendanceRepo repo;
    private final IEmployeeRepo employeeRepo;

    @Override
    protected IGenericRepo<Attendance, Integer> getRepo() {
        return repo;
    }

    // ========================== CHECK-IN =============================
    @Override
    public Attendance registerCheckIn(String dni) throws Exception {

        Employee emp = employeeRepo.findByDni(dni);
        if (emp == null) {
            throw new Exception("El DNI ingresado no existe.");
        }

        Schedule schedule = emp.getSchedule();
        if (schedule == null) {
            throw new Exception("El empleado no tiene un horario asignado.");
        }

        Attendance today = repo.findByEmployeeAndDate(
                emp.getId(),
                LocalDate.now()
        );

        if (today != null && today.getCheckIn() != null) {
            throw new Exception("Ya registró su entrada hoy.");
        }

        if (today == null) {
            today = new Attendance();
            today.setEmployee(emp);
            today.setDate(LocalDate.now());
        }

        LocalDateTime now = LocalDateTime.now();
        today.setCheckIn(now);

        // ==============================
        // CÁLCULO DE PUNTUALIDAD DINÁMICA
        // ==============================
        LocalTime horaInicio = schedule.getStartTime();
        LocalDateTime horaEntrada = now.toLocalDate().atTime(horaInicio);

        LocalDateTime toleranciaMax = horaEntrada.plusMinutes(15);

        if (now.isBefore(toleranciaMax) || now.isEqual(toleranciaMax)) {
            today.setStatus("PUNTUAL");
        } else {
            today.setStatus("TARDE");
        }

        return repo.save(today);
    }


    // ========================== CHECK-OUT =============================
    @Override
    public Attendance registerCheckOut(String dni) throws Exception {

        Employee emp = employeeRepo.findByDni(dni);
        if (emp == null) {
            throw new Exception("El DNI ingresado no existe.");
        }

        Attendance today = repo.findByEmployeeAndDate(
                emp.getId(),
                LocalDate.now()
        );

        if (today == null || today.getCheckIn() == null) {
            throw new Exception("Debe registrar su entrada antes de marcar salida.");
        }

        if (today.getCheckOut() != null) {
            throw new Exception("Ya registró su salida hoy.");
        }

        today.setCheckOut(LocalDateTime.now());

        // =============================
        // CÁLCULO DE HORAS TRABAJADAS
        // =============================
        if (today.getCheckIn() != null && today.getCheckOut() != null) {

            Duration duration = Duration.between(
                    today.getCheckIn(),
                    today.getCheckOut()
            );

            long hours = duration.toHours();
            long minutes = duration.toMinutesPart();
            long seconds = duration.toSecondsPart();

            String formatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            today.setTotalHoursFormatted(formatted);

            double hoursDecimal = duration.toSeconds() / 3600.0;
            hoursDecimal = Math.round(hoursDecimal * 100.0) / 100.0;

            today.setTotalHours(hoursDecimal);
        }

        return repo.save(today);
    }


    // ========================== LISTA CON JOINS =============================
    @Override
    public List<Attendance> findAll() {
        return repo.findAllWithEmployee();
    }
}
