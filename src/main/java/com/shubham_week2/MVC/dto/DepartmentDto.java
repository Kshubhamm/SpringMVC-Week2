package com.shubham_week2.MVC.dto;

import com.shubham_week2.MVC.annotations.PrimeAnnotation;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DepartmentDto {

    @NotNull(message = "Title cant be left blank")
    private String title;
    @AssertTrue(message = "Please make the active feild true")
    private Boolean isActive;
    @Past(message = "Chose a date of past")
    @NotNull(message = "Created at cant be null")
    private LocalDate createdAt;
    @Min(value = 1, message = "Atleast one eploy should be there")
    @Max(value = 100, message = "No more than 100 employees in a department")
    private Long numberOfEmployee;
    @FutureOrPresent(message ="Next townhall date should be in future")
    private LocalDate nextTownHall;
    @Email(message = "Chose a valid email")
    @NotBlank
    private String deptEmail;
    @PrimeAnnotation
    private Long securityNumber;


}
