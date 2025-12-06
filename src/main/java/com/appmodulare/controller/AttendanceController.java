package com.appmodulare.controller;

import com.appmodulare.dto.AttendanceDTO;
import com.appmodulare.model.Attendance;
import com.appmodulare.service.IAttendanceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/attendances")
@RequiredArgsConstructor
public class AttendanceController {

    private final IAttendanceService service;
    private final ModelMapper modelMapper;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping
    public ResponseEntity<List<AttendanceDTO>> findAll() throws Exception {
        List<AttendanceDTO> list = service.findAll().stream()
                .map(this::convertToDto).toList();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/check-in")
    public ResponseEntity<?> checkIn(@RequestBody Map<String, Object> body) throws Exception {
        try {
            String dni = (String) body.get("dni");
            Double latitude = body.get("latitude") != null ?
                    Double.parseDouble(body.get("latitude").toString()) : null;
            Double longitude = body.get("longitude") != null ?
                    Double.parseDouble(body.get("longitude").toString()) : null;
            String location = (String) body.getOrDefault("location", "No especificado");

            Attendance attendance = service.registerCheckIn(dni, latitude, longitude, location);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Entrada registrada correctamente",
                    "attendance", convertToDto(attendance),
                    "locationValid", attendance.getLocationValid() != null ? attendance.getLocationValid() : true,
                    "distance", attendance.getDistanceFromOffice() != null ?
                            String.format("%.2f metros", attendance.getDistanceFromOffice()) : "N/A"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    @PostMapping("/check-out")
    public ResponseEntity<?> checkOut(@RequestBody Map<String, Object> body) throws Exception {
        try {
            String dni = (String) body.get("dni");
            Double latitude = body.get("latitude") != null ?
                    Double.parseDouble(body.get("latitude").toString()) : null;
            Double longitude = body.get("longitude") != null ?
                    Double.parseDouble(body.get("longitude").toString()) : null;
            String location = (String) body.getOrDefault("location", "No especificado");

            Attendance attendance = service.registerCheckOut(dni, latitude, longitude, location);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Salida registrada correctamente",
                    "attendance", convertToDto(attendance),
                    "totalHours", attendance.getTotalHoursFormatted()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    public Attendance convertToEntity(AttendanceDTO dto) {
        return modelMapper.map(dto, Attendance.class);
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

        // ========== MAPEAR GPS ==========
        dto.setCheckInLatitude(att.getCheckInLatitude());
        dto.setCheckInLongitude(att.getCheckInLongitude());
        dto.setCheckInLocation(att.getCheckInLocation());

        dto.setCheckOutLatitude(att.getCheckOutLatitude());
        dto.setCheckOutLongitude(att.getCheckOutLongitude());
        dto.setCheckOutLocation(att.getCheckOutLocation());

        dto.setLocationValid(att.getLocationValid());
        dto.setDistanceFromOffice(att.getDistanceFromOffice());

        return dto;
    }
}