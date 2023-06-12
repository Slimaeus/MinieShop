package com.master.minieshop.validators;

import com.master.minieshop.service.CustomUserDetailsService;
import com.master.minieshop.validators.annotations.ValidUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidUsernameValidator implements
        ConstraintValidator<ValidUsername, String> {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Override
    public boolean isValid(String username, ConstraintValidatorContext
            context) {
        return customUserDetailsService.findByUsername(username).isEmpty();
    }
}
