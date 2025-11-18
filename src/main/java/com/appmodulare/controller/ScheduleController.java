package com.appmodulare.controller;

import com.appmodulare.model.Area;
import com.appmodulare.model.Schedule;
import com.appmodulare.service.IAreaService;
import com.appmodulare.service.IScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {


    private final IScheduleService service;

    // GET todos los horarios
    @GetMapping
    public ResponseEntity<List<Schedule>> findAll() throws Exception{
        List<Schedule> list= service.findAll();
        return ResponseEntity.ok().body(list);
    }

    // GET horario por ID
    @GetMapping("{id}")
    public ResponseEntity<Schedule>findById(@PathVariable("id") Integer id) throws Exception{

        Schedule list= service.findById(id);
        return  ResponseEntity.ok().body(list);

    }

    // POST crear horario
    @PostMapping
    public ResponseEntity<Schedule> create(@RequestBody Schedule schedule) throws Exception {

        Schedule obj = service.save(schedule);

        return  ResponseEntity.ok().body(obj);


    }

    @PutMapping("/{id}")
    public ResponseEntity<Schedule> update(@RequestBody Schedule schedule, @PathVariable("id") Integer id) throws Exception{
        Schedule obje = service.update(schedule ,id);

        return ResponseEntity.ok().body(obje);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}