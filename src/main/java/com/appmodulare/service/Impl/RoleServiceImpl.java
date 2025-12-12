package com.appmodulare.service.Impl;

import com.appmodulare.model.Role;
import com.appmodulare.repositorie.IGenericRepo;
import com.appmodulare.repositorie.IRoleRepo;
import com.appmodulare.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends CRUDImpl<Role, Integer> implements IRoleService {

    private final IRoleRepo  iRoleRepo;
    @Override
    protected IGenericRepo<Role, Integer> getRepo() {
        return iRoleRepo;
    }
}
