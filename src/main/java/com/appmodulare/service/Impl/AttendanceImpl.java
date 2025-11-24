package com.appmodulare.service.Impl;

import com.appmodulare.model.Attendance;
import com.appmodulare.model.Employee;
import com.appmodulare.repositorie.IAttendanceRepo;
import com.appmodulare.repositorie.IEmployeeRepo;
import com.appmodulare.repositorie.IGenericRepo;
import com.appmodulare.service.IAttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
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

        Attendance today = repo.findByEmployeeAndDate(
                emp.getId(),
                LocalDateTime.now().toLocalDate()
        );

        // Validación: ya registró entrada
        if (today != null && today.getCheckIn() != null) {
            throw new Exception("Ya registró su entrada hoy. No puede registrar dos veces.");
        }

        // Crear si no existe
        if (today == null) {
            today = new Attendance();
            today.setEmployee(emp);
            today.setDate(LocalDateTime.now().toLocalDate());
        }

        LocalDateTime now = LocalDateTime.now();
        today.setCheckIn(now);

        // ======= CÁLCULO DE ESTADO (PUNTUAL / TARDE) =======
        LocalDateTime horaEntrada = now.toLocalDate().atTime(8, 30);
        LocalDateTime toleranciaMaxima = horaEntrada.plusMinutes(15); // 08:45 AM

        if (now.isBefore(toleranciaMaxima) || now.isEqual(toleranciaMaxima)) {
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
                LocalDateTime.now().toLocalDate()
        );

        if (today == null || today.getCheckIn() == null) {
            throw new Exception("Debe registrar su entrada antes de marcar salida.");
        }

        if (today.getCheckOut() != null) {
            throw new Exception("Ya registró su salida hoy. No puede registrar dos veces.");
        }

        today.setCheckOut(LocalDateTime.now());

// Calcular horas trabajadas SOLO si checkIn y checkOut no son null
        if (today.getCheckIn() != null && today.getCheckOut() != null) {

            Duration duration = Duration.between(
                    today.getCheckIn(),
                    today.getCheckOut()
            );

            // *** FORMATO HH:mm:ss ***
            long hours = duration.toHours();
            long minutes = duration.toMinutesPart();
            long seconds = duration.toSecondsPart();

            String formatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            today.setTotalHoursFormatted(formatted);

            // *** FORMATO DECIMAL ***
            double hoursDecimal = duration.toSeconds() / 3600.0;
            hoursDecimal = Math.round(hoursDecimal * 100.0) / 100.0;

            today.setTotalHours(hoursDecimal);

        } else {
            today.setTotalHours(0.0);
            today.setTotalHoursFormatted("00:00:00");
        }

        return repo.save(today);
    }

    @Override
    public List<Attendance> findAll() {
        return repo.findAllWithEmployee();
    }
}
