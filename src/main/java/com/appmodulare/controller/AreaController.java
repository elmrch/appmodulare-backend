package com.appmodulare.controller;


import com.appmodulare.dto.AreaDTO;
import com.appmodulare.model.Area;
import com.appmodulare.service.IAreaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.EntityModel;

import  static  org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apiareas")
@RequiredArgsConstructor
public class AreaController {

    private final IAreaService service;
    private final ModelMapper modelMapper;


    @GetMapping
    public ResponseEntity<List<AreaDTO>>findAll() throws Exception{
        //ModelMapper modelMapper = new ModelMapper();

        List<AreaDTO>list= service.findAll().stream()
                .map(this::convertToDto).toList();

        return  ResponseEntity.ok().body(list);

    }
    @GetMapping("{id}")
    public ResponseEntity<AreaDTO>findById( @PathVariable("id") Integer id) throws Exception{

        AreaDTO list= convertToDto(service.findById(id).getArea());
        return  ResponseEntity.ok().body(list);

    }

    @PostMapping
    public ResponseEntity<Area> create (@Valid @RequestBody AreaDTO dto) throws Exception{

        Area obj = service.save(convertToEntity(dto));

        return ResponseEntity.ok().body(obj);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AreaDTO> update( @Valid @RequestBody AreaDTO dto, @PathVariable("id") Integer id) throws Exception{
        Area obje = service.update(convertToEntity(dto),id);

        return ResponseEntity.ok().body(convertToDto(obje));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hateoas/{id}")

    public EntityModel<AreaDTO> findByIdHateoas(@PathVariable("id") Integer id) throws Exception {

        Area obj = service.findById(id).getArea();

        EntityModel<AreaDTO> resource = EntityModel.of(convertToDto(obj));
        //generar link informativo

        WebMvcLinkBuilder link1= linkTo(methodOn(AreaController.class).findById(obj.getId()));
        WebMvcLinkBuilder link2= linkTo(methodOn(AreaController.class).findAll());

        resource.add(link1.withRel("area-self-info"));

        resource.add(link2.withRel("all-patients"));
        return resource;


    }
    public  Area convertToEntity(AreaDTO dto){
        return modelMapper.map(dto,Area.class);

    }

    public  AreaDTO convertToDto(Area entity){
        return modelMapper.map(entity,AreaDTO.class);

    }
}
