package org.example.demo2.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class MaggiorenneValidator implements ConstraintValidator<Maggiorenne, LocalDate>{

    @Override
    public boolean isValid(LocalDate dataNascita, ConstraintValidatorContext context) {
       if (dataNascita == null) {
            return false; 
    }
    return Period.between(dataNascita, LocalDate.now()).getYears() >= 18;
    }
}
