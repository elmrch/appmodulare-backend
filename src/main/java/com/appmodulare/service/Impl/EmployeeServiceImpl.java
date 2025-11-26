package com.appmodulare.service.Impl;

import com.appmodulare.model.Area;
import com.appmodulare.model.Employee;
import com.appmodulare.model.Schedule;
import com.appmodulare.repositorie.IAreaRepository;
import com.appmodulare.repositorie.IEmployeeRepo;
import com.appmodulare.repositorie.IGenericRepo;
import com.appmodulare.repositorie.IScheduleRepo;
import com.appmodulare.service.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl extends CRUDImpl<Employee, Integer> implements IEmployeeService {

    private final IEmployeeRepo repo;
    //private final IAreaRepository areaRepo;
//private final IScheduleRepo scheduleRepo;

    @Override
    protected IGenericRepo<Employee, Integer> getRepo() {
        return repo;
    }



}