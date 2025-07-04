package org.example.demo2.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = GradoValidoValidator.class)
@Target({ FIELD })
@Retention(RUNTIME)
public @interface GradoValido {
    String message() default "Grado non valido!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}