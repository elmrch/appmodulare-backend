package com.appmodulare.repositorie;

import com.appmodulare.dto.AttendanceReportDTO;
import com.appmodulare.model.Attendance;
import com.appmodulare.model.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IAttendanceRepo extends IGenericRepo<Attendance,Integer>{

   @Query("SELECT a FROM Attendance a WHERE a.employee.id = :empId AND a.date = :date")
   Attendance findByEmployeeAndDate(@Param("empId") Integer empId, @Param("date") LocalDate date);
    @Query("SELECT a FROM Attendance a JOIN FETCH a.employee")
    List<Attendance> findAllWithEmployee();


    // ========== NUEVOS MÉTODOS PARA REPORTES ==========

    /**
     * Obtiene reporte filtrado por fecha, empleado y estado
     */
    @Query("SELECT new com.appmodulare.dto.AttendanceReportDTO(" +
            "a.id, " +
            "e.id, " +
            "CONCAT(e.firstName, ' ', e.lastName), " +
            "e.dni, " +
            "a.date, " +
            "a.checkIn, " +
            "a.checkOut, " +
            "a.totalHours, " +
            "a.totalHoursFormatted, " +
            "a.status) " +
            "FROM Attendance a " +
            "JOIN a.employee e " +
            "WHERE a.date BETWEEN :startDate AND :endDate " +
            "AND (:employeeId IS NULL OR e.id = :employeeId) " +
            "AND (:status = 'all' OR a.status = :status) " +
            "ORDER BY a.date DESC, a.checkIn DESC")
    List<AttendanceReportDTO> findAttendanceReport(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("employeeId") Integer employeeId,
            @Param("status") String status
    );

    /**
     * Cuenta total de registros que cumplen los filtros
     */
    @Query("SELECT COUNT(a) FROM Attendance a " +
            "JOIN a.employee e " +
            "WHERE a.date BETWEEN :startDate AND :endDate " +
            "AND (:employeeId IS NULL OR e.id = :employeeId) " +
            "AND (:status = 'all' OR a.status = :status)")
    Long countByFilters(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("employeeId") Integer employeeId,
            @Param("status") String status
    );

    /**
     * Cuenta solo los puntuales según filtros
     */
    @Query("SELECT COUNT(a) FROM Attendance a " +
            "JOIN a.employee e " +
            "WHERE a.date BETWEEN :startDate AND :endDate " +
            "AND (:employeeId IS NULL OR e.id = :employeeId) " +
            "AND a.status = 'PUNTUAL'")
    Long countPunctualByFilters(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("employeeId") Integer employeeId
    );

    /**
     * Cuenta solo las tardanzas según filtros
     */
    @Query("SELECT COUNT(a) FROM Attendance a " +
            "JOIN a.employee e " +
            "WHERE a.date BETWEEN :startDate AND :endDate " +
            "AND (:employeeId IS NULL OR e.id = :employeeId) " +
            "AND a.status = 'TARDE'")
    Long countLateByFilters(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("employeeId") Integer employeeId
    );
}