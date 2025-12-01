package com.appmodulare.service;

import com.appmodulare.model.Attendance;
import com.appmodulare.model.Visit;

public interface IVisitService extends ICRUD<Visit,Integer>{

    Visit registerCheckIn(Visit visit) throws Exception;

    Visit registerCheckOut(String dni) throws Exception;


}
