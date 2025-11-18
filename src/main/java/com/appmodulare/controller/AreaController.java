package com.appmodulare.controller;


import com.appmodulare.model.Area;
import com.appmodulare.service.IAreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apiareas")
@RequiredArgsConstructor
public class AreaController {

    private final IAreaService service;


    @GetMapping
    public ResponseEntity<List<Area>>findAll() throws Exception{

        List<Area>list= service.findAll();
        return  ResponseEntity.ok().body(list);

    }
    @GetMapping("{id}")
    public ResponseEntity<Area>findById( @PathVariable("id") Integer id) throws Exception{

        Area list= service.findById(id);
        return  ResponseEntity.ok().body(list);

    }

    @PostMapping
    public ResponseEntity<Area> create (@RequestBody Area area) throws Exception{

        Area obj = service.save(area);

        return ResponseEntity.ok().body(obj);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Area> update(@RequestBody Area area, @PathVariable("id") Integer id) throws Exception{
        Area obje = service.update(area ,id);

        return ResponseEntity.ok().body(obje);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
