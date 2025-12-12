package com.appmodulare.service.Impl;

import com.appmodulare.dto.UserRegistrationDTO;
import com.appmodulare.excepction.ModelNotFoundException;
import com.appmodulare.model.Role;
import com.appmodulare.model.User;
import com.appmodulare.repositorie.IGenericRepo;
import com.appmodulare.repositorie.IRoleRepo;
import com.appmodulare.repositorie.IUserRepo;
import com.appmodulare.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends CRUDImpl<User, Integer> implements IUserService {

    private final IUserRepo repo;
    private final IRoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder; // ✅ Para encriptar contraseñas

    @Override
    protected IGenericRepo<User, Integer> getRepo() {
        return repo;
    }

    @Override
    @Transactional
    public User createUserWithRole(UserRegistrationDTO dto) throws Exception {
        // Validar que el username no exista
        if (repo.findByUsername(dto.getUsername()).isPresent()) {
            throw new Exception("El username ya existe");
        }

        // Crear nuevo usuario
        User user = new User();
        user.setFullName(dto.getFullName());
        user.setUsername(dto.getUsername());

        // ✅ ENCRIPTAR la contraseña con BCrypt
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        user.setEnabled(dto.getEnabled() != null ? dto.getEnabled() : true);
        user.setPhotoUrl(dto.getPhotoUrl());

        // Asignar rol
        Role role = roleRepo.findById(dto.getRoleId())
                .orElseThrow(() -> new ModelNotFoundException("Rol no encontrado con ID: " + dto.getRoleId()));

        user.setRoles(List.of(role));

        // Guardar usuario
        return repo.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repo.findByUsername(username);
    }

    @Transactional
    @Override
    public User update(Integer id, UserRegistrationDTO dto) throws Exception {
        User user = repo.findById(id)
                .orElseThrow(() -> new ModelNotFoundException("Usuario no encontrado con ID: " + id));

        // Actualizar campos
        if (dto.getFullName() != null && !dto.getFullName().trim().isEmpty()) {
            user.setFullName(dto.getFullName());
        }

        if (dto.getUsername() != null && !dto.getUsername().equals(user.getUsername())) {
            if (repo.findByUsername(dto.getUsername()).isPresent()) {
                throw new Exception("El username ya existe");
            }
            user.setUsername(dto.getUsername());
        }

        // Solo actualizar password si se proporciona
        if (dto.getPassword() != null && !dto.getPassword().trim().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        if (dto.getEnabled() != null) {
            user.setEnabled(dto.getEnabled());
        }

        if (dto.getPhotoUrl() != null) {
            user.setPhotoUrl(dto.getPhotoUrl());
        }

        // ✅ Ahora esto funcionará porque roles es un ArrayList mutable
        if (dto.getRoleId() != null) {
            Role role = roleRepo.findById(dto.getRoleId())
                    .orElseThrow(() -> new ModelNotFoundException("Rol no encontrado con ID: " + dto.getRoleId()));

            user.getRoles().clear(); // ✅ Funciona porque la lista es mutable
            user.getRoles().add(role);
        }

        return repo.save(user);
    }
    @Override
    public List<User> readAll() {
        return repo.findAll();
    }

    @Override
    public User readById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new ModelNotFoundException("Usuario no encontrado con ID: " + id));
    }
}