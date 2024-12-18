package com.shubham_week2.MVC.controllers;

import com.shubham_week2.MVC.dto.EmployeeDto;
import com.shubham_week2.MVC.exceptions.ResourceNotFoundException;
import com.shubham_week2.MVC.services.EmployeeService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping(path = "/employee")
public class EmployeeControler {


    private final ModelMapper mapper;
    private final EmployeeService employeeService;

    public EmployeeControler(ModelMapper mapper, EmployeeService employeeService) {

        this.mapper = mapper;
        this.employeeService = employeeService;
    }



    @GetMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable(name = "employeeId") Long id){

        Optional<EmployeeDto> emp = employeeService.getEmpById(id);

        return emp
                .map(a->ResponseEntity.ok(a))
                .orElseThrow(() -> new ResourceNotFoundException("No employee exists with this id"));

    }

//    @ExceptionHandler(NoSuchElementException.class)
//    public ResponseEntity<String> noEmployFound(NoSuchElementException noSuchElementException){
//        return new ResponseEntity<>("Employee not found",HttpStatus.NOT_FOUND);
//    }



    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(){
        return ResponseEntity.ok(employeeService.getEmployees());
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody @Valid EmployeeDto inpDto){
        EmployeeDto savedEmployee =  employeeService.saveEmployee(inpDto);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);


    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable(name = "id") Long id ,@RequestBody @Valid EmployeeDto inpDto){
        return ResponseEntity.ok(employeeService.updateEmployee(id,inpDto));


    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<EmployeeDto> patchUser(@PathVariable(name = "id") Long id, @RequestBody Map<String,Object> updates){
        return ResponseEntity.ok(employeeService.patchUser(id,updates));
    }

    @DeleteMapping(path = "/email/{email}")
   public ResponseEntity<Boolean> DeleteByID(@PathVariable @Valid String email){

        Boolean emp = employeeService.deleteById(email);
        if(emp)
            return ResponseEntity.ok(true);
        else return ResponseEntity.notFound().build();


    }

    @DeleteMapping(path = "/id/{id}")
    public ResponseEntity<Boolean> DeleteId(@PathVariable @Valid Long id){

        Boolean emp = employeeService.deleteId(id);
        if(emp)
            return ResponseEntity.ok(true);
        else return ResponseEntity.notFound().build();


    }










}
