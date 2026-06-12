package mate.academy.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Constraint(validatedBy = EmailValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
public @interface Email {
    String message() default "invalid format email";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
