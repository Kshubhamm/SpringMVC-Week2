package com.shubham_week2.MVC.advices;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
public class ApiError {
    private String message;
    private List<String> subError;
    private HttpStatus httpStatus;

}
