package com.ecommerce.project.annotation;

import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class PasswordValidator implements ConstraintValidator<ValidPassword, String>{

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		if(password == null) {
			context.disableDefaultConstraintViolation();
	        context.buildConstraintViolationWithTemplate("Password cannot be null")
	               .addConstraintViolation();
			return false;}
		if(!(password.matches(".*[A-Z].*") &&
				password.matches(".*[0-9].*")) ) {
			context.disableDefaultConstraintViolation();
		        context.buildConstraintViolationWithTemplate("Password must contain at least one uppercase letter, must contain at least one digit.")
	               .addConstraintViolation();
		        return false;
		}
		return  true ;
	}

}
