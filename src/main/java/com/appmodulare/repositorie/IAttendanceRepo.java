package com.appmodulare.repositorie;

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


}
