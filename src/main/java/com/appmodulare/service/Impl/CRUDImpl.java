package com.appmodulare.service.Impl;

import com.appmodulare.dto.UserRegistrationDTO;
import com.appmodulare.excepction.ModelNotFoundException;
import com.appmodulare.model.User;
import com.appmodulare.model.Visit;
import com.appmodulare.repositorie.IGenericRepo;
import com.appmodulare.service.ICRUD;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class CRUDImpl<T, ID> implements ICRUD<T, ID> {

    protected abstract IGenericRepo<T, ID> getRepo();

    @Override
    public T save(T t) throws Exception {
        return getRepo().save(t);
    }

    @Override
    public T update(T t, ID id) throws Exception {

        T existing = this.findById(id);

        BeanUtils.copyProperties(t, existing, "id");

        return getRepo().save(existing);
    }

    @Override
    public List<T> findAll() throws Exception {
        return getRepo().findAll();
    }

    @Override
    public T findById(ID id) throws Exception {
        return getRepo().findById(id)
                .orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
    }

    @Override
    public void delete(ID id) throws Exception {
        getRepo().deleteById(id);
    }



    /*public abstract Visit registerCheckIn(Visit visit) throws Exception;

    public abstract Visit registerCheckOut(Integer id) throws Exception;*/
}
