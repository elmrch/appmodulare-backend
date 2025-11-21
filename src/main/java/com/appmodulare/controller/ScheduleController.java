package com.appmodulare.controller;

import com.appmodulare.dto.AreaDTO;
import com.appmodulare.dto.ScheduleDTO;
import com.appmodulare.model.Area;
import com.appmodulare.model.Schedule;
import com.appmodulare.service.IAreaService;
import com.appmodulare.service.IScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {


    private final IScheduleService service;
    private final ModelMapper modelMapper;


    // GET todos los horarios
    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> findAll() throws Exception{
        List<ScheduleDTO> list= service.findAll().stream()
        .map(this::convertToDto).toList();
        return ResponseEntity.ok().body(list);
    }

    // GET horario por ID
    @GetMapping("{id}")
    public ResponseEntity<ScheduleDTO>findById(@PathVariable("id") Integer id) throws Exception{

        ScheduleDTO list= convertToDto(service.findById(id).getSchedule());
        return  ResponseEntity.ok().body(list);

    }

    // POST crear horario
    @PostMapping
    public ResponseEntity<Schedule> create(@Valid  @RequestBody ScheduleDTO dto) throws Exception {

        Schedule obj = service.save(convertToEntity(dto));

        return  ResponseEntity.ok().body(obj);


    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleDTO> update(@Valid @RequestBody ScheduleDTO dto, @PathVariable("id") Integer id) throws Exception{
        Schedule obje = service.update(convertToEntity(dto),id);

        return ResponseEntity.ok().body(convertToDto(obje));

    }
    @GetMapping("/hateoas/{id}")

    public EntityModel<ScheduleDTO> findByIdHateoas(@PathVariable("id") Integer id) throws Exception {

        Schedule obj = service.findById(id).getSchedule();

        EntityModel<ScheduleDTO> resource = EntityModel.of(convertToDto(obj));
        //generar link informativo

        WebMvcLinkBuilder link1= linkTo(methodOn(ScheduleController.class).findById(obj.getId()));
        WebMvcLinkBuilder link2= linkTo(methodOn(ScheduleController.class).findAll());

        resource.add(link1.withRel("area-self-info"));

        resource.add(link2.withRel("all-patients"));
        return resource;


    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
    public  Schedule convertToEntity(ScheduleDTO dto){
        return modelMapper.map(dto,Schedule.class);

    }

    public  ScheduleDTO convertToDto(Schedule entity){
        return modelMapper.map(entity,ScheduleDTO.class);

    }
}