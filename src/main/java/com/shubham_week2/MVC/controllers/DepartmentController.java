package com.shubham_week2.MVC.controllers;

import com.shubham_week2.MVC.dto.DepartmentDto;
import com.shubham_week2.MVC.exceptions.ResourceNotFoundException;
import com.shubham_week2.MVC.services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dept")
public class DepartmentController {

    DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDto>>getAllDepartment(){
        return ResponseEntity.ok(departmentService.getDepartments());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DepartmentDto> getAllDepartment(@PathVariable(name = "id") Long id){
        Optional<DepartmentDto> dept =  departmentService.getDepartmentsById(id);
        return dept
                .map(a->ResponseEntity.ok(a))
                .orElseThrow(()->new ResourceNotFoundException("No id there"));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<DepartmentDto> updateDepartment(@PathVariable(name = "id") Long id,@RequestBody @Valid DepartmentDto inpDto){
        return new ResponseEntity<>(departmentService.updateDepartment(id,inpDto),HttpStatus.CREATED);
    }


    @PostMapping
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody @Valid DepartmentDto DeptDto){
        return new ResponseEntity<>(departmentService.createDepartment(DeptDto), HttpStatus.CREATED);

    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id){
       Boolean isDeleted = departmentService.deleteById(id);
       if(isDeleted)
           return ResponseEntity.ok(isDeleted);
       return
               ResponseEntity.notFound().build();
    }
}
