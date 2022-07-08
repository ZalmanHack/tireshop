package com.zalmanhack.tireshop.utils.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ComplianceCompositionsValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ComplianceCompositions {
    String message() default "Data consistency in the \"composite\" field is not respected";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
