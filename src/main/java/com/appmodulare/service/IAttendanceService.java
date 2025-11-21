package com.appmodulare.service;

import com.appmodulare.model.Attendance;

public interface IAttendanceService extends ICRUD<Attendance,Integer>{
    Attendance registerCheckIn(String dni) throws Exception;

    Attendance registerCheckOut(String dni) throws Exception;

}
