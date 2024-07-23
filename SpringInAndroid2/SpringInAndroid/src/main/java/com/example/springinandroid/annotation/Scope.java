package com.example.springinandroid.annotation;

import com.example.springinandroid.beans.BeanDefinition;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Scope {
    String value() default BeanDefinition.SCOPE_SINGLETION;
}
