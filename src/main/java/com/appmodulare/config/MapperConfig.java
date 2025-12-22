package com.appmodulare.config;


import com.appmodulare.dto.EmployeeDTO;
import com.appmodulare.model.Employee;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {


    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();


        modelMapper.addMappings(new PropertyMap<EmployeeDTO, Employee>() {
            @Override
            protected void configure() {
                map().getArea().setId(source.getAreaId());
                map().getSchedule().setId(source.getScheduleId());
            }
        });


        modelMapper.addMappings(new PropertyMap<Employee, EmployeeDTO>() {
            @Override
            protected void configure() {
                map(source.getArea().getId(), destination.getAreaId());
                map(source.getSchedule().getId(), destination.getScheduleId());
            }
        });

        return modelMapper;
    }
}
