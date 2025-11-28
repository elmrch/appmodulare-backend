package com.appmodulare.service.Impl;

import com.appmodulare.dto.AttendanceReportDTO;
import com.appmodulare.dto.ReportFilterDTO;
import com.appmodulare.repositorie.IAttendanceRepo;
import com.appmodulare.service.IReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements IReportService {

    private final IAttendanceRepo attendanceRepo;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public List<AttendanceReportDTO> generateAttendanceReport(ReportFilterDTO filter) throws Exception {

        if (filter.getStartDate() == null || filter.getEndDate() == null) {
            throw new Exception("Las fechas son obligatorias");
        }

        LocalDate startDate;
        LocalDate endDate;

        try {
            startDate = LocalDate.parse(filter.getStartDate(), dateFormatter);
            endDate = LocalDate.parse(filter.getEndDate(), dateFormatter);
        } catch (Exception e) {
            throw new Exception("Formato de fecha inválido. Use: yyyy-MM-dd");
        }

        if (startDate.isAfter(endDate)) {
            throw new Exception("La fecha inicial no puede ser mayor que la fecha final");
        }

        String status = (filter.getStatus() == null || filter.getStatus().isEmpty())
                ? "all"
                : filter.getStatus();

        return attendanceRepo.findAttendanceReport(
                startDate,
                endDate,
                filter.getEmployeeId(),
                status
        );
    }

    @Override
    public Map<String, Long> getReportStatistics(ReportFilterDTO filter) throws Exception {

        if (filter.getStartDate() == null || filter.getEndDate() == null) {
            throw new Exception("Las fechas son obligatorias");
        }

        LocalDate startDate;
        LocalDate endDate;

        try {
            startDate = LocalDate.parse(filter.getStartDate(), dateFormatter);
            endDate = LocalDate.parse(filter.getEndDate(), dateFormatter);
        } catch (Exception e) {
            throw new Exception("Formato de fecha inválido. Use: yyyy-MM-dd");
        }

        if (startDate.isAfter(endDate)) {
            throw new Exception("La fecha inicial no puede ser mayor que la fecha final");
        }

        String status = (filter.getStatus() == null || filter.getStatus().isEmpty())
                ? "all"
                : filter.getStatus();

        Long total = attendanceRepo.countByFilters(
                startDate, endDate, filter.getEmployeeId(), status
        );

        Long punctual = attendanceRepo.countPunctualByFilters(
                startDate, endDate, filter.getEmployeeId()
        );

        Long late = attendanceRepo.countLateByFilters(
                startDate, endDate, filter.getEmployeeId()
        );

        Map<String, Long> stats = new HashMap<>();
        stats.put("total", total);
        stats.put("punctual", punctual);
        stats.put("late", late);

        return stats;
    }
}