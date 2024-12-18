package com.shubham_week2.MVC.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PrimeValidator implements ConstraintValidator<PrimeAnnotation,Long> {

    @Override
    public boolean isValid(Long number, ConstraintValidatorContext constraintValidatorContext) {
        if (number <= 1) {
            return false;
        }

        // Check for divisibility up to the square root of the number
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;

    }
}
