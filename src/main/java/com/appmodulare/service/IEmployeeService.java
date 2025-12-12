package com.appmodulare.service;

import com.appmodulare.model.Employee;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IEmployeeService extends ICRUD<Employee,Integer>{

    Employee updatePhoto(Integer id, MultipartFile photo) throws Exception;




}
