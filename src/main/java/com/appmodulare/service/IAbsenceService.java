package com.appmodulare.service;


import com.appmodulare.dto.AbsenceFilterDTO;
import com.appmodulare.dto.AbsenceReportDTO;

import java.util.List;

public interface IAbsenceService {
    List<AbsenceReportDTO> findMissingAttendances(AbsenceFilterDTO filter) throws Exception;
}