package com.appmodulare.controller;

import com.appmodulare.dto.AreaDTO;
import com.appmodulare.dto.AttendanceDTO;
import com.appmodulare.dto.AttendanceResponseDTO;
import com.appmodulare.model.Area;
import com.appmodulare.model.Attendance;
import com.appmodulare.service.IAttendanceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/attendances")
@RequiredArgsConstructor
public class AttendanceController {

    private final IAttendanceService service;
    private final ModelMapper modelMapper;


    @GetMapping
    public ResponseEntity<List<AttendanceDTO>>findAll() throws Exception{
        //ModelMapper modelMapper = new ModelMapper();

        List<AttendanceDTO>list= service.findAll().stream()
                .map(this::convertToDto).toList();

        return  ResponseEntity.ok().body(list);

    }

    @PostMapping("/check-in")
    public ResponseEntity<Attendance> checkIn(@RequestBody Map<String, String> body) throws Exception {
        String dni = body.get("dni");
        Attendance attendance = service.registerCheckIn(dni);
        return ResponseEntity.ok(attendance);
    }

    @PostMapping("/check-out")
    public ResponseEntity<Attendance> checkOut(@RequestBody Map<String, String> body) throws Exception {
        String dni = body.get("dni");
        Attendance attendance = service.registerCheckOut(dni);
        return ResponseEntity.ok(attendance);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    public Attendance convertToEntity(AttendanceDTO dto){
        return modelMapper.map(dto,Attendance.class);

    }

    public AttendanceDTO convertToDto(Attendance att) {

        AttendanceDTO dto = new AttendanceDTO();

        dto.setId(att.getId());
        dto.setEmployeeId(att.getEmployee().getId());
        dto.setDni(att.getEmployee().getDni());
        dto.setFirstName(att.getEmployee().getFirstName());
        dto.setLastName(att.getEmployee().getLastName());

        dto.setDate(att.getDate() != null ? att.getDate().toString() : null);
        dto.setCheckIn(att.getCheckIn() != null ? att.getCheckIn().toString() : null);
        dto.setCheckOut(att.getCheckOut() != null ? att.getCheckOut().toString() : null);

        dto.setTotalHours(att.getTotalHours());
        dto.setTotalHoursFormatted(att.getTotalHoursFormatted());
        dto.setStatus(att.getStatus());

        return dto;
    }


}
