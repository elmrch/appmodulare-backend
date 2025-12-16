package com.appmodulare.service;

import com.appmodulare.model.Attendance;

public interface IAttendanceService extends ICRUD<Attendance,Integer>{
    Attendance registerCheckIn(String identifier, Double latitude, Double longitude, String location) throws Exception;
    Attendance registerCheckOut(String identifier, Double latitude, Double longitude, String location) throws Exception;
}
