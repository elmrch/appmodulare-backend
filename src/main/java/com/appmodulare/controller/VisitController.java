package com.appmodulare.controller;

import com.appmodulare.dto.AttendanceDTO;
import com.appmodulare.dto.VisitDTO;
import com.appmodulare.model.Attendance;
import com.appmodulare.model.Visit;
import com.appmodulare.service.IAttendanceService;
import com.appmodulare.service.IVisitService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/visits")
@RequiredArgsConstructor
public class VisitController {

    private final IVisitService service;
    private final ModelMapper modelMapper;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")

    @GetMapping
    public ResponseEntity<List<VisitDTO>> findAll() throws Exception{
        //ModelMapper modelMapper = new ModelMapper();

        List<VisitDTO>list= service.findAll().stream()
                .map(this::convertToDto).toList();

        return  ResponseEntity.ok().body(list);

    }


    @PostMapping("/check-in")
    public ResponseEntity<VisitDTO> checkIn(@RequestBody VisitDTO dto) throws Exception {
        Visit v = service.registerCheckIn(convertToEntity(dto));
        return ResponseEntity.ok(convertToDto(v));
    }

    @PostMapping("/check-out")
    public ResponseEntity<Visit> checkOut(@RequestBody Map<String, String> body) throws Exception {
        String dni = body.get("dni");
        Visit visit = service.registerCheckOut(dni);
        return ResponseEntity.ok(visit);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    public Visit convertToEntity(VisitDTO dto){
        return modelMapper.map(dto,Visit.class);

    }

    public VisitDTO convertToDto(Visit visit) {
        VisitDTO dto = new VisitDTO();

        dto.setDate(visit.getDate() != null ? LocalDate.parse(visit.getDate().toString()) : null);
        dto.setId(Long.valueOf(visit.getId()));
        dto.setFullName(visit.getFullName());
        dto.setDni(visit.getDni());
        dto.setCompany(visit.getCompany());
        dto.setReason(visit.getReason());

        dto.setCheckIn(
                visit.getCheckIn() != null ? LocalDateTime.parse(visit.getCheckIn().toString()) : null
        );
        dto.setCheckOut(
                visit.getCheckOut() != null ? LocalDateTime.parse(visit.getCheckOut().toString()) : null
        );

        dto.setStatus(visit.getStatus());

        return dto;
    }


}
