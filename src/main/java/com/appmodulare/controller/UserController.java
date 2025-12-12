package com.appmodulare.controller;


import com.appmodulare.dto.EmployeeDTO;
import com.appmodulare.dto.UserRegistrationDTO;
import com.appmodulare.model.Employee;
import com.appmodulare.model.User;
import com.appmodulare.service.IRoleService;
import com.appmodulare.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PreAuthorize("hasAuthority('ADMIN')") // ✅ Solo ADMIN
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserRegistrationDTO dto) {
        try {
            User newUser = userService.createUserWithRole(dto);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userService.readAll();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        try {
            User user = userService.readById(id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Integer id,
                                       @RequestBody UserRegistrationDTO dto) {
        try {
            User update = userService.update(id, dto);
            return ResponseEntity.ok(update);
        } catch (Exception e) {
            e.printStackTrace(); // ✅ Ver el error en consola
            System.err.println("Error al actualizar: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        try {
            userService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}