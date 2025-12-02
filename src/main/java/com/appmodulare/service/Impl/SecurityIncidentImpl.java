package com.appmodulare.service.Impl;

import com.appmodulare.model.SecurityIncident;
import com.appmodulare.repositorie.IGenericRepo;
import com.appmodulare.repositorie.ISecurityIncidentRepo;
import com.appmodulare.service.ISecurityIncidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SecurityIncidentImpl extends CRUDImpl<SecurityIncident,Integer> implements ISecurityIncidentService {

    private final ISecurityIncidentRepo repo;
    @Override
    protected IGenericRepo<SecurityIncident, Integer> getRepo() {
        return repo;
    }
    @Override
    public SecurityIncident save(SecurityIncident incident) throws Exception {

        // Obtener el rol automáticamente ⭐
        String role = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .findFirst()
                .get()
                .getAuthority(); // "ROLE_ADMIN" o "ROLE_USER"

        // Establecer fecha y rol automáticamente
        incident.setDateTime(LocalDateTime.now());
        incident.setReportedBy(role);

        return super.save(incident); // Llama al método save del padre (CRUDImpl)
    }
}
