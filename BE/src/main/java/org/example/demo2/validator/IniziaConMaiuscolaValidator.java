package org.example.demo2.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IniziaConMaiuscolaValidator implements ConstraintValidator<IniziaConMaiuscola, String> {

@Override
    public boolean isValid(String valore, ConstraintValidatorContext context) {
        if (valore == null || valore.isBlank()) {
            return false;
        }
        char primaLettera = valore.charAt(0);
        return Character.isUpperCase(primaLettera);
    }


}
