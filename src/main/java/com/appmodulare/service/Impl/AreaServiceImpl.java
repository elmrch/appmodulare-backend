package com.appmodulare.service.Impl;

import com.appmodulare.model.Area;
import com.appmodulare.repositorie.IAreaRepository;
import com.appmodulare.repositorie.IGenericRepo;
import com.appmodulare.service.IAreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AreaServiceImpl extends CRUDImpl<Area,Integer> implements IAreaService {

    private  final IAreaRepository iAreaRepository;


    @Override
    protected IGenericRepo<Area, Integer> getRepo() {
        return iAreaRepository;
    }
}
