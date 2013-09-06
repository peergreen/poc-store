package com.peergreen.store.db.client.ejb.constraint.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Interface to define CkeckEmail constraint annotation.
 */
@Constraint(validatedBy = { CheckMailValidator.class })
@Documented
@Target({ElementType.METHOD,
    ElementType.FIELD,
    ElementType.ANNOTATION_TYPE,
    ElementType.CONSTRUCTOR,
    ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckEmail {

    /**
     * Default message when the constraint is violated.
     */
    String message() default "{invalid.email}";

    /**
     * Groups to which this constraint belongs.
     */
    Class<?>[] groups() default { };

    /**
     * Attribute to assign custom payload objects to this constraint.
     */
    Class<? extends Payload>[] payload() default { };

}
