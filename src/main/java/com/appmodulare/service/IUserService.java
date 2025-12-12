package com.appmodulare.service;

import com.appmodulare.dto.UserRegistrationDTO;
import com.appmodulare.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface IUserService extends ICRUD<User,Integer>{
    // Métodos específicos para usuarios
    User createUserWithRole(UserRegistrationDTO dto) throws Exception;

    Optional<User> findByUsername(String username);

    @Transactional
    User update(Integer id, UserRegistrationDTO dto) throws Exception;

    List<User> readAll();
    User readById(Integer id);

}
