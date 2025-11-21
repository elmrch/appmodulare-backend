package com.appmodulare.service.Impl;

import com.appmodulare.model.Attendance;
import com.appmodulare.model.Employee;
import com.appmodulare.repositorie.IAttendanceRepo;
import com.appmodulare.repositorie.IEmployeeRepo;
import com.appmodulare.repositorie.IGenericRepo;
import com.appmodulare.service.IAttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    // ================= CHECK-IN =================
    @Override
    public Attendance registerCheckIn(String dni) throws Exception {
        // Buscar empleado por DNI
        Employee emp = employeeRepo.findByDni(dni);
        if (emp == null) {
            throw new Exception("El DNI no existe en la base de datos.");
        }

        // Verificar si ya tiene una entrada abierta hoy
        Attendance open = repo.findByEmployeeAndDate(emp.getId(), LocalDateTime.now().toLocalDate());
        if (open != null && open.getCheckOut() == null) {
            throw new Exception("Ya tiene una entrada registrada y aún no ha marcado salida.");
        }

        // Crear nueva asistencia
        Attendance att = new Attendance();
        att.setEmployee(emp);
        att.setCheckIn(LocalDateTime.now());
        att.setDate(LocalDateTime.now().toLocalDate());

        // El estado PUNTUAL/TARDE se calcula automáticamente en prePersist()
        return repo.save(att);
    }

    // ================= CHECK-OUT =================
    @Override
    public Attendance registerCheckOut(String dni) throws Exception {
        // Buscar empleado por DNI
        Employee emp = employeeRepo.findByDni(dni);
        if (emp == null) {
            throw new Exception("El DNI no existe en la base de datos.");
        }

        // Buscar la asistencia abierta del día
        Attendance open = repo.findByEmployeeAndDate(emp.getId(), LocalDateTime.now().toLocalDate());
        if (open == null || open.getCheckOut() != null) {
            throw new Exception("No existe una entrada previa. Debe marcar entrada primero.");
        }

        // Registrar salida
        open.setCheckOut(LocalDateTime.now());

        // 🔥 CALCULAR HORAS AQUÍ
        if (open.getCheckIn() != null && open.getCheckOut() != null) {
            double hours = java.time.Duration.between(open.getCheckIn(), open.getCheckOut()).toMinutes() / 60.0;
            open.setTotalHours(hours);
        }

        return repo.save(open); // Ahora sí se guarda totalHours
    }

    @Override
    public List<Attendance> findAll() {
        return repo.findAllWithEmployee();
    }


}
