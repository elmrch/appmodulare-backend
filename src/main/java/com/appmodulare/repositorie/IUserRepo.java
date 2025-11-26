package com.appmodulare.repositorie;

import com.appmodulare.model.User;


public interface IUserRepo extends IGenericRepo<User,Integer>{

    User findOneByUsername(String username);



}
