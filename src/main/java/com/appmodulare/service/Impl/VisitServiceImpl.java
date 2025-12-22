package com.appmodulare.service.Impl;

import com.appmodulare.model.Visit;
import com.appmodulare.repositorie.IGenericRepo;
import com.appmodulare.repositorie.VisitRepo;
import com.appmodulare.service.IVisitService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl extends CRUDImpl<Visit ,Integer> implements IVisitService {


    private final VisitRepo repo;
    @Override
    protected IGenericRepo<Visit, Integer> getRepo() {
        return repo;
    }

    @Override
    public Visit registerCheckIn(Visit visit) {
        visit.setCheckIn(LocalDateTime.now());
        visit.setDate(LocalDate.now());
        visit.setStatus("IN");
        return repo.save(visit);
    }



    @Override
    public Visit registerCheckOut(String dni) throws Exception {
        Visit v = repo.findLastVisitWithoutCheckout(dni)
                .orElseThrow(() -> new Exception("No existe visita pendiente para este DNI"));

        v.setCheckOut(LocalDateTime.now());
        v.setStatus("OUT");

        return repo.save(v);
    }
}
