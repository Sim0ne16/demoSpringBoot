package org.example.demo2.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class GradoValidoValidator implements ConstraintValidator<GradoValido, String> {

    private final List<String> gradiValidi = Arrays.asList(
            "Prima", "Seconda", "Terza", "Quarta", "Quinta");

    @Override
    public boolean isValid(String valore, ConstraintValidatorContext context) {
        if (valore == null || valore.isBlank()) {
            return false;
        }
        return gradiValidi.contains(valore);
    }
}