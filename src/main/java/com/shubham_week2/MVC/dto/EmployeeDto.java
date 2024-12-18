package com.shubham_week2.MVC.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

//POJO CLASS
@Data
public class EmployeeDto {


@NotNull(message = "Name feild is required")
   private String name;

@Email(message = "Give a valid email")
   private String email;

@Past(message = "Date of past only allowed")
   private LocalDate dateOfJoining;

@Max(60)
@Min(18)
   private Integer age;



}
