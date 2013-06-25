package com.peergreen.store.db.client.ejb.constraint.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

  @Constraint(validatedBy = {CheckMailValidator.class})
  @Documented
  @Target({ElementType.METHOD,
      ElementType.FIELD,
      ElementType.ANNOTATION_TYPE,
      ElementType.CONSTRUCTOR,
      ElementType.PARAMETER})
  @Retention(RetentionPolicy.RUNTIME)
  
  public @interface CheckEmail {

      String message() default "{invalid.email}";

      Class<?>[] groups() default {};

      Class<? extends Payload>[] payload() default {};

  }
