package com.appmodulare.repositorie;

import com.appmodulare.model.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IEmployeeRepo extends  IGenericRepo<Employee,Integer> {
    Employee findByDni(String dni);
    Optional<Employee> findByQrCode(String qrCode);
    Optional<Employee> findByDniOrQrCode(String dni, String qrCode);

}
