package com.shubham_week2.MVC.services;

import com.shubham_week2.MVC.dto.DepartmentDto;
import com.shubham_week2.MVC.entities.DepartmentEntity;
import com.shubham_week2.MVC.exceptions.ResourceNotFoundException;
import com.shubham_week2.MVC.repositories.DepartmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    DepartmentRepository departmentRepository;
    ModelMapper mapper;


    public DepartmentService(ModelMapper mapper, DepartmentRepository departmentRepository) {
        this.mapper = mapper;
        this.departmentRepository = departmentRepository;
    }

    public  Boolean deleteById(Long id) {
        if(!checkDeptexist(id))
            throw new ResourceNotFoundException("Department doesnt exist to be deleted");

        departmentRepository.deleteById(id);
        return true;
    }


    public List<DepartmentDto> getDepartments() {
       return departmentRepository.findAll()
               .stream()
               .map(a->mapper.map(a,DepartmentDto.class))
               .collect(Collectors.toList());

    }

    public DepartmentDto createDepartment(DepartmentDto deptDto) {
        DepartmentEntity newDept = mapper.map(deptDto,DepartmentEntity.class);
        departmentRepository.save(newDept);
        return deptDto;

    }

    public Boolean checkDeptexist(Long id){
        return departmentRepository.existsById(id);
    }

    public Optional<DepartmentDto> getDepartmentsById(Long id) {
        if(!checkDeptexist(id))
            throw new ResourceNotFoundException("Department doesnt exist");

        Optional<DepartmentDto> dept = departmentRepository.findById(id).map(a->mapper.map(a,DepartmentDto.class));
    return dept;


    }

    public DepartmentDto updateDepartment(Long id, DepartmentDto inpDto) {
        if(!checkDeptexist(id))
            throw new ResourceNotFoundException("No department exist to update");
        DepartmentEntity dept = mapper.map(inpDto,DepartmentEntity.class);
        dept.setId(id);
        departmentRepository.save(dept);
        return mapper.map(dept,DepartmentDto.class);

    }
}
