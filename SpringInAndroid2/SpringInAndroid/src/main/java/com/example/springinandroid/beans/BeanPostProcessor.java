package com.example.springinandroid.beans;

public interface BeanPostProcessor {

	default Object postProcessBeforeInitialization(Object bean, String beanName) throws Throwable {
		return bean;
	}

	default Object postProcessAfterInitialization(Object bean, String beanName) throws Throwable {
		return bean;
	}
}
