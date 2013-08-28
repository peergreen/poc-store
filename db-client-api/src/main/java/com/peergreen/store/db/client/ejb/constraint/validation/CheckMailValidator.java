package com.peergreen.store.db.client.ejb.constraint.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class CheckMailValidator implements ConstraintValidator<CheckEmail, String> {

    @Override
    public void initialize(CheckEmail arg0) {
    }

    @Override
    public boolean isValid(String arg0, ConstraintValidatorContext arg1) {

        Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$");
        Matcher m = p.matcher(arg0.toUpperCase());
        return m.matches();
    }

}





