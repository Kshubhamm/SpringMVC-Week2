package com.shubham_week2.MVC.services;

import com.shubham_week2.MVC.dto.EmployeeDto;
import com.shubham_week2.MVC.entities.EmployeeEntity;
import com.shubham_week2.MVC.exceptions.ResourceNotFoundException;
import com.shubham_week2.MVC.repositories.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
import org.springframework.boot.web.embedded.netty.NettyWebServer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    //BEAN injected
    private final ModelMapper mapper;
    private final EmployeeRepository employeeRepository;


    public EmployeeService(ModelMapper mapper, EmployeeRepository employeeRepository) {
        this.mapper = mapper;
        this.employeeRepository = employeeRepository;
    }

    public Optional<EmployeeDto> getEmpById(long id){

       return employeeRepository.findById(id)
               .map(emp->mapper.map(emp,EmployeeDto.class));

}

    public List<EmployeeDto> getEmployees(){
        List<EmployeeEntity> employees = employeeRepository.findAll();
       return  employees
                .stream()
                .map(a->mapper.map(a,EmployeeDto.class))
                .collect(Collectors.toList());
    }

    public EmployeeDto saveEmployee(EmployeeDto inpDto){
        //convert it to entity
        EmployeeEntity newEmployee = mapper.map(inpDto,EmployeeEntity.class);
        return mapper.map(employeeRepository.save(newEmployee),EmployeeDto.class);

    }

    public Boolean isExistById(Long id){
        return employeeRepository.existsById(id);
    }



    public EmployeeDto updateEmployee(Long id,EmployeeDto inpEmployee){
        EmployeeEntity emp = mapper.map(inpEmployee,EmployeeEntity.class);
        if(!isExistById(id))
            throw new ResourceNotFoundException("Employee with this id, does not exist, check before updating");

        emp.setId(id);
        employeeRepository.save(emp);
        return mapper.map(emp,EmployeeDto.class);



    }
    @Transactional
    public boolean deleteById(String email) {

        if(!employeeRepository.existsByEmail(email))
            return false;

        employeeRepository.deleteByEmail(email);
        return true;
    }

    public EmployeeDto patchUser(Long id, Map<String, Object> updates) {
        //Need to use reflections here --> very simple concept yet effective
        EmployeeEntity emp = mapper.map(getEmpById(id), EmployeeEntity.class);

        updates.forEach((fieldName,update)->{
            Field requiredField = ReflectionUtils.findField(EmployeeEntity.class,fieldName);
            requiredField.setAccessible(true);
            ReflectionUtils.setField(requiredField,emp,update);

        });
        emp.setId(id);


        return mapper.map(employeeRepository.save(emp), EmployeeDto.class);




    }

    public Boolean deleteId(Long id) {
        if(!employeeRepository.existsById(id))
            return false;
        employeeRepository.deleteById(id);
        return true;
    }
}

