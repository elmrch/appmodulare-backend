package com.appmodulare.service.Impl;

import com.appmodulare.model.Schedule;
import com.appmodulare.repositorie.IGenericRepo;
import com.appmodulare.repositorie.IScheduleRepo;
import com.appmodulare.service.IAreaService;
import com.appmodulare.service.IScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public  class ScheduleServiceImpl extends CRUDImpl<Schedule,Integer> implements IScheduleService{

    private final IScheduleRepo repo;


    @Override
    protected IGenericRepo<Schedule, Integer> getRepo() {
        return repo;
    }
}
