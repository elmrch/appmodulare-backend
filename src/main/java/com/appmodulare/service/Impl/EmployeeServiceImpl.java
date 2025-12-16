package com.appmodulare.service.Impl;

import com.appmodulare.model.Area;
import com.appmodulare.model.Employee;
import com.appmodulare.model.Schedule;
import com.appmodulare.repositorie.IAreaRepository;
import com.appmodulare.repositorie.IEmployeeRepo;
import com.appmodulare.repositorie.IGenericRepo;
import com.appmodulare.repositorie.IScheduleRepo;
import com.appmodulare.service.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl extends CRUDImpl<Employee, Integer> implements IEmployeeService {

    private final IEmployeeRepo repo;
    //private final IAreaRepository areaRepo;
//private final IScheduleRepo scheduleRepo;
    private static final String UPLOAD_DIR = "C:\\Users\\Usuario\\Pictures\\Saved Pictures\\";


    @Override
    protected IGenericRepo<Employee, Integer> getRepo() {
        return repo;
    }

    @Override
    public String generateQrCode(Integer employeeId) throws Exception {
        Employee employee = findById(employeeId);
        String qrCode = "EMP-" + UUID.randomUUID().toString();
        employee.setQrCode(qrCode);
        employee.setQrEnabled(true);
        repo.save(employee);
        return qrCode;
    }

    @Override
    public Employee findByQrCode(String qrCode) {
        return repo.findByQrCode(qrCode)
                .orElseThrow(() -> new RuntimeException("Employee not found with QR: " + qrCode));
    }

    @Override
    public Employee findByDniOrQrCode(String identifier) {
        return repo.findByDniOrQrCode(identifier, identifier)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }
    @Override
    public Employee updatePhoto(Integer id, MultipartFile photo) throws Exception {
        // Buscar el empleado
        Employee employee = findById(id);

        if (photo != null && !photo.isEmpty()) {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = UUID.randomUUID().toString() + "_" + photo.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);

            Files.copy(photo.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            employee.setPhotoUrl("http://localhost:8080/uploads/" + fileName);



            return repo.save(employee);
        }

        throw new Exception("No photo provided");
    }
}