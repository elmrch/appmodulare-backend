package com.appmodulare.controller;

import com.appmodulare.dto.SecurityIncidentDTO;
import com.appmodulare.model.SecurityIncident;
import com.appmodulare.service.ISecurityIncidentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping("/securitys")
@RequiredArgsConstructor
public class SecurityIncidentController {

    private final ISecurityIncidentService service;
    private final ModelMapper modelMapper;


    @GetMapping
    public ResponseEntity<List<SecurityIncidentDTO>> findAll() throws Exception{
        //ModelMapper modelMapper = new ModelMapper();

        List<SecurityIncidentDTO>list= service.findAll().stream()
                .map(this::convertToDto).toList();

        return  ResponseEntity.ok().body(list);

    }


    @PostMapping
    public ResponseEntity<SecurityIncident> create (@Valid @RequestBody SecurityIncidentDTO dto) throws Exception{

        SecurityIncident obj = service.save(convertToEntity(dto));

        return ResponseEntity.ok().body(obj);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SecurityIncidentDTO> update(@Valid @RequestBody SecurityIncidentDTO dto, @PathVariable("id") Integer id) throws Exception{
        SecurityIncident obje = service.update(convertToEntity(dto),id);

        return ResponseEntity.ok().body(convertToDto(obje));

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

   /* public SecurityIncident convertToEntity(SecurityIncidentDTO dto){
        return modelMapper.map(dto,SecurityIncident.class);

    }

    public SecurityIncidentDTO convertToDto(SecurityIncident securityIncident) {
        SecurityIncidentDTO dto = new SecurityIncidentDTO();

        dto.setId(Long.valueOf(securityIncident.getId()));
       dto.setDateTime(securityIncident.getDateTime());
        dto.setSeverity(securityIncident.getSeverity().toString()); // Enum → String
        dto.setIncident(securityIncident.getIncident());
        dto.setTitle(securityIncident.getTitle());
        dto.setReportedBy(securityIncident.getReportedBy());


        return dto;
    }
*/
   public  SecurityIncident convertToEntity(SecurityIncidentDTO dto){
       return modelMapper.map(dto,SecurityIncident.class);

   }

    public  SecurityIncidentDTO convertToDto(SecurityIncident entity){
        return modelMapper.map(entity,SecurityIncidentDTO.class);

    }

}
