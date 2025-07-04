package org.example.demo2.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Documented
@Constraint(validatedBy = IniziaConMaiuscolaValidator.class)
@Target({ FIELD })
@Retention(RUNTIME)
public @interface IniziaConMaiuscola {
    String message() default "Il valore deve iniziare con una lettera maiuscola!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}