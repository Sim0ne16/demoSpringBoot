package org.example.demo2.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IdentificativoValidoValidator implements ConstraintValidator<IdentificativoValido, String> {

    @Override
    public boolean isValid(String valore, ConstraintValidatorContext context) {
        if (valore == null || valore.isBlank()) {
            return false;
        }
        return valore.length() == 1 && Character.isLetter(valore.charAt(0)) && Character.isUpperCase(valore.charAt(0));
    }
}
/*
 * null o vuoto ➜ blocca
 * Se la lunghezza è diversa da 1 ➜ blocca
 * Se non è lettera ➜ blocca
 * Se non è maiuscola ➜ blocca
 * 
 * 
 */