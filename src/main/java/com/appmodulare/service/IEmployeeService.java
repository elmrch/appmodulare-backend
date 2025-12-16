package com.appmodulare.service;

import com.appmodulare.model.Employee;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IEmployeeService extends ICRUD<Employee,Integer>{

    Employee updatePhoto(Integer id, MultipartFile photo) throws Exception;

    String generateQrCode(Integer employeeId) throws Exception;
    Employee findByQrCode(String qrCode);
    Employee findByDniOrQrCode(String identifier);




}
