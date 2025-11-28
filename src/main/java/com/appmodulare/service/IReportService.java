package com.appmodulare.service;


import com.appmodulare.dto.AttendanceReportDTO;
import com.appmodulare.dto.ReportFilterDTO;

import java.util.List;
import java.util.Map;

public interface IReportService {

    /**
     * Genera reporte de asistencias según filtros
     */
    List<AttendanceReportDTO> generateAttendanceReport(ReportFilterDTO filter) throws Exception;

    /**
     * Obtiene estadísticas del reporte (total, puntuales, tardanzas)
     */
    Map<String, Long> getReportStatistics(ReportFilterDTO filter) throws Exception;
}