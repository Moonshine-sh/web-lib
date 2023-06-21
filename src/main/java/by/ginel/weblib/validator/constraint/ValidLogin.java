package by.ginel.weblib.validator.constraint;

import by.ginel.weblib.validator.impl.LoginConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = LoginConstraintValidator.class)
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface ValidLogin {

    String message() default "Invalid Login";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
