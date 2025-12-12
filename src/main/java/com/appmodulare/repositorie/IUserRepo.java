package com.appmodulare.repositorie;

import com.appmodulare.model.User;

import java.util.Optional;


public interface IUserRepo extends IGenericRepo<User,Integer>{

    User findOneByUsername(String username);
    Optional<User> findByUsername(String username);




}
