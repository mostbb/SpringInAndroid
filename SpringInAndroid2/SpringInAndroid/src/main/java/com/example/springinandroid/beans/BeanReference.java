package com.example.springinandroid.beans;

import java.lang.reflect.Type;

/**
 * 用于依赖注入中描述bean依赖
 */
public class BeanReference {

	private String beanName;

	private Class<?> type;//获取字段的原始类型信息

	private Type gType;//获取字段的泛型类型信息

	public BeanReference(String beanName) {
		super();
		this.beanName = beanName;
	}

	public BeanReference(Class<?> type) {
		this.type = type;
	}


	public BeanReference(Class<?> type,Type gType) {
		this.type = type;
		this.gType = gType;
	}


	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public Type getgType(){
		return gType;
	}

	public void setgType(Type gType){}
}
