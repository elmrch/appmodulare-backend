package com.appmodulare.repositorie;

import com.appmodulare.model.Visit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VisitRepo extends IGenericRepo<Visit,Integer> {

    @Query("SELECT v FROM Visit v WHERE v.dni = :dni AND v.checkOut IS NULL ORDER BY v.checkIn DESC LIMIT 1")
    Optional<Visit> findLastVisitWithoutCheckout(@Param("dni") String dni);

}
