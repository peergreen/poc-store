package com.peergreen.store.db.client.ejb.constraint.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Implementation of a constraint validator for the constraint CheckEmail.
 */
public class CheckMailValidator
implements ConstraintValidator<CheckEmail, String> {

    /**
     * Method to give access to attribute values of the constraint annotation.
     * @param arg0 An instance of the constraint annotation.
     */
    @Override
    public void initialize(CheckEmail arg0) {
    }
    /**
     * Method which determines if a String given is parameter is valid according
     * to @CheckEmail.
     * @param arg0 Email to check
     * @param arg1 Could be used to raise any custom validation errors
     * @return true if the arg0 is valid, false if not
     */
    @Override
    public boolean isValid(String arg0, ConstraintValidatorContext arg1) {

        Pattern p = Pattern.
                compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$");
        Matcher m = p.matcher(arg0.toUpperCase());
        return m.matches();
    }

}





