package com.appmodulare.controller;

import com.appmodulare.dto.AreaDTO;
import com.appmodulare.dto.EmployeeDTO;
import com.appmodulare.model.Area;
import com.appmodulare.model.Employee;
import com.appmodulare.service.IAreaService;
import com.appmodulare.service.IEmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/employes")
@RequiredArgsConstructor
public class EmployeeController {

    private final IEmployeeService service;
    private final ModelMapper modelMapper;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> findAll() throws Exception{
        //ModelMapper modelMapper = new ModelMapper();

        List<EmployeeDTO>list= service.findAll().stream()
                .map(this::convertToDto).toList();

        return  ResponseEntity.ok().body(list);

    }
    @GetMapping("{id}")
    public ResponseEntity<EmployeeDTO> findById(@PathVariable("id") Integer id) throws Exception {

        Employee employee = service.findById(id);         // recuperas el employee

        EmployeeDTO dto = convertToDto(employee);         // conviertes employee → dto

        return ResponseEntity.ok(dto);
    }


    @PostMapping
    public ResponseEntity<Employee> create (@Valid @RequestBody EmployeeDTO dto) throws Exception{
        Employee obj = service.save(convertToEntity(dto));
       /* URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();*/



        return ResponseEntity.ok().body(obj);
    }
    @PostMapping("/{id}/photo")
    public ResponseEntity<EmployeeDTO> uploadPhoto(
            @PathVariable("id") Integer id,
            @RequestParam("photo") MultipartFile photo) throws Exception {

        Employee employee = service.updatePhoto(id, photo);

        EmployeeDTO dto = convertToDto(employee);

        dto.setPhotoUrl(employee.getPhotoUrl());

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> update( @Valid @RequestBody EmployeeDTO dto, @PathVariable("id") Integer id) throws Exception{
        Employee obje = service.update(convertToEntity(dto),id);

        return ResponseEntity.ok().body(convertToDto(obje));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
    // Generar código QR para un empleado
    @PostMapping("/{id}/generate-qr")
    public ResponseEntity<String> generateQrCode(@PathVariable("id") Integer id) throws Exception {
        String qrCode = service.generateQrCode(id);
        return ResponseEntity.ok(qrCode);
    }

    // Buscar empleado por código QR
    @GetMapping("/qr/{qrCode}")
    public ResponseEntity<EmployeeDTO> findByQrCode(@PathVariable("qrCode") String qrCode) throws Exception {
        Employee employee = service.findByQrCode(qrCode);
        return ResponseEntity.ok(convertToDto(employee));
    }

    // Buscar empleado por DNI o QR (útil para el registro de asistencia)
    @GetMapping("/search/{identifier}")
    public ResponseEntity<EmployeeDTO> findByDniOrQr(@PathVariable("identifier") String identifier) throws Exception {
        Employee employee = service.findByDniOrQrCode(identifier);
        return ResponseEntity.ok(convertToDto(employee));
    }

    /*@GetMapping("/hateoas/{id}")

    public EntityModel<EmployeeDTO> findByIdHateoas(@PathVariable("id") Integer id) throws Exception {

        Employee obj = service.findById(id).gete();

        EntityModel<AreaDTO> resource = EntityModel.of(convertToDto(obj));
        //generar link informativo

        WebMvcLinkBuilder link1= linkTo(methodOn(AreaController.class).findById(obj.getId()));
        WebMvcLinkBuilder link2= linkTo(methodOn(AreaController.class).findAll());

        resource.add(link1.withRel("area-self-info"));

        resource.add(link2.withRel("all-patients"));
        return resource;


    }*/
    public  Employee convertToEntity(EmployeeDTO dto){
        return modelMapper.map(dto,Employee.class);

    }

    public  EmployeeDTO convertToDto(Employee entity){
        return modelMapper.map(entity,EmployeeDTO.class);

    }
}
