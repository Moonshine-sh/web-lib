package by.ginel.weblib.validator.impl;

import by.ginel.weblib.validator.constraint.ValidLogin;
import com.google.common.base.Joiner;
import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class LoginConstraintValidator implements ConstraintValidator<ValidLogin, String> {
    @Override
    public void initialize(ValidLogin constraintAnnotation) {
    }

    @Override
    public boolean isValid(String login, ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(4, 20),
                new IllegalCharacterRule(EnglishCharacterData.Special.getCharacters().toCharArray()),
                new WhitespaceRule())
        );

        RuleResult result = validator.validate(new PasswordData(login));
        if (result.isValid())
            return true;
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                Joiner.on(",").join(validator.getMessages(result)))
                .addConstraintViolation();
        return false;
    }
}
