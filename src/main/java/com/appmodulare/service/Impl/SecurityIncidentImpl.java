package com.appmodulare.service.Impl;

import com.appmodulare.model.SecurityIncident;
import com.appmodulare.model.User;
import com.appmodulare.repositorie.IGenericRepo;
import com.appmodulare.repositorie.ISecurityIncidentRepo;
import com.appmodulare.repositorie.IUserRepo;
import com.appmodulare.service.ISecurityIncidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SecurityIncidentImpl extends CRUDImpl<SecurityIncident,Integer> implements ISecurityIncidentService {

    private final ISecurityIncidentRepo repo;
    private final IUserRepo userRepo;

    @Override
    protected IGenericRepo<SecurityIncident, Integer> getRepo() {
        return repo;
    }
    @Override
    public SecurityIncident save(SecurityIncident incident) throws Exception {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();


        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        incident.setDateTime(LocalDateTime.now());
        incident.setReportedBy(username);

        return repo.save(incident);
    }

    @Override
    public SecurityIncident update(SecurityIncident incident, Integer id) throws Exception {
        SecurityIncident existing = this.findById(id);
        BeanUtils.copyProperties(incident, existing, "id", "dateTime", "reportedBy");
        return getRepo().save(existing);
    }
}
