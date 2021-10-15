package com.example.musicdb.model.validator;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, Object> {
    private String password;
    private String confirmPassword;
    private String message;

    @Override
    public void initialize(PasswordsMatch constraintAnnotation) {
        this.password = constraintAnnotation.first();
        this.confirmPassword = constraintAnnotation.second();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object password, ConstraintValidatorContext context) {

        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(password);

        Object passwordValue = beanWrapper.getPropertyValue(this.password);
        Object confirmPasswordValue = beanWrapper.getPropertyValue(this.confirmPassword);

        boolean valid;
        if (passwordValue == null) {
            valid = confirmPasswordValue == null;
        } else {
            valid = passwordValue.equals(confirmPasswordValue);
        }

        if (!valid) {
            context.buildConstraintViolationWithTemplate(message)
                        .addPropertyNode(this.password)
                        .addConstraintViolation()
                    .buildConstraintViolationWithTemplate(message)
                        .addPropertyNode(this.confirmPassword)
                        .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }
}
